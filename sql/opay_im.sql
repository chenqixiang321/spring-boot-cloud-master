CREATE DATABASE IF NOT EXISTS opay_im
DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50)  NOT NULL COMMENT '群主的opay_id',
  `img` varchar(200)  NULL DEFAULT NULL COMMENT '群组头像',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '群组名称',
  `notice` varchar(255)  NULL DEFAULT NULL COMMENT '公告',
  `number` int(11) NOT NULL DEFAULT 1 COMMENT '人数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NULL DEFAULT 0 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 ;

-- ----------------------------
-- Table structure for chat_group_member
-- ----------------------------
DROP TABLE IF EXISTS `chat_group_member`;
CREATE TABLE `chat_group_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL COMMENT '群id',
  `opay_id` varchar(50)  NOT NULL COMMENT '群成员的opay_id',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '群成员名',
  `img` varchar(200)  NULL DEFAULT NULL COMMENT '成员头像',
  `is_mute` tinyint(1) NULL DEFAULT 0 COMMENT '是否静音 0:否 1:是',
  `is_block` tinyint(1) NULL DEFAULT 0 COMMENT '是否屏蔽 0:否 1:是',
  `join_time` datetime(0) NOT NULL COMMENT '加入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 ;

-- ----------------------------
-- Table structure for lucky_money
-- ----------------------------
DROP TABLE IF EXISTS `lucky_money`;
CREATE TABLE `lucky_money`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50)  NOT NULL COMMENT '发送者的opay_id',
  `opay_name` varchar(50)  NULL DEFAULT NULL COMMENT '发送者的opay名字',
  `opay_phone` varchar(20)  NULL DEFAULT NULL COMMENT '发送者的手机号',
  `target_id` varchar(50)  NOT NULL COMMENT '发送目标 群or人',
  `target_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '目标类型:0个人 1群',
  `show` varchar(300)  NOT NULL COMMENT '红包说明Best Wishes!',
  `amount` decimal(10, 2) NOT NULL COMMENT '红包总金额',
  `quantity` int(10) NOT NULL COMMENT '红包个数',
  `pay_status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '支付状态1:成功 2:失败',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `version` bigint(20) NULL DEFAULT 0 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 ;

-- ----------------------------
-- Table structure for lucky_money_record
-- ----------------------------
DROP TABLE IF EXISTS `lucky_money_record`;
CREATE TABLE `lucky_money_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `luck_money_id` bigint(20) NOT NULL COMMENT '红包id',
  `opay_id` varchar(50)  NULL DEFAULT NULL COMMENT '抢到者的opay_id',
  `opay_name` varchar(50)  NULL DEFAULT NULL COMMENT '抢到者的opay名字',
  `opay_phone` varchar(20)  NULL DEFAULT NULL COMMENT '抢到者的手机号',
  `amount` decimal(10, 2) NOT NULL COMMENT '红包金额',
  `get_time` datetime(0) NULL DEFAULT NULL COMMENT '抢到红包时间',
  `version` bigint(20) NULL DEFAULT 0 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `luck_money_id`(`luck_money_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 ;

-- ----------------------------
-- Table structure for rong_cloud_message
-- ----------------------------
DROP TABLE IF EXISTS `rong_cloud_message`;
CREATE TABLE `rong_cloud_message`  (
  `msg_uid` varchar(20)  NOT NULL,
  `from_user_id` varchar(50)  NULL DEFAULT NULL,
  `to_user_id` varchar(50)  NULL DEFAULT NULL,
  `object_name` varchar(20)  NULL DEFAULT NULL,
  `content` longtext  NULL,
  `channel_type` varchar(20)  NULL DEFAULT NULL,
  `msg_timestamp` bigint(20) NULL DEFAULT NULL,
  `sensitive_type` tinyint(4) NULL DEFAULT NULL,
  `source` varchar(20)  NULL DEFAULT NULL,
  `group_user_ids` longtext  NULL,
  PRIMARY KEY (`msg_uid`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for rong_cloud_message_2019_12
-- ----------------------------
DROP TABLE IF EXISTS `rong_cloud_message_2019_12`;
CREATE TABLE `rong_cloud_message_2019_12`  (
  `msg_uid` varchar(20)  NOT NULL,
  `from_user_id` varchar(50)  NULL DEFAULT NULL,
  `to_user_id` varchar(50)  NULL DEFAULT NULL,
  `object_name` varchar(20)  NULL DEFAULT NULL,
  `content` longtext  NULL,
  `channel_type` varchar(20)  NULL DEFAULT NULL,
  `msg_timestamp` bigint(20) NULL DEFAULT NULL,
  `sensitive_type` tinyint(4) NULL DEFAULT NULL,
  `source` varchar(20)  NULL DEFAULT NULL,
  `group_user_ids` longtext  NULL,
  PRIMARY KEY (`msg_uid`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for rong_cloud_message_2020_1
-- ----------------------------
DROP TABLE IF EXISTS `rong_cloud_message_2020_1`;
CREATE TABLE `rong_cloud_message_2020_1`  (
  `msg_uid` varchar(20)  NOT NULL,
  `from_user_id` varchar(50)  NULL DEFAULT NULL,
  `to_user_id` varchar(50)  NULL DEFAULT NULL,
  `object_name` varchar(20)  NULL DEFAULT NULL,
  `content` longtext  NULL,
  `channel_type` varchar(20)  NULL DEFAULT NULL,
  `msg_timestamp` bigint(20) NULL DEFAULT NULL,
  `sensitive_type` tinyint(4) NULL DEFAULT NULL,
  `source` varchar(20)  NULL DEFAULT NULL,
  `group_user_ids` longtext  NULL,
  PRIMARY KEY (`msg_uid`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for rong_cloud_message_2020_2
-- ----------------------------
DROP TABLE IF EXISTS `rong_cloud_message_2020_2`;
CREATE TABLE `rong_cloud_message_2020_2`  (
  `msg_uid` varchar(20)  NOT NULL,
  `from_user_id` varchar(50)  NULL DEFAULT NULL,
  `to_user_id` varchar(50)  NULL DEFAULT NULL,
  `object_name` varchar(20)  NULL DEFAULT NULL,
  `content` longtext  NULL,
  `channel_type` varchar(20)  NULL DEFAULT NULL,
  `msg_timestamp` bigint(20) NULL DEFAULT NULL,
  `sensitive_type` tinyint(4) NULL DEFAULT NULL,
  `source` varchar(20)  NULL DEFAULT NULL,
  `group_user_ids` longtext  NULL,
  PRIMARY KEY (`msg_uid`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for rong_cloud_message_2020_3
-- ----------------------------
DROP TABLE IF EXISTS `rong_cloud_message_2020_3`;
CREATE TABLE `rong_cloud_message_2020_3`  (
  `msg_uid` varchar(20)  NOT NULL,
  `from_user_id` varchar(50)  NULL DEFAULT NULL,
  `to_user_id` varchar(50)  NULL DEFAULT NULL,
  `object_name` varchar(20)  NULL DEFAULT NULL,
  `content` longtext  NULL,
  `channel_type` varchar(20)  NULL DEFAULT NULL,
  `msg_timestamp` bigint(20) NULL DEFAULT NULL,
  `sensitive_type` tinyint(4) NULL DEFAULT NULL,
  `source` varchar(20)  NULL DEFAULT NULL,
  `group_user_ids` longtext  NULL,
  PRIMARY KEY (`msg_uid`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for rong_cloud_message_2020_4
-- ----------------------------
DROP TABLE IF EXISTS `rong_cloud_message_2020_4`;
CREATE TABLE `rong_cloud_message_2020_4`  (
  `msg_uid` varchar(20)  NOT NULL,
  `from_user_id` varchar(50)  NULL DEFAULT NULL,
  `to_user_id` varchar(50)  NULL DEFAULT NULL,
  `object_name` varchar(20)  NULL DEFAULT NULL,
  `content` longtext  NULL,
  `channel_type` varchar(20)  NULL DEFAULT NULL,
  `msg_timestamp` bigint(20) NULL DEFAULT NULL,
  `sensitive_type` tinyint(4) NULL DEFAULT NULL,
  `source` varchar(20)  NULL DEFAULT NULL,
  `group_user_ids` longtext  NULL,
  PRIMARY KEY (`msg_uid`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for rong_cloud_message_2020_5
-- ----------------------------
DROP TABLE IF EXISTS `rong_cloud_message_2020_5`;
CREATE TABLE `rong_cloud_message_2020_5`  (
  `msg_uid` varchar(20)  NOT NULL,
  `from_user_id` varchar(50)  NULL DEFAULT NULL,
  `to_user_id` varchar(50)  NULL DEFAULT NULL,
  `object_name` varchar(20)  NULL DEFAULT NULL,
  `content` longtext  NULL,
  `channel_type` varchar(20)  NULL DEFAULT NULL,
  `msg_timestamp` bigint(20) NULL DEFAULT NULL,
  `sensitive_type` tinyint(4) NULL DEFAULT NULL,
  `source` varchar(20)  NULL DEFAULT NULL,
  `group_user_ids` longtext  NULL,
  PRIMARY KEY (`msg_uid`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opay_id` varchar(50)  NOT NULL COMMENT 'opay帐号id',
  `phone` varchar(20)  NULL DEFAULT NULL COMMENT '手机号',
  `token` varchar(512)  NOT NULL COMMENT '融云token',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `opay_id`(`opay_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 ;

SET FOREIGN_KEY_CHECKS = 1;
