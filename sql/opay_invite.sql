/*
 Navicat MySQL Data Transfer

 Source Server         : 52.210.41.16(opay-invite-test)
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 52.210.41.16
 Source Database       : opay_invite

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : utf-8

 Date: 12/17/2019 11:13:54 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `TRIGGER_NAME` varchar(120) NOT NULL COMMENT 'Trigger名称',
  `TRIGGER_GROUP` varchar(120) NOT NULL COMMENT 'Trigger组',
  `BLOB_DATA` blob COMMENT 'BLOB数据',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `CALENDAR_NAME` varchar(120) NOT NULL COMMENT '日历名称',
  `CALENDAR` blob NOT NULL COMMENT 'Blob calendar对象序列化后存入此字段',
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `TRIGGER_NAME` varchar(120) NOT NULL COMMENT 'Trigger名称',
  `TRIGGER_GROUP` varchar(120) NOT NULL COMMENT 'Trigger组',
  `CRON_EXPRESSION` varchar(200) NOT NULL COMMENT 'Cron表达式',
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `ENTRY_ID` varchar(95) NOT NULL COMMENT 'InstanceId+系统当前毫秒',
  `TRIGGER_NAME` varchar(200) NOT NULL COMMENT 'Trigger 名称',
  `TRIGGER_GROUP` varchar(200) NOT NULL COMMENT 'Trigger 组',
  `INSTANCE_NAME` varchar(200) NOT NULL COMMENT 'Quartz InstanceId 实例对象ID',
  `FIRED_TIME` bigint(13) NOT NULL COMMENT '触发时间（系统时间）',
  `SCHED_TIME` bigint(13) NOT NULL COMMENT '下次fire时间',
  `PRIORITY` int(11) NOT NULL COMMENT '任务优先级',
  `STATE` varchar(16) NOT NULL COMMENT '状态都是“ACQUIRED”',
  `JOB_NAME` varchar(200) DEFAULT NULL COMMENT 'jobdetail名字',
  `JOB_GROUP` varchar(200) DEFAULT NULL COMMENT 'Jobdetail 组',
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL COMMENT '同一任务是否并行执行',
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL COMMENT 'job是否恢复运行',
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `JOB_NAME` varchar(120) NOT NULL COMMENT '集群中job的名字',
  `JOB_GROUP` varchar(120) NOT NULL COMMENT '集群中job的所属组的名字',
  `DESCRIPTION` varchar(250) DEFAULT NULL COMMENT '描述',
  `JOB_CLASS_NAME` varchar(250) NOT NULL COMMENT '执行job class类',
  `IS_DURABLE` varchar(1) NOT NULL COMMENT '是否持久化   1为是',
  `IS_NONCONCURRENT` varchar(1) NOT NULL COMMENT '并发JOB @DisallowConcurrentExecution',
  `IS_UPDATE_DATA` varchar(1) NOT NULL COMMENT '持久jobDataMap @PersistJobDataAfterExecution',
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob COMMENT 'Job的datamap里的数据',
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `LOCK_NAME` varchar(40) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `TRIGGER_GROUP` varchar(120) NOT NULL COMMENT 'Trigger 组',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `INSTANCE_NAME` varchar(120) NOT NULL COMMENT 'InstanceId',
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL COMMENT '最后一次检查时间',
  `CHECKIN_INTERVAL` bigint(13) NOT NULL COMMENT '检查周期',
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `TRIGGER_NAME` varchar(120) NOT NULL COMMENT 'Trigger 名称',
  `TRIGGER_GROUP` varchar(120) NOT NULL COMMENT 'Trigger 组',
  `REPEAT_COUNT` bigint(7) NOT NULL COMMENT '重复执行次数',
  `REPEAT_INTERVAL` bigint(12) NOT NULL COMMENT '间隔时间',
  `TIMES_TRIGGERED` bigint(10) NOT NULL COMMENT '作业运行的次数',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `TRIGGER_NAME` varchar(120) NOT NULL COMMENT 'Trigger 名称',
  `TRIGGER_GROUP` varchar(120) NOT NULL COMMENT 'Trigger 组',
  `STR_PROP_1` varchar(512) DEFAULT NULL COMMENT 'String类型PROP1',
  `STR_PROP_2` varchar(512) DEFAULT NULL COMMENT 'String类型PROP2',
  `STR_PROP_3` varchar(512) DEFAULT NULL COMMENT 'String类型PROP3',
  `INT_PROP_1` int(11) DEFAULT NULL COMMENT 'Integer类型PROP1',
  `INT_PROP_2` int(11) DEFAULT NULL COMMENT 'Integer类型PROP2',
  `LONG_PROP_1` bigint(20) DEFAULT NULL COMMENT 'Long类型PROP1',
  `LONG_PROP_2` bigint(20) DEFAULT NULL COMMENT 'Long类型PROP2',
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的PROP1',
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的PROP2',
  `BOOL_PROP_1` varchar(1) DEFAULT NULL COMMENT 'boolean类型PROP1',
  `BOOL_PROP_2` varchar(1) DEFAULT NULL COMMENT 'boolean类型PROP2',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL COMMENT 'Quartz 实例名',
  `TRIGGER_NAME` varchar(120) NOT NULL COMMENT 'Trigger 名称',
  `TRIGGER_GROUP` varchar(120) NOT NULL COMMENT 'Trigger 组',
  `JOB_NAME` varchar(120) NOT NULL COMMENT 'Job 名称',
  `JOB_GROUP` varchar(120) NOT NULL COMMENT 'Job 组',
  `DESCRIPTION` varchar(250) DEFAULT NULL COMMENT '描述',
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '下次触发时间 毫秒',
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '上次触发时间',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '权重',
  `TRIGGER_STATE` varchar(16) NOT NULL COMMENT '状态',
  `TRIGGER_TYPE` varchar(8) NOT NULL COMMENT '触发类型 cron ,simple,calendar',
  `START_TIME` bigint(13) NOT NULL COMMENT '开始运行时间',
  `END_TIME` bigint(13) DEFAULT NULL COMMENT '结束运行时间',
  `CALENDAR_NAME` varchar(200) DEFAULT NULL COMMENT '设置的calendar的名称',
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL COMMENT '-1:错过执行 0:正常',
  `JOB_DATA` blob COMMENT 'JOB的datamap,仓库ID存在此datamap里',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `invite_count`
-- ----------------------------
DROP TABLE IF EXISTS `invite_count`;
CREATE TABLE `invite_count` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50) NOT NULL COMMENT '发送者的opay_id',
  `day` varchar(8) NOT NULL COMMENT '日期',
  `opay_name` varchar(50) DEFAULT '' COMMENT '发送者的opay名字',
  `opay_phone` varchar(20) DEFAULT '' COMMENT '发送者的手机号',
  `login` int(1) NOT NULL DEFAULT '1' COMMENT '每天登录获得次数',
  `share` int(1) NOT NULL DEFAULT '0' COMMENT '每天分享次数',
  `invite` int(1) NOT NULL DEFAULT '0' COMMENT '成功邀请次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `opay_id` (`opay_id`,`day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `luck_draw_info`
-- ----------------------------
DROP TABLE IF EXISTS `luck_draw_info`;
CREATE TABLE `luck_draw_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50) NOT NULL COMMENT '中奖者的opay_id',
  `opay_name` varchar(50) DEFAULT '' COMMENT '中奖者的opay名字',
  `opay_phone` varchar(20) DEFAULT '' COMMENT '中奖者的手机号',
  `prize` varchar(20) NOT NULL COMMENT '奖品',
  `prize_level` int(1) NOT NULL COMMENT '奖品级别 数越小越值钱',
  `prize_pool` int(1) NOT NULL COMMENT '奖池号 1,2',
  `reference` varchar(50) DEFAULT NULL,
  `requestId` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `prize` (`prize`) USING BTREE,
  KEY `prize_level` (`prize_level`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12187 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `opay_active_cashback`
-- ----------------------------
DROP TABLE IF EXISTS `opay_active_cashback`;
CREATE TABLE `opay_active_cashback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `version` int(10) DEFAULT '0' COMMENT '版本号',
  `amount` decimal(12,2) DEFAULT '0.00' COMMENT '可提总金额',
  `total_amount` decimal(12,2) DEFAULT '0.00' COMMENT '累计总金额',
  `status` tinyint(1) DEFAULT '0' COMMENT '0:正常 1:冻结',
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_o` (`opay_id`) USING BTREE,
  KEY `idx_o_v` (`opay_id`,`version`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='用户活动钱包';

-- ----------------------------
--  Table structure for `opay_active_tixian`
-- ----------------------------
DROP TABLE IF EXISTS `opay_active_tixian`;
CREATE TABLE `opay_active_tixian` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50) DEFAULT NULL COMMENT '提现用户',
  `amount` decimal(10,2) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '0' COMMENT '0:bonus 1:balance',
  `create_at` timestamp NULL DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '0:申请中， 1:审批通过 2:审批不通过 3:转账成功 4:转账失败',
  `month` int(6) DEFAULT NULL,
  `day` int(8) DEFAULT NULL,
  `device_id` varchar(50) DEFAULT NULL COMMENT '用户手机设备号',
  `ip` varchar(50) DEFAULT NULL COMMENT '用户IP',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '外部订单号',
  `reference` varchar(50) DEFAULT NULL COMMENT '内部流水号',
  `update_at` datetime DEFAULT NULL COMMENT '时间处理',
  `operator` varchar(50) DEFAULT NULL COMMENT '审核人',
  PRIMARY KEY (`id`),
  KEY `idx_o` (`opay_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `opay_active_tixian_log`
-- ----------------------------
DROP TABLE IF EXISTS `opay_active_tixian_log`;
CREATE TABLE `opay_active_tixian_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50) DEFAULT NULL COMMENT '提现用户',
  `amount` decimal(10,2) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '0' COMMENT '0:bonus 1:balance',
  `create_at` datetime DEFAULT NULL,
  `tixian_id` bigint(20) DEFAULT '0' COMMENT '提现ID',
  `month` int(6) DEFAULT NULL,
  `day` int(8) DEFAULT NULL,
  `device_id` varchar(50) DEFAULT NULL COMMENT '用户手机设备号',
  `ip` varchar(50) DEFAULT NULL COMMENT '用户IP',
  `mark` tinyint(1) DEFAULT '0' COMMENT '0:扣除，1:收入',
  PRIMARY KEY (`id`),
  KEY `idx_o` (`opay_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `opay_invite_code`
-- ----------------------------
DROP TABLE IF EXISTS `opay_invite_code`;
CREATE TABLE `opay_invite_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `invite_code` varchar(30) DEFAULT NULL COMMENT '邀请码',
  `opay_id` varchar(50) DEFAULT NULL COMMENT '邀请人',
  `phone` varchar(30) DEFAULT NULL COMMENT '手机号',
  `create_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_ic` (`invite_code`) USING BTREE,
  KEY `idx_o` (`opay_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `opay_invite_relation`
-- ----------------------------
DROP TABLE IF EXISTS `opay_invite_relation`;
CREATE TABLE `opay_invite_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `master_id` varchar(50) DEFAULT NULL COMMENT '邀请人ID，对应opay_id',
  `pupil_id` varchar(50) DEFAULT NULL COMMENT '徒弟 对应opay_id',
  `pupil_phone` varchar(30) DEFAULT NULL COMMENT '徒弟手机号',
  `master_phone` varchar(30) DEFAULT NULL COMMENT '邀请人手机号',
  `create_at` datetime DEFAULT NULL,
  `master_parent_id` varchar(50) DEFAULT NULL COMMENT '师傅的师傅对应opay_id',
  `mark_type` tinyint(1) DEFAULT '0' COMMENT '邀请人账号类型0:普通用户， 1:代理',
  `month` int(6) DEFAULT NULL,
  `day` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_m` (`master_id`) USING BTREE,
  KEY `idx_p` (`pupil_id`) USING BTREE,
  KEY `idx_m_p` (`master_id`,`pupil_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='邀请关系';

-- ----------------------------
--  Table structure for `opay_master_pupil_award`
-- ----------------------------
DROP TABLE IF EXISTS `opay_master_pupil_award`;
CREATE TABLE `opay_master_pupil_award` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50) DEFAULT NULL COMMENT 'opay_id唯一ID',
  `pupil_id` varchar(50) DEFAULT NULL COMMENT '徒弟',
  `reward` decimal(10,2) DEFAULT '0.00' COMMENT '奖励金额',
  `create_at` datetime DEFAULT NULL COMMENT '奖励时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '0:待入账，1:已入账',
  `action` tinyint(1) DEFAULT '0' COMMENT '执行行为奖励 1:注册绑定徒弟关系 2:徒弟首次充值到钱包 3:购买话费或其它小额支付 4:博彩 5:打车',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '实际金额',
  `mark_type` tinyint(1) DEFAULT '0' COMMENT '邀请人账号类型0:普通用户， 1:代理',
  `step_json` varchar(300) DEFAULT NULL,
  `master_reward` decimal(10,2) DEFAULT '0.00' COMMENT '师傅获得奖励',
  `master_type` tinyint(1) DEFAULT '0' COMMENT '0:徒弟 1:师傅',
  `month` int(6) DEFAULT NULL COMMENT '201901',
  `day` int(10) DEFAULT NULL COMMENT '20191101',
  `order_id` varchar(50) DEFAULT NULL COMMENT '外部订单号',
  PRIMARY KEY (`id`),
  KEY `idx_o` (`opay_id`) USING BTREE,
  KEY `idx_d` (`day`) USING BTREE,
  KEY `idx_o_p` (`opay_id`,`pupil_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 COMMENT='师傅或是徒弟各自获得奖励金额';

-- ----------------------------
--  Table structure for `opay_user_order`
-- ----------------------------
DROP TABLE IF EXISTS `opay_user_order`;
CREATE TABLE `opay_user_order` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单号',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '折扣前总金额',
  `actual_amount` decimal(10,2) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `order_time` varchar(50) DEFAULT NULL COMMENT '订单时间',
  `opay_id` varchar(255) DEFAULT NULL COMMENT '交易用户ID',
  `type` tinyint(1) DEFAULT NULL COMMENT '订单类型2:徒弟首次充值到钱包 3:购买话费或其它小额支付 4:博彩 5:打车',
  `status` tinyint(1) DEFAULT '0' COMMENT '处理状态：0待处理，1:已处理',
  `month` int(6) DEFAULT NULL COMMENT '月份201911',
  `day` int(8) DEFAULT NULL COMMENT '天：20191120',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_o` (`order_id`) USING BTREE,
  KEY `idx_d` (`day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `opay_user_task`
-- ----------------------------
DROP TABLE IF EXISTS `opay_user_task`;
CREATE TABLE `opay_user_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opay_id` varchar(50) DEFAULT NULL,
  `day` int(10) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_o_d` (`opay_id`,`day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
