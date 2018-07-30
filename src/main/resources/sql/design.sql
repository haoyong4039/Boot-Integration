/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : design

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-06-02 16:35:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for design_role
-- ----------------------------
DROP TABLE IF EXISTS `design_role`;
CREATE TABLE `design_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT '1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of design_role
-- ----------------------------
INSERT INTO `design_role` VALUES ('1', 'ROLE_ADMIN', '1', '2018-05-17 11:29:37');
INSERT INTO `design_role` VALUES ('2', 'ROLE_USER', '1', '2018-05-17 11:30:09');

-- ----------------------------
-- Table structure for design_user
-- ----------------------------
DROP TABLE IF EXISTS `design_user`;
CREATE TABLE `design_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT '1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of design_user
-- ----------------------------
INSERT INTO `design_user` VALUES ('1', 'haoyong', 'haoyong', '1', '2018-05-17 11:30:50');
INSERT INTO `design_user` VALUES ('2', 'yangkai', 'yangkai', '1', '2018-05-17 11:30:50');

-- ----------------------------
-- Table structure for design_user_role
-- ----------------------------
DROP TABLE IF EXISTS `design_user_role`;
CREATE TABLE `design_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `version` bigint(20) DEFAULT '1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of design_user_role
-- ----------------------------
INSERT INTO `design_user_role` VALUES ('1', '1', '1', '1', '2018-05-17 11:31:32');
INSERT INTO `design_user_role` VALUES ('2', '1', '2', '1', '2018-05-17 11:31:32');
INSERT INTO `design_user_role` VALUES ('3', '2', '2', '1', '2018-05-17 13:12:34');
