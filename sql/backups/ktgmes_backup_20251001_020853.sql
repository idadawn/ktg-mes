-- MySQL dump 10.13  Distrib 5.7.44, for Linux (x86_64)
--
-- Host: mysql    Database: ktgmes
-- ------------------------------------------------------
-- Server version	5.7.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Blob类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Cron类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='已触发的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储的悲观锁信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='暂停的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='调度器状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='简单触发器的信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='同步机制的行锁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='触发器详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ç¼–å·',
  `table_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'è¡¨åç§°',
  `table_comment` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'è¡¨æè¿°',
  `sub_table_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å…³è”å­è¡¨çš„è¡¨å',
  `sub_table_fk_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å­è¡¨å…³è”çš„å¤–é”®å',
  `class_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å®žä½“ç±»åç§°',
  `tpl_category` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'crud' COMMENT 'ä½¿ç”¨çš„æ¨¡æ¿ï¼ˆcrudå•è¡¨æ“ä½œ treeæ ‘è¡¨æ“ä½œï¼‰',
  `package_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ç”ŸæˆåŒ…è·¯å¾„',
  `module_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ç”Ÿæˆæ¨¡å—å',
  `business_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ç”Ÿæˆä¸šåŠ¡å',
  `function_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ç”ŸæˆåŠŸèƒ½å',
  `function_author` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ç”ŸæˆåŠŸèƒ½ä½œè€…',
  `gen_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'ç”Ÿæˆä»£ç æ–¹å¼ï¼ˆ0zipåŽ‹ç¼©åŒ… 1è‡ªå®šä¹‰è·¯å¾„ï¼‰',
  `gen_path` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '/' COMMENT 'ç”Ÿæˆè·¯å¾„ï¼ˆä¸å¡«é»˜è®¤é¡¹ç›®è·¯å¾„ï¼‰',
  `options` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å…¶å®ƒç”Ÿæˆé€‰é¡¹',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ä»£ç ç”Ÿæˆä¸šåŠ¡è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ç¼–å·',
  `table_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å½’å±žè¡¨ç¼–å·',
  `column_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'åˆ—åç§°',
  `column_comment` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'åˆ—æè¿°',
  `column_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'åˆ—ç±»åž‹',
  `java_type` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'JAVAç±»åž‹',
  `java_field` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'JAVAå­—æ®µå',
  `is_pk` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¯å¦ä¸»é”®ï¼ˆ1æ˜¯ï¼‰',
  `is_increment` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¯å¦è‡ªå¢žï¼ˆ1æ˜¯ï¼‰',
  `is_required` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¯å¦å¿…å¡«ï¼ˆ1æ˜¯ï¼‰',
  `is_insert` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¯å¦ä¸ºæ’å…¥å­—æ®µï¼ˆ1æ˜¯ï¼‰',
  `is_edit` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¯å¦ç¼–è¾‘å­—æ®µï¼ˆ1æ˜¯ï¼‰',
  `is_list` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¯å¦åˆ—è¡¨å­—æ®µï¼ˆ1æ˜¯ï¼‰',
  `is_query` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¯å¦æŸ¥è¯¢å­—æ®µï¼ˆ1æ˜¯ï¼‰',
  `query_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'EQ' COMMENT 'æŸ¥è¯¢æ–¹å¼ï¼ˆç­‰äºŽã€ä¸ç­‰äºŽã€å¤§äºŽã€å°äºŽã€èŒƒå›´ï¼‰',
  `html_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ˜¾ç¤ºç±»åž‹ï¼ˆæ–‡æœ¬æ¡†ã€æ–‡æœ¬åŸŸã€ä¸‹æ‹‰æ¡†ã€å¤é€‰æ¡†ã€å•é€‰æ¡†ã€æ—¥æœŸæŽ§ä»¶ï¼‰',
  `dict_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å­—å…¸ç±»åž‹',
  `sort` int(11) DEFAULT NULL COMMENT 'æŽ’åº',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ä»£ç ç”Ÿæˆä¸šåŠ¡è¡¨å­—æ®µ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `print_client`
--

DROP TABLE IF EXISTS `print_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_client` (
  `client_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'æ‰“å°æœºå®¢æˆ·ç«¯ID',
  `client_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'æ‰“å°æœºå®¢æˆ·ç«¯ç¼–ç ',
  `client_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'æ‰“å°æœºå®¢æˆ·ç«¯åç§°',
  `client_ip` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ‰“å°å®¢æˆ·ç«¯IP',
  `client_port` bigint(20) DEFAULT NULL COMMENT 'æ‰“å°å®¢æˆ·ç«¯ç«¯å£',
  `client_token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ‰“å°å®¢æˆ·ç«¯token',
  `status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'å®¢æˆ·ç«¯çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `workshop_id` bigint(20) DEFAULT NULL COMMENT 'æ‰€å±žè½¦é—´ID',
  `workshop_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ‰€å±žè½¦é—´ç¼–ç ',
  `workshop_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ‰€å±žè½¦é—´åç§°',
  `workstation_id` bigint(20) DEFAULT NULL COMMENT 'å·¥ä½œç«™ID',
  `workstation_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å·¥ä½œç«™ç¼–ç ',
  `workstation_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å·¥ä½œç«™åç§°',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å¤‡æ³¨',
  `attr1` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'é¢„ç•™å­—æ®µ1',
  `attr2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'é¢„ç•™å­—æ®µ2',
  `attr3` bigint(20) DEFAULT NULL COMMENT 'é¢„ç•™å­—æ®µ3',
  `attr4` bigint(20) DEFAULT NULL COMMENT 'é¢„ç•™å­—æ®µ4',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `uk_client_code` (`client_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='æ‰“å°å®¢æˆ·ç«¯è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `print_client`
--

LOCK TABLES `print_client` WRITE;
/*!40000 ALTER TABLE `print_client` DISABLE KEYS */;
INSERT INTO `print_client` VALUES (1,'PC001','默认打印客户端','127.0.0.1',8080,'default_token','0',NULL,NULL,NULL,NULL,NULL,NULL,'默认打印客户端',NULL,NULL,NULL,NULL,'admin','2025-10-01 01:09:39','',NULL);
/*!40000 ALTER TABLE `print_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_feedback`
--

DROP TABLE IF EXISTS `pro_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pro_feedback` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `feedback_type` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报工类型',
  `feedback_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报工单编号',
  `workstation_id` bigint(20) NOT NULL COMMENT '工作站ID',
  `workstation_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作站编号',
  `workstation_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作站名称',
  `workorder_id` bigint(20) NOT NULL COMMENT '生产工单ID',
  `workorder_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生产工单编号',
  `workorder_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生产工单名称',
  `route_id` bigint(20) NOT NULL COMMENT '工艺流程ID',
  `route_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工艺流程编号',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `process_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工序编码',
  `process_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工序名称',
  `task_id` bigint(20) DEFAULT NULL COMMENT '生产任务ID',
  `task_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生产任务编号',
  `item_id` bigint(20) NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品物料名称',
  `unit_of_measure` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位',
  `unit_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位名称',
  `specification` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '规格型号',
  `expire_date` datetime DEFAULT NULL COMMENT '过期日期',
  `lot_number` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生产批号',
  `quantity` decimal(14,2) DEFAULT NULL COMMENT '排产数量',
  `quantity_feedback` decimal(14,2) DEFAULT NULL COMMENT '本次报工数量',
  `quantity_qualified` decimal(14,2) DEFAULT NULL COMMENT '合格品数量',
  `quantity_unquanlified` decimal(14,2) DEFAULT NULL COMMENT '不良品数量',
  `quantity_uncheck` decimal(14,2) DEFAULT NULL COMMENT '待检测数量',
  `quantity_labor_scrap` decimal(14,2) DEFAULT NULL COMMENT '人工报废数量',
  `quantity_material_scrap` decimal(14,2) DEFAULT NULL COMMENT '料废数量',
  `quantity_other_scrap` decimal(14,2) DEFAULT NULL COMMENT '其他报废数量',
  `user_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报工用户名',
  `nick_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `feedback_channel` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报工途径',
  `feedback_time` datetime DEFAULT NULL COMMENT '报工时间',
  `record_user` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '记录人',
  `record_nick` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '记录人名称',
  `status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT 'PREPARE' COMMENT '状态',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段2',
  `attr3` bigint(20) DEFAULT NULL COMMENT '预留字段3',
  `attr4` bigint(20) DEFAULT NULL COMMENT '预留字段4',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生产报工记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_feedback`
--

LOCK TABLES `pro_feedback` WRITE;
/*!40000 ALTER TABLE `pro_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_route_process`
--

DROP TABLE IF EXISTS `pro_route_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pro_route_process` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `route_id` bigint(20) NOT NULL COMMENT '工艺路线ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `process_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工序编码',
  `process_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工序名称',
  `order_num` int(4) DEFAULT '1' COMMENT '序号',
  `next_process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `next_process_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工序编码',
  `next_process_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工序名称',
  `link_type` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT 'SS' COMMENT '与下一道工序关系',
  `default_pre_time` int(11) DEFAULT '0' COMMENT '准备时间',
  `default_suf_time` int(11) DEFAULT '0' COMMENT '等待时间',
  `color_code` char(7) COLLATE utf8mb4_unicode_ci DEFAULT '#00AEF3' COMMENT '甘特图显示颜色',
  `key_flag` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT 'N' COMMENT '关键工序',
  `is_check` char(1) COLLATE utf8mb4_unicode_ci DEFAULT 'N' COMMENT '是否检验',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段2',
  `attr3` bigint(20) DEFAULT NULL COMMENT '预留字段3',
  `attr4` bigint(20) DEFAULT NULL COMMENT '预留字段4',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工艺组成表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_route_process`
--

LOCK TABLES `pro_route_process` WRITE;
/*!40000 ALTER TABLE `pro_route_process` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_route_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_route_product`
--

DROP TABLE IF EXISTS `pro_route_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pro_route_product` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `route_id` bigint(20) NOT NULL COMMENT '工艺路线ID',
  `item_id` bigint(20) NOT NULL COMMENT '产品物料ID',
  `item_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品物料编码',
  `item_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品物料名称',
  `specification` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位',
  `unit_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位名称',
  `quantity` bigint(20) DEFAULT '1' COMMENT '生产数量',
  `production_time` decimal(12,2) DEFAULT '1.00' COMMENT '生产用时',
  `time_unit_type` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT 'MINUTE' COMMENT '时间单位',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段2',
  `attr3` bigint(20) DEFAULT NULL COMMENT '预留字段3',
  `attr4` bigint(20) DEFAULT NULL COMMENT '预留字段4',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品制程表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_route_product`
--

LOCK TABLES `pro_route_product` WRITE;
/*!40000 ALTER TABLE `pro_route_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_route_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_workorder`
--

DROP TABLE IF EXISTS `pro_workorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pro_workorder` (
  `workorder_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `workorder_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工单编码',
  `workorder_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工单名称',
  `workorder_type` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT 'SELF' COMMENT '工单类型',
  `order_source` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '来源类型',
  `source_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '来源单据',
  `product_id` bigint(20) NOT NULL COMMENT '产品ID',
  `product_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品编号',
  `product_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品名称',
  `product_spc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '规格型号',
  `unit_of_measure` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位',
  `unit_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位名称',
  `batch_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '批次号',
  `quantity` decimal(14,2) NOT NULL DEFAULT '0.00' COMMENT '生产数量',
  `quantity_produced` decimal(14,2) DEFAULT '0.00' COMMENT '已生产数量',
  `quantity_changed` decimal(14,2) DEFAULT '0.00' COMMENT '调整数量',
  `quantity_scheduled` decimal(14,2) DEFAULT '0.00' COMMENT '已排产数量',
  `client_id` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `client_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户编码',
  `client_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户名称',
  `vendor_id` bigint(20) DEFAULT NULL COMMENT '供应商ID',
  `vendor_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '供应商编号',
  `vendor_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '供应商名称',
  `request_date` datetime NOT NULL COMMENT '需求日期',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父工单',
  `ancestors` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所有父节点ID',
  `cancel_date` datetime DEFAULT NULL COMMENT '取消日期',
  `finish_date` datetime DEFAULT NULL COMMENT '完成时间',
  `status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT 'PREPARE' COMMENT '单据状态',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留字段2',
  `attr3` bigint(20) DEFAULT NULL COMMENT '预留字段3',
  `attr4` bigint(20) DEFAULT NULL COMMENT '预留字段4',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`workorder_id`),
  UNIQUE KEY `uk_workorder_code` (`workorder_code`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生产工单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_workorder`
--

LOCK TABLES `pro_workorder` WRITE;
/*!40000 ALTER TABLE `pro_workorder` DISABLE KEYS */;
INSERT INTO `pro_workorder` VALUES (200,'WO20240930001','测试工单001','SELF','MANUAL','',1,'P001','测试产品','规格A','PCS','个','BATCH001',100.00,0.00,0.00,0.00,1,'C001','测试客户',NULL,NULL,NULL,'2024-09-30 00:00:00',0,'0',NULL,NULL,'PREPARE','测试工单',NULL,NULL,NULL,NULL,'admin','2025-10-01 01:40:50','',NULL);
/*!40000 ALTER TABLE `pro_workorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'å‚æ•°ä¸»é”®',
  `config_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å‚æ•°åç§°',
  `config_key` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å‚æ•°é”®å',
  `config_value` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å‚æ•°é”®å€¼',
  `config_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT 'N' COMMENT 'ç³»ç»Ÿå†…ç½®ï¼ˆYæ˜¯ Nå¦ï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='å‚æ•°é…ç½®è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'ä¸»æ¡†æž¶é¡µ-é»˜è®¤çš®è‚¤æ ·å¼åç§°','sys.index.skinName','skin-blue','Y','admin','2025-09-30 03:52:52','',NULL,'è“è‰² skin-blueã€ç»¿è‰² skin-greenã€ç´«è‰² skin-purpleã€çº¢è‰² skin-redã€é»„è‰² skin-yellow'),(2,'ç”¨æˆ·ç®¡ç†-è´¦å·åˆå§‹å¯†ç ','sys.user.initPassword','123456','Y','admin','2025-09-30 03:52:52','',NULL,'åˆå§‹åŒ–å¯†ç  123456'),(3,'ä¸»æ¡†æž¶é¡µ-ä¾§è¾¹æ ä¸»é¢˜','sys.index.sideTheme','theme-dark','Y','admin','2025-09-30 03:52:52','',NULL,'æ·±è‰²ä¸»é¢˜theme-darkï¼Œæµ…è‰²ä¸»é¢˜theme-light'),(4,'è´¦å·è‡ªåŠ©-éªŒè¯ç å¼€å…³','sys.account.captchaOnOff','false','Y','admin','2025-09-30 03:52:52','',NULL,'æ˜¯å¦å¼€å¯éªŒè¯ç åŠŸèƒ½ï¼ˆtrueå¼€å¯ï¼Œfalseå…³é—­ï¼‰'),(5,'è´¦å·è‡ªåŠ©-æ˜¯å¦å¼€å¯ç”¨æˆ·æ³¨å†ŒåŠŸèƒ½','sys.account.registerUser','false','Y','admin','2025-09-30 03:52:52','',NULL,'æ˜¯å¦å¼€å¯æ³¨å†Œç”¨æˆ·åŠŸèƒ½ï¼ˆtrueå¼€å¯ï¼Œfalseå…³é—­ï¼‰');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'éƒ¨é—¨id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT 'çˆ¶éƒ¨é—¨id',
  `ancestors` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'ç¥–çº§åˆ—è¡¨',
  `dept_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'éƒ¨é—¨åç§°',
  `order_num` int(4) DEFAULT '0' COMMENT 'æ˜¾ç¤ºé¡ºåº',
  `leader` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'è´Ÿè´£äºº',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'è”ç³»ç”µè¯',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'é‚®ç®±',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'éƒ¨é—¨çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'åˆ é™¤æ ‡å¿—ï¼ˆ0ä»£è¡¨å­˜åœ¨ 2ä»£è¡¨åˆ é™¤ï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='éƒ¨é—¨è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0',NULL,'若依科技',0,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(101,100,'0,100',NULL,'深圳总公司',1,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(102,100,'0,100',NULL,'长沙分公司',2,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(103,101,'0,100,101',NULL,'研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(104,101,'0,100,101',NULL,'市场部门',2,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(105,101,'0,100,101',NULL,'测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(106,101,'0,100,101',NULL,'财务部门',4,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(107,101,'0,100,101',NULL,'运维部门',5,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(108,102,'0,100,102',NULL,'市场部门',1,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL),(109,102,'0,100,102',NULL,'财务部门',2,'è‹¥ä¾','15888888888','ry@qq.com','0','0','admin','2025-09-30 03:52:52','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'å­—å…¸ç¼–ç ',
  `dict_sort` int(4) DEFAULT '0' COMMENT 'å­—å…¸æŽ’åº',
  `dict_label` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å­—å…¸æ ‡ç­¾',
  `dict_value` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å­—å…¸é”®å€¼',
  `dict_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å­—å…¸ç±»åž‹',
  `css_class` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ ·å¼å±žæ€§ï¼ˆå…¶ä»–æ ·å¼æ‰©å±•ï¼‰',
  `list_class` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'è¡¨æ ¼å›žæ˜¾æ ·å¼',
  `is_default` char(1) COLLATE utf8mb4_unicode_ci DEFAULT 'N' COMMENT 'æ˜¯å¦é»˜è®¤ï¼ˆYæ˜¯ Nå¦ï¼‰',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='å­—å…¸æ•°æ®è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'ç”·','0','sys_user_sex','','','Y','0','admin','2025-09-30 03:52:52','',NULL,'æ€§åˆ«ç”·'),(2,2,'å¥³','1','sys_user_sex','','','N','0','admin','2025-09-30 03:52:52','',NULL,'æ€§åˆ«å¥³'),(3,3,'æœªçŸ¥','2','sys_user_sex','','','N','0','admin','2025-09-30 03:52:52','',NULL,'æ€§åˆ«æœªçŸ¥'),(4,1,'æ˜¾ç¤º','0','sys_show_hide','','primary','Y','0','admin','2025-09-30 03:52:52','',NULL,'æ˜¾ç¤ºèœå•'),(5,2,'éšè—','1','sys_show_hide','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'éšè—èœå•'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2025-09-30 03:52:52','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'停用状态'),(8,1,'æ­£å¸¸','0','sys_job_status','','primary','Y','0','admin','2025-09-30 03:52:52','',NULL,'æ­£å¸¸çŠ¶æ€'),(9,2,'æš‚åœ','1','sys_job_status','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'åœç”¨çŠ¶æ€'),(10,1,'é»˜è®¤','DEFAULT','sys_job_group','','','Y','0','admin','2025-09-30 03:52:52','',NULL,'é»˜è®¤åˆ†ç»„'),(11,2,'ç³»ç»Ÿ','SYSTEM','sys_job_group','','','N','0','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿåˆ†ç»„'),(12,1,'æ˜¯','Y','sys_yes_no','','primary','Y','0','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿé»˜è®¤æ˜¯'),(13,2,'å¦','N','sys_yes_no','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿé»˜è®¤å¦'),(14,1,'é€šçŸ¥','1','sys_notice_type','','warning','Y','0','admin','2025-09-30 03:52:52','',NULL,'é€šçŸ¥'),(15,2,'å…¬å‘Š','2','sys_notice_type','','success','N','0','admin','2025-09-30 03:52:52','',NULL,'å…¬å‘Š'),(16,1,'æ­£å¸¸','0','sys_notice_status','','primary','Y','0','admin','2025-09-30 03:52:52','',NULL,'æ­£å¸¸çŠ¶æ€'),(17,2,'å…³é—­','1','sys_notice_status','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'å…³é—­çŠ¶æ€'),(18,1,'æ–°å¢ž','1','sys_oper_type','','info','N','0','admin','2025-09-30 03:52:52','',NULL,'æ–°å¢žæ“ä½œ'),(19,2,'ä¿®æ”¹','2','sys_oper_type','','info','N','0','admin','2025-09-30 03:52:52','',NULL,'ä¿®æ”¹æ“ä½œ'),(20,3,'åˆ é™¤','3','sys_oper_type','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'åˆ é™¤æ“ä½œ'),(21,4,'æŽˆæƒ','4','sys_oper_type','','primary','N','0','admin','2025-09-30 03:52:52','',NULL,'æŽˆæƒæ“ä½œ'),(22,5,'å¯¼å‡º','5','sys_oper_type','','warning','N','0','admin','2025-09-30 03:52:52','',NULL,'å¯¼å‡ºæ“ä½œ'),(23,6,'å¯¼å…¥','6','sys_oper_type','','warning','N','0','admin','2025-09-30 03:52:52','',NULL,'å¯¼å…¥æ“ä½œ'),(24,7,'å¼ºé€€','7','sys_oper_type','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'å¼ºé€€æ“ä½œ'),(25,8,'ç”Ÿæˆä»£ç ','8','sys_oper_type','','warning','N','0','admin','2025-09-30 03:52:52','',NULL,'ç”Ÿæˆæ“ä½œ'),(26,9,'æ¸…ç©ºæ•°æ®','9','sys_oper_type','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'æ¸…ç©ºæ“ä½œ'),(27,1,'æˆåŠŸ','0','sys_common_status','','primary','N','0','admin','2025-09-30 03:52:52','',NULL,'æ­£å¸¸çŠ¶æ€'),(28,2,'å¤±è´¥','1','sys_common_status','','danger','N','0','admin','2025-09-30 03:52:52','',NULL,'åœç”¨çŠ¶æ€');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'å­—å…¸ä¸»é”®',
  `dict_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å­—å…¸åç§°',
  `dict_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å­—å…¸ç±»åž‹',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='å­—å…¸ç±»åž‹è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'ç”¨æˆ·æ€§åˆ«','sys_user_sex','0','admin','2025-09-30 03:52:52','',NULL,'ç”¨æˆ·æ€§åˆ«åˆ—è¡¨'),(2,'èœå•çŠ¶æ€','sys_show_hide','0','admin','2025-09-30 03:52:52','',NULL,'èœå•çŠ¶æ€åˆ—è¡¨'),(3,'ç³»ç»Ÿå¼€å…³','sys_normal_disable','0','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿå¼€å…³åˆ—è¡¨'),(4,'ä»»åŠ¡çŠ¶æ€','sys_job_status','0','admin','2025-09-30 03:52:52','',NULL,'ä»»åŠ¡çŠ¶æ€åˆ—è¡¨'),(5,'ä»»åŠ¡åˆ†ç»„','sys_job_group','0','admin','2025-09-30 03:52:52','',NULL,'ä»»åŠ¡åˆ†ç»„åˆ—è¡¨'),(6,'ç³»ç»Ÿæ˜¯å¦','sys_yes_no','0','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿæ˜¯å¦åˆ—è¡¨'),(7,'é€šçŸ¥ç±»åž‹','sys_notice_type','0','admin','2025-09-30 03:52:52','',NULL,'é€šçŸ¥ç±»åž‹åˆ—è¡¨'),(8,'é€šçŸ¥çŠ¶æ€','sys_notice_status','0','admin','2025-09-30 03:52:52','',NULL,'é€šçŸ¥çŠ¶æ€åˆ—è¡¨'),(9,'æ“ä½œç±»åž‹','sys_oper_type','0','admin','2025-09-30 03:52:52','',NULL,'æ“ä½œç±»åž‹åˆ—è¡¨'),(10,'ç³»ç»ŸçŠ¶æ€','sys_common_status','0','admin','2025-09-30 03:52:52','',NULL,'ç™»å½•çŠ¶æ€åˆ—è¡¨');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ä»»åŠ¡ID',
  `job_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'ä»»åŠ¡åç§°',
  `job_group` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'DEFAULT' COMMENT 'ä»»åŠ¡ç»„å',
  `invoke_target` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'è°ƒç”¨ç›®æ ‡å­—ç¬¦ä¸²',
  `cron_expression` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'cronæ‰§è¡Œè¡¨è¾¾å¼',
  `misfire_policy` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '3' COMMENT 'è®¡åˆ’æ‰§è¡Œé”™è¯¯ç­–ç•¥ï¼ˆ1ç«‹å³æ‰§è¡Œ 2æ‰§è¡Œä¸€æ¬¡ 3æ”¾å¼ƒæ‰§è¡Œï¼‰',
  `concurrent` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT 'æ˜¯å¦å¹¶å‘æ‰§è¡Œï¼ˆ0å…è®¸ 1ç¦æ­¢ï¼‰',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1æš‚åœï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å¤‡æ³¨ä¿¡æ¯',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='å®šæ—¶ä»»åŠ¡è°ƒåº¦è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2025-09-30 03:52:52','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2025-09-30 03:52:52','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2025-09-30 03:52:52','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ä»»åŠ¡æ—¥å¿—ID',
  `job_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ä»»åŠ¡åç§°',
  `job_group` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ä»»åŠ¡ç»„å',
  `invoke_target` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'è°ƒç”¨ç›®æ ‡å­—ç¬¦ä¸²',
  `job_message` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æ—¥å¿—ä¿¡æ¯',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'æ‰§è¡ŒçŠ¶æ€ï¼ˆ0æ­£å¸¸ 1å¤±è´¥ï¼‰',
  `exception_info` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å¼‚å¸¸ä¿¡æ¯',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='å®šæ—¶ä»»åŠ¡è°ƒåº¦æ—¥å¿—è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'è®¿é—®ID',
  `user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'ç”¨æˆ·è´¦å·',
  `ipaddr` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'ç™»å½•IPåœ°å€',
  `login_location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'ç™»å½•åœ°ç‚¹',
  `browser` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æµè§ˆå™¨ç±»åž‹',
  `os` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ“ä½œç³»ç»Ÿ',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'ç™»å½•çŠ¶æ€ï¼ˆ0æˆåŠŸ 1å¤±è´¥ï¼‰',
  `msg` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æç¤ºæ¶ˆæ¯',
  `login_time` datetime DEFAULT NULL COMMENT 'è®¿é—®æ—¶é—´',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ç³»ç»Ÿè®¿é—®è®°å½•';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (1,'testuser','172.22.0.1','内网IP','Chrome 14','Windows 10','1','登录用户：testuser 不存在','2025-10-01 01:23:47'),(2,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','1','验证码已失效','2025-10-01 01:27:03'),(3,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','1','验证码错误','2025-10-01 01:27:20'),(4,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','1','验证码已失效','2025-10-01 01:27:39'),(5,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','0','登录成功','2025-10-01 01:28:14'),(6,'admin','172.22.0.1','内网IP','Chrome 14','Windows 10','0','登录成功','2025-10-01 01:28:44'),(7,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','1','验证码已失效','2025-10-01 01:31:00'),(8,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','1','验证码已失效','2025-10-01 01:31:11'),(9,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','0','登录成功','2025-10-01 01:31:48'),(10,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','0','登录成功','2025-10-01 01:33:12'),(11,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','0','登录成功','2025-10-01 01:34:23'),(12,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','0','登录成功','2025-10-01 01:35:36'),(13,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','0','登录成功','2025-10-01 01:36:58'),(14,'admin','172.22.0.1','内网IP','Chrome 14','Windows 10','0','退出成功','2025-10-01 01:38:49'),(15,'admin','172.22.0.1','内网IP','Chrome 14','Windows 10','0','登录成功','2025-10-01 01:38:55'),(16,'admin','172.22.0.1','内网IP','Downloading Tool','Unknown','0','登录成功','2025-10-01 01:50:07'),(17,'admin','172.22.0.1','内网IP','Chrome 14','Windows 10','0','退出成功','2025-10-01 01:53:04'),(18,'admin','172.22.0.1','内网IP','Chrome 14','Windows 10','0','登录成功','2025-10-01 01:53:11');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'èœå•ID',
  `menu_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'èœå•åç§°',
  `parent_id` bigint(20) DEFAULT '0' COMMENT 'çˆ¶èœå•ID',
  `order_num` int(4) DEFAULT '0' COMMENT 'æ˜¾ç¤ºé¡ºåº',
  `path` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'è·¯ç”±åœ°å€',
  `component` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ç»„ä»¶è·¯å¾„',
  `query` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'è·¯ç”±å‚æ•°',
  `is_frame` int(1) DEFAULT '1' COMMENT 'æ˜¯å¦ä¸ºå¤–é“¾ï¼ˆ0æ˜¯ 1å¦ï¼‰',
  `is_cache` int(1) DEFAULT '0' COMMENT 'æ˜¯å¦ç¼“å­˜ï¼ˆ0ç¼“å­˜ 1ä¸ç¼“å­˜ï¼‰',
  `menu_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'èœå•ç±»åž‹ï¼ˆMç›®å½• Cèœå• FæŒ‰é’®ï¼‰',
  `visible` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'èœå•çŠ¶æ€ï¼ˆ0æ˜¾ç¤º 1éšè—ï¼‰',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'èœå•çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `perms` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'æƒé™æ ‡è¯†',
  `icon` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '#' COMMENT 'èœå•å›¾æ ‡',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1061 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='èœå•æƒé™è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,1,'system',NULL,'',1,0,'M','0','0','','system','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿç®¡ç†ç›®å½•'),(2,'系统监控',0,2,'monitor',NULL,'',1,0,'M','0','0','','monitor','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿç›‘æŽ§ç›®å½•'),(3,'系统工具',0,3,'tool',NULL,'',1,0,'M','0','0','','tool','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»Ÿå·¥å…·ç›®å½•'),(4,'苦糖果MES官网',0,4,'http://029tec.com',NULL,'',0,0,'M','0','0','','guide','admin','2025-09-30 03:52:52','',NULL,'è‹¦ç³–æžœMESå®˜ç½‘åœ°å€'),(100,'用户管理',1,1,'user','system/user/index','',1,0,'C','0','0','system:user:list','user','admin','2025-09-30 03:52:52','',NULL,'ç”¨æˆ·ç®¡ç†èœå•'),(101,'角色管理',1,2,'role','system/role/index','',1,0,'C','0','0','system:role:list','peoples','admin','2025-09-30 03:52:52','',NULL,'è§’è‰²ç®¡ç†èœå•'),(102,'菜单管理',1,3,'menu','system/menu/index','',1,0,'C','0','0','system:menu:list','tree-table','admin','2025-09-30 03:52:52','',NULL,'èœå•ç®¡ç†èœå•'),(103,'部门管理',1,4,'dept','system/dept/index','',1,0,'C','0','0','system:dept:list','tree','admin','2025-09-30 03:52:52','',NULL,'éƒ¨é—¨ç®¡ç†èœå•'),(104,'岗位管理',1,5,'post','system/post/index','',1,0,'C','0','0','system:post:list','post','admin','2025-09-30 03:52:52','',NULL,'å²—ä½ç®¡ç†èœå•'),(105,'字典管理',1,6,'dict','system/dict/index','',1,0,'C','0','0','system:dict:list','dict','admin','2025-09-30 03:52:52','',NULL,'å­—å…¸ç®¡ç†èœå•'),(106,'参数设置',1,7,'config','system/config/index','',1,0,'C','0','0','system:config:list','edit','admin','2025-09-30 03:52:52','',NULL,'å‚æ•°è®¾ç½®èœå•'),(107,'通知公告',1,8,'notice','system/notice/index','',1,0,'C','0','0','system:notice:list','message','admin','2025-09-30 03:52:52','',NULL,'é€šçŸ¥å…¬å‘Šèœå•'),(108,'日志管理',1,9,'log','','',1,0,'M','0','0','','log','admin','2025-09-30 03:52:52','',NULL,'æ—¥å¿—ç®¡ç†èœå•'),(109,'在线用户',2,1,'online','monitor/online/index','',1,0,'C','0','0','monitor:online:list','online','admin','2025-09-30 03:52:52','',NULL,'åœ¨çº¿ç”¨æˆ·èœå•'),(110,'定时任务',2,2,'job','monitor/job/index','',1,0,'C','0','0','monitor:job:list','job','admin','2025-09-30 03:52:52','',NULL,'å®šæ—¶ä»»åŠ¡èœå•'),(111,'数据监控',2,3,'druid','monitor/druid/index','',1,0,'C','0','0','monitor:druid:list','druid','admin','2025-09-30 03:52:52','',NULL,'æ•°æ®ç›‘æŽ§èœå•'),(112,'服务监控',2,4,'server','monitor/server/index','',1,0,'C','0','0','monitor:server:list','server','admin','2025-09-30 03:52:52','',NULL,'æœåŠ¡ç›‘æŽ§èœå•'),(113,'缓存监控',2,5,'cache','monitor/cache/index','',1,0,'C','0','0','monitor:cache:list','redis','admin','2025-09-30 03:52:52','',NULL,'ç¼“å­˜ç›‘æŽ§èœå•'),(114,'表单构建',3,1,'build','tool/build/index','',1,0,'C','0','0','tool:build:list','build','admin','2025-09-30 03:52:52','',NULL,'è¡¨å•æž„å»ºèœå•'),(115,'代码生成',3,2,'gen','tool/gen/index','',1,0,'C','0','0','tool:gen:list','code','admin','2025-09-30 03:52:52','',NULL,'ä»£ç ç”Ÿæˆèœå•'),(116,'系统接口',3,3,'swagger','tool/swagger/index','',1,0,'C','0','0','tool:swagger:list','swagger','admin','2025-09-30 03:52:52','',NULL,'ç³»ç»ŸæŽ¥å£èœå•'),(500,'操作日志',108,1,'operlog','monitor/operlog/index','',1,0,'C','0','0','monitor:operlog:list','form','admin','2025-09-30 03:52:52','',NULL,'æ“ä½œæ—¥å¿—èœå•'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index','',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2025-09-30 03:52:52','',NULL,'ç™»å½•æ—¥å¿—èœå•'),(1001,'用户查询',100,1,'','','',1,0,'F','0','0','system:user:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1002,'用户新增',100,2,'','','',1,0,'F','0','0','system:user:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1003,'用户修改',100,3,'','','',1,0,'F','0','0','system:user:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1004,'用户删除',100,4,'','','',1,0,'F','0','0','system:user:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1005,'用户导出',100,5,'','','',1,0,'F','0','0','system:user:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1006,'用户导入',100,6,'','','',1,0,'F','0','0','system:user:import','#','admin','2025-09-30 03:52:52','',NULL,''),(1007,'重置密码',100,7,'','','',1,0,'F','0','0','system:user:resetPwd','#','admin','2025-09-30 03:52:52','',NULL,''),(1008,'角色查询',101,1,'','','',1,0,'F','0','0','system:role:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1009,'角色新增',101,2,'','','',1,0,'F','0','0','system:role:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1010,'角色修改',101,3,'','','',1,0,'F','0','0','system:role:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1011,'角色删除',101,4,'','','',1,0,'F','0','0','system:role:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1012,'角色导出',101,5,'','','',1,0,'F','0','0','system:role:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1013,'菜单查询',102,1,'','','',1,0,'F','0','0','system:menu:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1014,'菜单新增',102,2,'','','',1,0,'F','0','0','system:menu:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1015,'菜单修改',102,3,'','','',1,0,'F','0','0','system:menu:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1016,'菜单删除',102,4,'','','',1,0,'F','0','0','system:menu:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1017,'部门查询',103,1,'','','',1,0,'F','0','0','system:dept:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1018,'部门新增',103,2,'','','',1,0,'F','0','0','system:dept:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1019,'部门修改',103,3,'','','',1,0,'F','0','0','system:dept:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1020,'部门删除',103,4,'','','',1,0,'F','0','0','system:dept:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1021,'岗位查询',104,1,'','','',1,0,'F','0','0','system:post:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1022,'岗位新增',104,2,'','','',1,0,'F','0','0','system:post:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1023,'岗位修改',104,3,'','','',1,0,'F','0','0','system:post:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1024,'岗位删除',104,4,'','','',1,0,'F','0','0','system:post:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1025,'岗位导出',104,5,'','','',1,0,'F','0','0','system:post:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1026,'字典查询',105,1,'#','','',1,0,'F','0','0','system:dict:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1027,'字典新增',105,2,'#','','',1,0,'F','0','0','system:dict:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1028,'字典修改',105,3,'#','','',1,0,'F','0','0','system:dict:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1029,'字典删除',105,4,'#','','',1,0,'F','0','0','system:dict:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1030,'字典导出',105,5,'#','','',1,0,'F','0','0','system:dict:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1031,'参数查询',106,1,'#','','',1,0,'F','0','0','system:config:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1032,'参数新增',106,2,'#','','',1,0,'F','0','0','system:config:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1033,'参数修改',106,3,'#','','',1,0,'F','0','0','system:config:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1034,'参数删除',106,4,'#','','',1,0,'F','0','0','system:config:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1035,'参数导出',106,5,'#','','',1,0,'F','0','0','system:config:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1036,'公告查询',107,1,'#','','',1,0,'F','0','0','system:notice:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1037,'公告新增',107,2,'#','','',1,0,'F','0','0','system:notice:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1038,'公告修改',107,3,'#','','',1,0,'F','0','0','system:notice:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1039,'公告删除',107,4,'#','','',1,0,'F','0','0','system:notice:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1040,'操作查询',500,1,'#','','',1,0,'F','0','0','monitor:operlog:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1041,'操作删除',500,2,'#','','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1042,'日志导出',500,4,'#','','',1,0,'F','0','0','monitor:operlog:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1043,'登录查询',501,1,'#','','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1044,'登录删除',501,2,'#','','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1045,'日志导出',501,3,'#','','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1046,'在线查询',109,1,'#','','',1,0,'F','0','0','monitor:online:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1047,'批量强退',109,2,'#','','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2025-09-30 03:52:52','',NULL,''),(1048,'单条强退',109,3,'#','','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2025-09-30 03:52:52','',NULL,''),(1049,'任务查询',110,1,'#','','',1,0,'F','0','0','monitor:job:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1050,'任务新增',110,2,'#','','',1,0,'F','0','0','monitor:job:add','#','admin','2025-09-30 03:52:52','',NULL,''),(1051,'任务修改',110,3,'#','','',1,0,'F','0','0','monitor:job:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1052,'任务删除',110,4,'#','','',1,0,'F','0','0','monitor:job:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1053,'状态修改',110,5,'#','','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2025-09-30 03:52:52','',NULL,''),(1054,'任务导出',110,7,'#','','',1,0,'F','0','0','monitor:job:export','#','admin','2025-09-30 03:52:52','',NULL,''),(1055,'生成查询',115,1,'#','','',1,0,'F','0','0','tool:gen:query','#','admin','2025-09-30 03:52:52','',NULL,''),(1056,'生成修改',115,2,'#','','',1,0,'F','0','0','tool:gen:edit','#','admin','2025-09-30 03:52:52','',NULL,''),(1057,'生成删除',115,3,'#','','',1,0,'F','0','0','tool:gen:remove','#','admin','2025-09-30 03:52:52','',NULL,''),(1058,'导入代码',115,2,'#','','',1,0,'F','0','0','tool:gen:import','#','admin','2025-09-30 03:52:52','',NULL,''),(1059,'预览代码',115,4,'#','','',1,0,'F','0','0','tool:gen:preview','#','admin','2025-09-30 03:52:52','',NULL,''),(1060,'生成代码',115,5,'#','','',1,0,'F','0','0','tool:gen:code','#','admin','2025-09-30 03:52:52','',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'å…¬å‘ŠID',
  `notice_title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'å…¬å‘Šæ ‡é¢˜',
  `notice_type` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'å…¬å‘Šç±»åž‹ï¼ˆ1é€šçŸ¥ 2å…¬å‘Šï¼‰',
  `notice_content` longblob COMMENT 'å…¬å‘Šå†…å®¹',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'å…¬å‘ŠçŠ¶æ€ï¼ˆ0æ­£å¸¸ 1å…³é—­ï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='é€šçŸ¥å…¬å‘Šè¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'æ¸©é¦¨æé†’ï¼š2018-07-01 è‹¥ä¾æ–°ç‰ˆæœ¬å‘å¸ƒå•¦','2',_binary '新版本内容','0','admin','2025-09-30 03:52:52','',NULL,'ç®¡ç†å‘˜'),(2,'ç»´æŠ¤é€šçŸ¥ï¼š2018-07-01 è‹¥ä¾ç³»ç»Ÿå‡Œæ™¨ç»´æŠ¤','1',_binary '维护内容','0','admin','2025-09-30 03:52:52','',NULL,'ç®¡ç†å‘˜');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'æ—¥å¿—ä¸»é”®',
  `title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ¨¡å—æ ‡é¢˜',
  `business_type` int(2) DEFAULT '0' COMMENT 'ä¸šåŠ¡ç±»åž‹ï¼ˆ0å…¶å®ƒ 1æ–°å¢ž 2ä¿®æ”¹ 3åˆ é™¤ï¼‰',
  `method` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ–¹æ³•åç§°',
  `request_method` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'è¯·æ±‚æ–¹å¼',
  `operator_type` int(1) DEFAULT '0' COMMENT 'æ“ä½œç±»åˆ«ï¼ˆ0å…¶å®ƒ 1åŽå°ç”¨æˆ· 2æ‰‹æœºç«¯ç”¨æˆ·ï¼‰',
  `oper_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ“ä½œäººå‘˜',
  `dept_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'éƒ¨é—¨åç§°',
  `oper_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'è¯·æ±‚URL',
  `oper_ip` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'ä¸»æœºåœ°å€',
  `oper_location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ“ä½œåœ°ç‚¹',
  `oper_param` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'è¯·æ±‚å‚æ•°',
  `json_result` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'è¿”å›žå‚æ•°',
  `status` int(1) DEFAULT '0' COMMENT 'æ“ä½œçŠ¶æ€ï¼ˆ0æ­£å¸¸ 1å¼‚å¸¸ï¼‰',
  `error_msg` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'é”™è¯¯æ¶ˆæ¯',
  `oper_time` datetime DEFAULT NULL COMMENT 'æ“ä½œæ—¶é—´',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='æ“ä½œæ—¥å¿—è®°å½•';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (1,'字典类型',9,'com.ktg.web.controller.system.SysDictTypeController.refreshCache()','DELETE',1,'admin',NULL,'/system/dict/type/refreshCache','172.22.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-10-01 01:54:47');
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'å²—ä½ID',
  `post_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'å²—ä½ç¼–ç ',
  `post_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'å²—ä½åç§°',
  `post_sort` int(4) NOT NULL COMMENT 'æ˜¾ç¤ºé¡ºåº',
  `status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='å²—ä½ä¿¡æ¯è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','è‘£äº‹é•¿',1,'0','admin','2025-09-30 03:52:52','',NULL,''),(2,'se','é¡¹ç›®ç»ç†',2,'0','admin','2025-09-30 03:52:52','',NULL,''),(3,'hr','äººåŠ›èµ„æº',3,'0','admin','2025-09-30 03:52:52','',NULL,''),(4,'user','æ™®é€šå‘˜å·¥',4,'0','admin','2025-09-30 03:52:52','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'è§’è‰²ID',
  `role_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'è§’è‰²åç§°',
  `role_key` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'è§’è‰²æƒé™å­—ç¬¦ä¸²',
  `role_sort` int(4) NOT NULL COMMENT 'æ˜¾ç¤ºé¡ºåº',
  `data_scope` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT 'æ•°æ®èŒƒå›´ï¼ˆ1ï¼šå…¨éƒ¨æ•°æ®æƒé™ 2ï¼šè‡ªå®šæ•°æ®æƒé™ 3ï¼šæœ¬éƒ¨é—¨æ•°æ®æƒé™ 4ï¼šæœ¬éƒ¨é—¨åŠä»¥ä¸‹æ•°æ®æƒé™ï¼‰',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT 'èœå•æ ‘é€‰æ‹©é¡¹æ˜¯å¦å…³è”æ˜¾ç¤º',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT 'éƒ¨é—¨æ ‘é€‰æ‹©é¡¹æ˜¯å¦å…³è”æ˜¾ç¤º',
  `status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'è§’è‰²çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'åˆ é™¤æ ‡å¿—ï¼ˆ0ä»£è¡¨å­˜åœ¨ 2ä»£è¡¨åˆ é™¤ï¼‰',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='è§’è‰²ä¿¡æ¯è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2025-09-30 03:52:52','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2025-09-30 03:52:52','',NULL,'普通角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT 'è§’è‰²ID',
  `dept_id` bigint(20) NOT NULL COMMENT 'éƒ¨é—¨ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='è§’è‰²å’Œéƒ¨é—¨å…³è”è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT 'è§’è‰²ID',
  `menu_id` bigint(20) NOT NULL COMMENT 'èœå•ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='è§’è‰²å’Œèœå•å…³è”è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ç”¨æˆ·ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT 'éƒ¨é—¨ID',
  `user_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ç”¨æˆ·è´¦å·',
  `nick_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ç”¨æˆ·æ˜µç§°',
  `user_type` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '00' COMMENT 'ç”¨æˆ·ç±»åž‹ï¼ˆ00ç³»ç»Ÿç”¨æˆ·ï¼‰',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'ç”¨æˆ·é‚®ç®±',
  `phonenumber` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ‰‹æœºå·ç ',
  `sex` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'ç”¨æˆ·æ€§åˆ«ï¼ˆ0ç”· 1å¥³ 2æœªçŸ¥ï¼‰',
  `avatar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å¤´åƒåœ°å€',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'å¯†ç ',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'å¸å·çŠ¶æ€ï¼ˆ0æ­£å¸¸ 1åœç”¨ï¼‰',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'åˆ é™¤æ ‡å¿—ï¼ˆ0ä»£è¡¨å­˜åœ¨ 2ä»£è¡¨åˆ é™¤ï¼‰',
  `login_ip` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æœ€åŽç™»å½•IP',
  `login_date` datetime DEFAULT NULL COMMENT 'æœ€åŽç™»å½•æ—¶é—´',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'åˆ›å»ºè€…',
  `create_time` datetime DEFAULT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'æ›´æ–°è€…',
  `update_time` datetime DEFAULT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'å¤‡æ³¨',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ç”¨æˆ·ä¿¡æ¯è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','若依','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','172.22.0.1','2025-10-01 01:53:12','admin','2025-09-30 03:52:52','','2025-10-01 01:53:11','管理员'),(2,105,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-09-30 03:52:52','admin','2025-09-30 03:52:52','',NULL,'测试员');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT 'ç”¨æˆ·ID',
  `post_id` bigint(20) NOT NULL COMMENT 'å²—ä½ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ç”¨æˆ·ä¸Žå²—ä½å…³è”è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT 'ç”¨æˆ·ID',
  `role_id` bigint(20) NOT NULL COMMENT 'è§’è‰²ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ç”¨æˆ·å’Œè§’è‰²å…³è”è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-30 18:08:53
