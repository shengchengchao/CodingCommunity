/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80014
Source Host           : localhost:3306
Source Database       : community

Target Server Type    : MYSQL
Target Server Version : 80014
File Encoding         : 65001

Date: 2020-02-05 14:24:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ad
-- ----------------------------
DROP TABLE IF EXISTS `ad`;
CREATE TABLE `ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL,
  `url` varchar(512) DEFAULT NULL,
  `image` varchar(256) DEFAULT NULL,
  `gmt_start` bigint(20) DEFAULT NULL,
  `gmt_end` bigint(20) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `pos` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ad
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `commentator` bigint(20) DEFAULT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  `like_count` bigint(20) DEFAULT '0',
  `content` varchar(1024) DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '34', '1', '49709991', '1577261746042', '1577261746042', '0', '餐擦啊', '0');
INSERT INTO `comment` VALUES ('2', '34', '1', '49709991', '1577325077739', '1577325077739', '0', 'sdadasdada', '3');
INSERT INTO `comment` VALUES ('3', '2', '2', '49709991', '1577342807377', '1577342807377', '0', 'asdadaa', '0');
INSERT INTO `comment` VALUES ('4', '2', '2', '49709991', '1577342895400', '1577342895400', '0', 'dadada', '0');
INSERT INTO `comment` VALUES ('5', '34', '1', '49709991', '1577342968456', '1577342968456', '0', 'dadada', '0');
INSERT INTO `comment` VALUES ('7', '2', '2', '49709991', '1577409171693', '1577409171693', '0', '测试', null);
INSERT INTO `comment` VALUES ('8', '59', '1', '49709991', '1577612961760', '1577612961760', '0', 'cfscscs', null);
INSERT INTO `comment` VALUES ('9', '58', '1', '49709991', '1577625054591', '1577625054591', '0', '出生地方式服务收费', null);
INSERT INTO `comment` VALUES ('10', '9', '2', '49709991', '1577625061339', '1577625061339', '0', '个人隔热条个人', null);
INSERT INTO `comment` VALUES ('11', '57', '1', '49709991', '1577625077599', '1577625077599', '0', '为台湾而台湾', null);
INSERT INTO `comment` VALUES ('12', '11', '2', '49709991', '1577625082850', '1577625082850', '0', '服务而让他问题为', null);
INSERT INTO `comment` VALUES ('13', '63', '1', '49709991', '1580794342722', '1580794342722', '0', '使用github下载', null);

-- ----------------------------
-- Table structure for nav
-- ----------------------------
DROP TABLE IF EXISTS `nav`;
CREATE TABLE `nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `url` varchar(256) DEFAULT NULL,
  `priority` int(11) DEFAULT '0',
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of nav
-- ----------------------------

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `notifier` bigint(20) NOT NULL,
  `receiver` bigint(20) NOT NULL,
  `outerid` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `notifier_name` varchar(100) DEFAULT NULL,
  `outer_title` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES ('1', '49709991', '49709991', '59', '1', '1577612961940', '4', 'codeStudent', 'mysql');
INSERT INTO `notification` VALUES ('2', '49709991', '49709991', '58', '1', '1577625054850', '4', 'codeStudent', 'mybatis');
INSERT INTO `notification` VALUES ('3', '49709991', '49709991', '58', '2', '1577625061420', '4', 'codeStudent', '出生地方式服务收费');
INSERT INTO `notification` VALUES ('4', '49709991', '49709991', '57', '1', '1577625077612', '4', 'codeStudent', 'spring MVC');
INSERT INTO `notification` VALUES ('5', '49709991', '49709991', '57', '2', '1577625082870', '4', 'codeStudent', '为台湾而台湾');
INSERT INTO `notification` VALUES ('6', '49709991', '49709991', '63', '1', '1580794342733', '4', 'codeStudent', '如何下载elasticsearch');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0',
  `view_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `tag` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('34', '1', '2222', '1576654498014', '1577001363373', '49709991', '3', '38', '0', 'cei');
INSERT INTO `question` VALUES ('48', '1', '21', '1576654534522', '1576906765512', '49709991', '0', '0', '0', 'ce');
INSERT INTO `question` VALUES ('53', '231', '2313', '1576909350941', '1576909350941', '49709991', '0', '2', '0', '2313');
INSERT INTO `question` VALUES ('54', 'spring boot', 'spring boot', '1577427713473', '1577427713473', '49709991', '0', '13', '0', 'spring boot,spring,java,mybatis');
INSERT INTO `question` VALUES ('55', 'spring', 'spring', '1577427898996', '1577427898996', '49709991', '0', '2', '0', 'spring,mybatis');
INSERT INTO `question` VALUES ('56', 'java', 'java', '1577427913166', '1577427913166', '49709991', '0', '2', '0', 'java');
INSERT INTO `question` VALUES ('57', 'spring MVC', 'spring MVC', '1577427930131', '1577427930131', '49709991', '0', '6', '0', 'spring');
INSERT INTO `question` VALUES ('58', 'mybatis', 'mybatis', '1577429074627', '1577429074627', '49709991', '0', '8', '0', 'mybatis,java');
INSERT INTO `question` VALUES ('59', 'mysql', 'mysql', '1577430611331', '1577430611331', '49709991', '0', '5', '0', 'mysql,h2');
INSERT INTO `question` VALUES ('60', 'java test', '``` \r\nprivate \r\nexception\r\n```', '1577673801634', '1577673801634', '49709991', '0', '9', '0', 'php');
INSERT INTO `question` VALUES ('62', 'jest client 学习', 'jest client 学习', '1578301880536', '1578301880536', '49709991', '0', '0', '0', 'php');
INSERT INTO `question` VALUES ('63', '如何下载elasticsearch', '如何下载elasticsearch 快速安装', '1580794320112', '1580794320112', '49709991', '0', '3', '0', 'java');
INSERT INTO `question` VALUES ('64', 'elasticsearch head如何跨域', 'elasticsearch head如何跨域 实现本机与云服务器访问', '1580802526199', '1580802526199', '49709991', '0', '0', '0', 'php,html,django,koa');
INSERT INTO `question` VALUES ('65', '图片测试', '![](http://121.36.26.52/49d23361-cef1-4c28-b0a6-d52ea8b5f1bb.png)', '1580883168333', '1580883168333', '49709991', '0', '1', '0', 'javascript');

-- ----------------------------
-- Table structure for tagcache
-- ----------------------------
DROP TABLE IF EXISTS `tagcache`;
CREATE TABLE `tagcache` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `cache` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tagcache
-- ----------------------------
INSERT INTO `tagcache` VALUES ('1', 'program', 'javascript');
INSERT INTO `tagcache` VALUES ('2', 'program', 'php');
INSERT INTO `tagcache` VALUES ('3', 'program', 'css');
INSERT INTO `tagcache` VALUES ('4', 'program', 'html');
INSERT INTO `tagcache` VALUES ('5', 'program', 'html5');
INSERT INTO `tagcache` VALUES ('6', 'program', 'java');
INSERT INTO `tagcache` VALUES ('7', 'program', 'node.js');
INSERT INTO `tagcache` VALUES ('8', 'program', 'python');
INSERT INTO `tagcache` VALUES ('9', 'program', 'c++');
INSERT INTO `tagcache` VALUES ('10', 'program', 'golang');
INSERT INTO `tagcache` VALUES ('11', 'program', 'objective-c');
INSERT INTO `tagcache` VALUES ('12', 'program', 'typescript');
INSERT INTO `tagcache` VALUES ('13', 'program', 'shell');
INSERT INTO `tagcache` VALUES ('14', 'program', 'bash');
INSERT INTO `tagcache` VALUES ('15', 'program', 'c');
INSERT INTO `tagcache` VALUES ('16', 'program', 'less');
INSERT INTO `tagcache` VALUES ('17', 'program', 'asp.net');
INSERT INTO `tagcache` VALUES ('18', 'program', 'lua');
INSERT INTO `tagcache` VALUES ('19', 'program', 'scala');
INSERT INTO `tagcache` VALUES ('20', 'program', 'coffeescript');
INSERT INTO `tagcache` VALUES ('21', 'program', 'actionscript');
INSERT INTO `tagcache` VALUES ('22', 'program', 'swift');
INSERT INTO `tagcache` VALUES ('23', 'program', 'c#');
INSERT INTO `tagcache` VALUES ('24', 'program', 'rust');
INSERT INTO `tagcache` VALUES ('25', 'program', 'erlang');
INSERT INTO `tagcache` VALUES ('26', 'program', 'sass');
INSERT INTO `tagcache` VALUES ('27', 'program', 'ruby');
INSERT INTO `tagcache` VALUES ('28', 'framework', 'laravel');
INSERT INTO `tagcache` VALUES ('29', 'framework', 'spring');
INSERT INTO `tagcache` VALUES ('30', 'program', 'perl');
INSERT INTO `tagcache` VALUES ('31', 'framework', 'express');
INSERT INTO `tagcache` VALUES ('32', 'framework', 'django');
INSERT INTO `tagcache` VALUES ('33', 'framework', 'flask');
INSERT INTO `tagcache` VALUES ('34', 'framework', 'koa');
INSERT INTO `tagcache` VALUES ('35', 'framework', 'laravel');
INSERT INTO `tagcache` VALUES ('36', 'framework', 'yii');
INSERT INTO `tagcache` VALUES ('37', 'framework', 'struts');
INSERT INTO `tagcache` VALUES ('38', 'framework', 'ruby-on-rails');
INSERT INTO `tagcache` VALUES ('39', 'framework', 'tornado');
INSERT INTO `tagcache` VALUES ('40', 'framework', 'yii');
INSERT INTO `tagcache` VALUES ('41', 'server', 'linux');
INSERT INTO `tagcache` VALUES ('42', 'server', 'nginx');
INSERT INTO `tagcache` VALUES ('43', 'server', 'docker');
INSERT INTO `tagcache` VALUES ('44', 'server', 'apache');
INSERT INTO `tagcache` VALUES ('45', 'server', 'ubuntu');
INSERT INTO `tagcache` VALUES ('46', 'server', 'centos');
INSERT INTO `tagcache` VALUES ('47', 'server', 'tomcat');
INSERT INTO `tagcache` VALUES ('48', 'server', 'unix');
INSERT INTO `tagcache` VALUES ('49', 'server', 'hadoop');
INSERT INTO `tagcache` VALUES ('50', 'server', 'windows-server');
INSERT INTO `tagcache` VALUES ('51', 'db', 'mysql');
INSERT INTO `tagcache` VALUES ('52', 'db', 'redis');
INSERT INTO `tagcache` VALUES ('53', 'db', 'mongodb');
INSERT INTO `tagcache` VALUES ('54', 'db', 'sql');
INSERT INTO `tagcache` VALUES ('55', 'db', 'oracle');
INSERT INTO `tagcache` VALUES ('56', 'db', 'sqlite');
INSERT INTO `tagcache` VALUES ('57', 'db', 'oracle');
INSERT INTO `tagcache` VALUES ('58', 'db', 'nosql memcached');
INSERT INTO `tagcache` VALUES ('59', 'db', 'sqlserver');
INSERT INTO `tagcache` VALUES ('60', 'db', 'postgresql');
INSERT INTO `tagcache` VALUES ('61', 'tool', 'git');
INSERT INTO `tagcache` VALUES ('62', 'tool', 'visual-studio-code');
INSERT INTO `tagcache` VALUES ('63', 'tool', 'eclipse');
INSERT INTO `tagcache` VALUES ('64', 'tool', 'maven');
INSERT INTO `tagcache` VALUES ('65', 'tool', 'ide');
INSERT INTO `tagcache` VALUES ('66', 'tool', 'github');
INSERT INTO `tagcache` VALUES ('67', 'tool', 'visual-studio');
INSERT INTO `tagcache` VALUES ('68', 'tool', 'atom emacs');
INSERT INTO `tagcache` VALUES ('69', 'tool', 'textmate');
INSERT INTO `tagcache` VALUES ('70', 'tool', 'hg');
INSERT INTO `tagcache` VALUES ('71', 'tool', 'svn');
INSERT INTO `tagcache` VALUES ('72', 'tool', 'sublime-text');
INSERT INTO `tagcache` VALUES ('73', 'tool', 'vim');
INSERT INTO `tagcache` VALUES ('74', 'tool', 'xcode intellij-idea');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `token` char(50) NOT NULL,
  `create_date` bigint(20) NOT NULL,
  `update_date` bigint(20) NOT NULL,
  `bio` varchar(50) NOT NULL DEFAULT '' COMMENT '爱好',
  `avatar_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('34', '49709991', 'codeStudent', '66e9759d-5a33-4112-b3e8-d61d8b79d1fe', '1580882972490', '1580882972517', 'student', 'https://avatars3.githubusercontent.com/u/49709991?v=4');
