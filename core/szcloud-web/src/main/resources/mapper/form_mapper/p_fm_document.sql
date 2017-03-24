/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.19 : Database - platformcloud
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`platformcloud` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `platformcloud`;

/*Table structure for table `p_fm_document` */

DROP TABLE IF EXISTS `p_fm_document`;

CREATE TABLE `p_fm_document` (
  `ID` varchar(40) NOT NULL,
  `LASTMODIFIED` datetime DEFAULT NULL COMMENT '最后编辑时间',
  `DYNAMICPAGE_NAME` varchar(200) DEFAULT NULL COMMENT '动态表单名称',
  `DYNAMICPAGE_ID` varchar(36) DEFAULT NULL COMMENT '动态表单ID',
  `AUTHOR_ID` bigint(20) DEFAULT NULL,
  `AUTHOR_DEPT_INDEX` varchar(200) DEFAULT NULL,
  `CREATED` datetime DEFAULT NULL COMMENT '创建时间',
  `ISTMP` bit(1) DEFAULT NULL,
  `VERSIONS` int(11) DEFAULT NULL COMMENT '修改次数',
  `PARENT` varchar(40) DEFAULT NULL,
  `SORTID` varchar(200) DEFAULT NULL,
  `STATELABEL` varchar(200) DEFAULT NULL,
  `INITIATOR` varchar(200) DEFAULT NULL COMMENT '发起人/创建人',
  `AUDITDATE` datetime DEFAULT NULL COMMENT '审批时间',
  `AUDITUSER` varchar(200) DEFAULT NULL COMMENT '审批人',
  `AUDITORNAMES` mediumtext,
  `STATE` varchar(200) DEFAULT NULL COMMENT '数据状态',
  `STATEINT` int(11) DEFAULT NULL,
  `LASTMODIFIER` varchar(200) DEFAULT NULL COMMENT '最后编辑人',
  `AUDITORLIST` mediumtext,
  `RECORD_ID` varchar(36) DEFAULT NULL COMMENT '数据库记录ID',
  `INSTANCE_ID` varchar(40) DEFAULT NULL COMMENT '流程实例ID',
  `TABLE_NAME` varchar(200) DEFAULT NULL COMMENT '数据库表的名称',
  `NODE_ID` varchar(40) DEFAULT NULL COMMENT '流程节点ID',
  `WORKFLOW_ID` varchar(40) DEFAULT NULL COMMENT '流程ID',
  `TASK_ID` varchar(40) DEFAULT NULL COMMENT '任务ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `p_fm_document` */

insert  into `p_fm_document`(`ID`,`LASTMODIFIED`,`DYNAMICPAGE_NAME`,`DYNAMICPAGE_ID`,`AUTHOR_ID`,`AUTHOR_DEPT_INDEX`,`CREATED`,`ISTMP`,`VERSIONS`,`PARENT`,`SORTID`,`STATELABEL`,`INITIATOR`,`AUDITDATE`,`AUDITUSER`,`AUDITORNAMES`,`STATE`,`STATEINT`,`LASTMODIFIER`,`AUDITORLIST`,`RECORD_ID`,`INSTANCE_ID`,`TABLE_NAME`,`NODE_ID`,`WORKFLOW_ID`,`TASK_ID`) values ('1591a250-db07-4528-a3f8-b88609807766',NULL,'请假表单','54',NULL,NULL,'2014-12-15 16:30:40','\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'56',NULL,'ask_table',NULL,NULL,NULL),('1ead4543-31dc-4114-a95e-5284f9440ddb',NULL,'请假表单','54',NULL,NULL,'2014-12-15 16:28:01','\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('4b9f9177-c5eb-4f17-b561-725701d72a42',NULL,'请假表单','54',NULL,NULL,'2014-12-15 15:12:00','\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'54',NULL,'ask_table',NULL,NULL,NULL),('b7909588-2633-495d-b2df-cfe91646a821',NULL,'请假表单','54',NULL,NULL,'2014-12-15 16:27:25','\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'55',NULL,'ask_table',NULL,NULL,NULL),('d94c9182-ba1a-4fe0-a39d-c412aa9ae38c',NULL,'请假表单','54',NULL,NULL,'2014-12-15 14:19:02','\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'53',NULL,'ask_table',NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
