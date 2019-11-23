package com.opay.im.config;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class CustomerRedisCacheWriter implements RedisCacheWriter {
    private final RedisConnectionFactory connectionFactory;
    private final Duration sleepTime;

    CustomerRedisCacheWriter(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, Duration.ZERO);
    }

    CustomerRedisCacheWriter(RedisConnectionFactory connectionFactory, Duration sleepTime) {
        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");
        Assert.notNull(sleepTime, "SleepTime must not be null!");
        this.connectionFactory = connectionFactory;
        this.sleepTime = sleepTime;
    }

    public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");
        this.execute(name, (connection) -> {
            long exp = getExp(key);
            if (exp != 0) {
                connection.set(key, value, Expiration.from(exp, TimeUnit.SECONDS), SetOption.upsert());
            } else if (shouldExpireWithin(ttl)) {
                connection.set(key, value, Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS), SetOption.upsert());
            } else {
                connection.set(key, value);
            }

            return "OK";
        });
    }

    private long getExp(byte[] key) {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        long exp = 0;
        for (int i = 0; i < key.length; i++) {
            if (key[i] == 64) {
                index = i + 1;
            }
            if (i + 1 > key.length || key[i + 1] == 58) {
                break;
            }
            if (index != 0) {
                sb.append((char) key[index]);
                index++;
            }
        }
        String expStr=sb.toString();
        if("".equals(expStr)){
            return exp;
        }else{
            return Long.parseLong(expStr);
        }
    }

    public byte[] get(String name, byte[] key) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        return (byte[]) this.execute(name, (connection) -> {
            return connection.get(key);
        });
    }

    public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");
        return (byte[]) this.execute(name, (connection) -> {
            if (this.isLockingCacheWriter()) {
                this.doLock(name, connection);
            }

            Object var6;
            try {
                if (!connection.setNX(key, value)) {
                    byte[] var10 = connection.get(key);
                    return var10;
                }

                if (shouldExpireWithin(ttl)) {
                    connection.pExpire(key, ttl.toMillis());
                }

                var6 = null;
            } finally {
                if (this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }

            return (byte[]) var6;
        });
    }

    public void remove(String name, byte[] key) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        this.execute(name, (connection) -> {
            return connection.del(new byte[][]{key});
        });
    }

    public void clean(String name, byte[] pattern) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(pattern, "Pattern must not be null!");
        this.execute(name, (connection) -> {
            boolean wasLocked = false;

            try {
                if (this.isLockingCacheWriter()) {
                    this.doLock(name, connection);
                    wasLocked = true;
                }

                byte[][] keys = (byte[][]) ((Set) Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet())).toArray(new byte[0][]);
                if (keys.length > 0) {
                    connection.del(keys);
                }
            } finally {
                if (wasLocked && this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }

            return "OK";
        });
    }

    void lock(String name) {
        this.execute(name, (connection) -> {
            return this.doLock(name, connection);
        });
    }

    void unlock(String name) {
        this.executeLockFree((connection) -> {
            this.doUnlock(name, connection);
        });
    }

    private Boolean doLock(String name, RedisConnection connection) {
        return connection.setNX(createCacheLockKey(name), new byte[0]);
    }

    private Long doUnlock(String name, RedisConnection connection) {
        return connection.del(new byte[][]{createCacheLockKey(name)});
    }

    boolean doCheckLock(String name, RedisConnection connection) {
        return connection.exists(createCacheLockKey(name));
    }

    private boolean isLockingCacheWriter() {
        return !this.sleepTime.isZero() && !this.sleepTime.isNegative();
    }

    private <T> T execute(String name, Function<RedisConnection, T> callback) {
        RedisConnection connection = this.connectionFactory.getConnection();

        T var4;
        try {
            this.checkAndPotentiallyWaitUntilUnlocked(name, connection);
            var4 = callback.apply(connection);
        } finally {
            connection.close();
        }

        return var4;
    }

    private void executeLockFree(Consumer<RedisConnection> callback) {
        RedisConnection connection = this.connectionFactory.getConnection();

        try {
            callback.accept(connection);
        } finally {
            connection.close();
        }

    }

    private void checkAndPotentiallyWaitUntilUnlocked(String name, RedisConnection connection) {
        if (this.isLockingCacheWriter()) {
            try {
                while (this.doCheckLock(name, connection)) {
                    Thread.sleep(this.sleepTime.toMillis());
                }

            } catch (InterruptedException var4) {
                Thread.currentThread().interrupt();
                throw new PessimisticLockingFailureException(String.format("Interrupted while waiting to unlock cache %s", name), var4);
            }
        }
    }

    private static boolean shouldExpireWithin(@Nullable Duration ttl) {
        return ttl != null && !ttl.isZero() && !ttl.isNegative();
    }

    private static byte[] createCacheLockKey(String name) {
        return (name + "~lock").getBytes(StandardCharsets.UTF_8);
    }
}
