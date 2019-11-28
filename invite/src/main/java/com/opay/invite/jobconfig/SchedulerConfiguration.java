package com.opay.invite.jobconfig;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.Properties;

/**
 * spring boot 2.x可直接继承，此类不再需要
 */
@EnableConfigurationProperties(QuartzProperties.class)
@Configuration
public class SchedulerConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(QuartzProperties quartzProperties) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        Properties props = new Properties();
        Properties raw = quartzProperties.getProperties();
        for (Object key : raw.keySet()) {
            Object value = raw.get(key);
            props.setProperty("org.quartz." + key, value.toString());
        }
        schedulerFactoryBean.setQuartzProperties(props);

        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setThreadNamePrefix("Quartz-Thread-");
        poolTaskExecutor.setQueueCapacity(10000);
        poolTaskExecutor.setCorePoolSize(50);
        poolTaskExecutor.setMaxPoolSize(1000);
        poolTaskExecutor.setKeepAliveSeconds(30000);
        poolTaskExecutor.setRejectedExecutionHandler((r, e) -> r.run());
        poolTaskExecutor.initialize();

        schedulerFactoryBean.setTaskExecutor(poolTaskExecutor);
        schedulerFactoryBean.setJobFactory(new SpringBeanJobFactory() {
            @Override
            protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
                final Object job = super.createJobInstance(bundle);
                applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
                return job;
            }
        });

        return schedulerFactoryBean;
    }
}
