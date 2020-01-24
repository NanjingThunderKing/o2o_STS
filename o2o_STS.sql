/*
 Navicat Premium Data Transfer

 Source Server         : WAF__66066
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : o2o

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 24/01/2020 09:44:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area` (
  `area_id` int(2) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) NOT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_area
-- ----------------------------
BEGIN;
INSERT INTO `tb_area` VALUES (2, '东苑', 1, NULL, NULL);
INSERT INTO `tb_area` VALUES (3, '西苑', 2, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_award
-- ----------------------------
DROP TABLE IF EXISTS `tb_award`;
CREATE TABLE `tb_award` (
  `award_id` int(10) NOT NULL AUTO_INCREMENT,
  `award_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `award_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `award_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `point` int(10) NOT NULL DEFAULT '0',
  `priority` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `shop_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`award_id`),
  KEY `fk_award_shop_idx` (`shop_id`),
  CONSTRAINT `fk_award_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_award
-- ----------------------------
BEGIN;
INSERT INTO `tb_award` VALUES (1, '12SS工装衬衫', 'WAF', '/upload/item/shop/1/2019122816192517757.jpg', 1, 2, NULL, '2019-12-28 16:19:26', 1, 1);
INSERT INTO `tb_award` VALUES (2, '元年黑魂卫衣', '我行我素', '/upload/item/shop/1/2019122816194989470.jpg', 2, 1, NULL, '2019-12-28 16:19:49', 1, 1);
INSERT INTO `tb_award` VALUES (3, 'LoveyBoy88', '鱼香肉丝炒面', '/upload/item/shop/1/2019122816184958776.jpg', 6, 12, '2019-12-24 16:35:06', '2019-12-28 16:18:49', 1, 1);
INSERT INTO `tb_award` VALUES (4, '棉花糖Z8跑跑', '贼快', '/upload/item/shop/1/2019122510561943646.jpg', 120, 126, '2019-12-24 16:35:06', '2019-12-25 10:56:19', 1, 1);
INSERT INTO `tb_award` VALUES (5, '英梨子', '可爱复古女孩', '/upload/item/shop/1/2019122510571659390.jpg', 66, 66, '2019-12-25 10:57:16', '2019-12-27 15:53:01', 1, 1);
INSERT INTO `tb_award` VALUES (6, '实在好美食城', '贼好吃', '/upload/item/shop/36/2019122611220743647.jpg', 2, 6, '2019-12-26 11:22:07', '2019-12-26 11:22:07', 1, 36);
INSERT INTO `tb_award` VALUES (7, '巧克力味奥利奥', '6元', '/upload/item/shop/39/2019122915481080530.jpg', 1, 3, '2019-12-29 15:48:10', '2019-12-29 15:48:10', 1, 39);
COMMIT;

-- ----------------------------
-- Table structure for tb_head_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line` (
  `line_id` int(100) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(1000) DEFAULT NULL,
  `line_link` varchar(2000) NOT NULL,
  `line_img` varchar(2000) NOT NULL,
  `priority` int(2) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_head_line
-- ----------------------------
BEGIN;
INSERT INTO `tb_head_line` VALUES (1, '1', '山驴B', '/upload/item/headtitle/2019122915074813980.jpg', 1, 1, NULL, '2019-12-29 15:07:49');
INSERT INTO `tb_head_line` VALUES (2, '2', '新隆嘉', '/upload/item/headtitle/2.jpg', 2, 1, NULL, NULL);
INSERT INTO `tb_head_line` VALUES (3, '3', '头条3', '/upload/item/headtitle/2019122915003270679.jpg', 3, 1, NULL, '2019-12-29 15:00:32');
INSERT INTO `tb_head_line` VALUES (4, '4', '头条4', '/upload/item/headtitle/4.jpg', 4, 1, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_local_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  UNIQUE KEY `uk_local_profile` (`username`),
  KEY `fk_localauth_profile` (`user_id`),
  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_local_auth
-- ----------------------------
BEGIN;
INSERT INTO `tb_local_auth` VALUES (1, 1, 'testbind', 's05bse6q2qlb9qblls96s592y55y556s', '2019-12-14 14:26:45', '2019-12-26 08:36:48');
COMMIT;

-- ----------------------------
-- Table structure for tb_person_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `profile_img` varchar(1024) DEFAULT NULL,
  `email` varchar(1024) DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:禁止使用本商城,1:允许使用本商城',
  `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:顾客,2:店家,3:超级管理员',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_person_info
-- ----------------------------
BEGIN;
INSERT INTO `tb_person_info` VALUES (1, '冲击之刃', '/upload/item/person/1.jpg', 'test', '1', 1, 2, NULL, NULL);
INSERT INTO `tb_person_info` VALUES (3, '小孩游神', '/upload/item/person/2.jpg', NULL, NULL, 1, 1, '2019-12-10 11:26:46', NULL);
INSERT INTO `tb_person_info` VALUES (4, '西卡李浩宇', '/upload/item/person/3.jpg', NULL, NULL, 1, 1, '2019-12-17 10:04:17', NULL);
INSERT INTO `tb_person_info` VALUES (5, '国服第一劫zed', '/upload/item/person/5.jpg', NULL, NULL, 1, 1, NULL, NULL);
INSERT INTO `tb_person_info` VALUES (6, '七耀山鳄鱼哥', '/upload/item/person/4.jpg', NULL, NULL, 1, 1, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) DEFAULT '',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  `point` int(10) DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_procate` (`product_category_id`),
  KEY `fk_product_shop` (`shop_id`),
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
BEGIN;
INSERT INTO `tb_product` VALUES (1, '正式的商品6', '正式的商品描述66', '/upload/item/shop/1/2019112814524716199.jpg', '26', '16', 2, '2019-11-25 16:49:10', '2019-11-28 14:52:47', 1, NULL, 1, 1);
INSERT INTO `tb_product` VALUES (2, '开发区扛把子', '奥妮克希亚', '/upload/item/shop/1/2019122915033220774.jpg', '31', '13', 100, '2019-11-25 16:49:10', '2019-12-29 15:03:33', 1, 1, 1, 8);
INSERT INTO `tb_product` VALUES (3, '巧克力奥利奥', '六元', '/upload/item/shop/1/2019123008105757494.jpg', '666', '268', 6, '2019-11-25 16:49:10', '2019-12-30 08:10:57', 1, 1, 1, 3);
INSERT INTO `tb_product` VALUES (4, '测试商品1', '描述测试商品1', '/upload/item/shop/1/2019112611202624671.jpg', '1678', '875', 20, '2019-11-26 11:20:27', '2019-11-26 11:20:27', 1, 1, 1, 0);
INSERT INTO `tb_product` VALUES (5, '海底捞火锅测试蘸料', '测试商品类别2', '/upload/item/shop/1/2019121615255559009.jpg', '200', '100', 100, '2019-11-26 16:52:44', '2019-12-16 15:25:55', 1, 6, 1, 16);
INSERT INTO `tb_product` VALUES (6, '三百万鲜族风味拌饭', '就是好吃', '/upload/item/shop/1/2019122315202631325.jpg', '12', '6', 10, '2019-11-29 11:50:03', '2019-12-23 15:20:27', 1, 2, 1, 1);
INSERT INTO `tb_product` VALUES (7, '包子6号', '好吃', '/upload/item/shop/1/2019120909544636105.jpeg', '5', '6', 62, '2019-12-09 09:54:46', '2019-12-09 09:54:46', 1, 8, 23, 2);
INSERT INTO `tb_product` VALUES (8, '包子26号', '好吃就完事了', '/upload/item/shop/23/2019121611301072823.jpeg', '33', '5', 26, '2019-12-09 10:02:15', '2019-12-16 11:30:11', 1, 7, 23, 6);
INSERT INTO `tb_product` VALUES (9, '撕裂的天堂New', '国服第一劫ZED', '/upload/item/shop/23/2019121717305231026.png', '5', '6', 16, '2019-12-17 17:30:52', '2019-12-17 17:30:52', 1, 8, 23, 12);
INSERT INTO `tb_product` VALUES (10, '猪肉包OR牛肉包', '贼好吃快来哟', '/upload/item/shop/22/2019121808453277904.jpg', '8', '9', 7, '2019-12-18 08:44:23', '2019-12-18 08:45:32', 1, 9, 22, 6);
INSERT INTO `tb_product` VALUES (11, '火腿大亨', '12元一个', '/upload/item/shop/1/2019122913002531040.jpg', '11', '12', 9, '2019-12-29 13:00:26', '2019-12-29 13:00:26', 1, 11, 1, 10);
INSERT INTO `tb_product` VALUES (12, '淋雨一直走', '有梦就别怕痛', '/upload/item/shop/39/2019122915463939673.jpg', '6', '66', 26, '2019-12-29 15:46:40', '2019-12-29 15:46:40', 1, 12, 39, 62);
COMMIT;

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(100) NOT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_category_id`),
  KEY `fk_procate_shop` (`shop_id`),
  CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_product_category` VALUES (1, '店铺商品类别2', 20, NULL, 1);
INSERT INTO `tb_product_category` VALUES (2, '店铺商品类别3', 2, NULL, 1);
INSERT INTO `tb_product_category` VALUES (6, '商品类别11', 200, NULL, 1);
INSERT INTO `tb_product_category` VALUES (7, '肉包', 26, NULL, 23);
INSERT INTO `tb_product_category` VALUES (8, ' 菜包', 16, NULL, 23);
INSERT INTO `tb_product_category` VALUES (9, '噶好吃', 3, NULL, 22);
INSERT INTO `tb_product_category` VALUES (10, '孤傲苍狼', 16, NULL, 1);
INSERT INTO `tb_product_category` VALUES (11, '力王', 26, NULL, 1);
INSERT INTO `tb_product_category` VALUES (12, '张韶涵', 26, NULL, 39);
COMMIT;

-- ----------------------------
-- Table structure for tb_product_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img` (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) NOT NULL,
  `img_desc` varchar(2000) DEFAULT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `product_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `fk_proming_product` (`product_id`),
  CONSTRAINT `fk_proming_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_img
-- ----------------------------
BEGIN;
INSERT INTO `tb_product_img` VALUES (5, 'upload/item/shop/1/2019112611202773378.jpg', NULL, NULL, '2019-11-26 11:20:27', 4);
INSERT INTO `tb_product_img` VALUES (6, 'upload/item/shop/1/2019112611202770876.jpg', NULL, NULL, '2019-11-26 11:20:27', 4);
INSERT INTO `tb_product_img` VALUES (21, 'upload/item/shop/1/2019112814524744954.jpg', NULL, NULL, '2019-11-28 14:52:47', 1);
INSERT INTO `tb_product_img` VALUES (22, 'upload/item/shop/1/2019112814524728171.jpg', NULL, NULL, '2019-11-28 14:52:47', 1);
INSERT INTO `tb_product_img` VALUES (25, '/upload/item/shop/1/2019120909544663941.jpeg', NULL, NULL, '2019-12-09 09:54:46', 7);
INSERT INTO `tb_product_img` VALUES (26, '/upload/item/shop/1/2019120909544682826.jpeg', NULL, NULL, '2019-12-09 09:54:46', 7);
INSERT INTO `tb_product_img` VALUES (29, '/upload/item/shop/23/2019121611301091167.jpeg', NULL, NULL, '2019-12-16 11:30:11', 8);
INSERT INTO `tb_product_img` VALUES (30, '/upload/item/shop/1/2019121615255586282.png', NULL, NULL, '2019-12-16 15:25:56', 5);
INSERT INTO `tb_product_img` VALUES (31, '/upload/item/shop/23/2019121717305285152.jpeg', NULL, NULL, '2019-12-17 17:30:52', 9);
INSERT INTO `tb_product_img` VALUES (32, '/upload/item/shop/23/2019121717305244517.png', NULL, NULL, '2019-12-17 17:30:52', 9);
INSERT INTO `tb_product_img` VALUES (35, '/upload/item/shop/22/2019121808453276915.jpg', NULL, NULL, '2019-12-18 08:45:33', 10);
INSERT INTO `tb_product_img` VALUES (36, '/upload/item/shop/22/2019121808453390747.jpg', NULL, NULL, '2019-12-18 08:45:33', 10);
INSERT INTO `tb_product_img` VALUES (37, '/upload/item/shop/1/2019122315202722909.jpg', NULL, NULL, '2019-12-23 15:20:27', 6);
INSERT INTO `tb_product_img` VALUES (38, '/upload/item/shop/1/2019122315202736505.jpg', NULL, NULL, '2019-12-23 15:20:27', 6);
INSERT INTO `tb_product_img` VALUES (40, '/upload/item/shop/1/2019122913002544065.jpg', NULL, NULL, '2019-12-29 13:00:26', 11);
INSERT INTO `tb_product_img` VALUES (41, '/upload/item/shop/1/2019122913002630097.jpg', NULL, NULL, '2019-12-29 13:00:26', 11);
INSERT INTO `tb_product_img` VALUES (42, '/upload/item/shop/1/2019122915033249268.jpg', NULL, NULL, '2019-12-29 15:03:33', 2);
INSERT INTO `tb_product_img` VALUES (43, '/upload/item/shop/39/2019122915463977911.jpg', NULL, NULL, '2019-12-29 15:46:40', 12);
INSERT INTO `tb_product_img` VALUES (44, '/upload/item/shop/39/2019122915463963186.jpg', NULL, NULL, '2019-12-29 15:46:40', 12);
INSERT INTO `tb_product_img` VALUES (45, '/upload/item/shop/1/2019123008105771392.jpg', NULL, NULL, '2019-12-30 08:10:58', 3);
INSERT INTO `tb_product_img` VALUES (46, '/upload/item/shop/1/2019123008105835985.jpg', NULL, NULL, '2019-12-30 08:10:58', 3);
COMMIT;

-- ----------------------------
-- Table structure for tb_product_sell_daily
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_sell_daily`;
CREATE TABLE `tb_product_sell_daily` (
  `product_sell_daily_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_id` int(100) DEFAULT NULL,
  `shop_id` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `total` int(10) DEFAULT '0',
  PRIMARY KEY (`product_sell_daily_id`),
  UNIQUE KEY `uq_product_sell` (`product_id`,`shop_id`,`create_time`) USING BTREE,
  KEY `fk_product_sell_shop` (`shop_id`),
  CONSTRAINT `fk_product_sell_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`),
  CONSTRAINT `fk_product_sell_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_sell_daily
-- ----------------------------
BEGIN;
INSERT INTO `tb_product_sell_daily` VALUES (10, 1, 1, '2019-12-22 00:00:00', 2);
INSERT INTO `tb_product_sell_daily` VALUES (11, 2, 1, '2019-12-22 00:00:00', 1);
INSERT INTO `tb_product_sell_daily` VALUES (12, 9, 23, '2019-12-22 00:00:00', 1);
INSERT INTO `tb_product_sell_daily` VALUES (13, 3, 1, '2019-12-22 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (14, 4, 1, '2019-12-22 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (15, 5, 1, '2019-12-22 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (16, 6, 1, '2019-12-22 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (17, 10, 22, '2019-12-22 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (18, 7, 23, '2019-12-22 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (19, 8, 23, '2019-12-22 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (20, 1, 1, '2019-12-21 00:00:00', 6);
INSERT INTO `tb_product_sell_daily` VALUES (21, 1, 1, '2019-12-20 00:00:00', 8);
INSERT INTO `tb_product_sell_daily` VALUES (22, 1, 1, '2019-12-19 00:00:00', 5);
INSERT INTO `tb_product_sell_daily` VALUES (23, 1, 1, '2019-12-18 00:00:00', 3);
INSERT INTO `tb_product_sell_daily` VALUES (24, 1, 1, '2019-12-17 00:00:00', 0);
INSERT INTO `tb_product_sell_daily` VALUES (25, 1, 1, '2019-12-16 00:00:00', 9);
INSERT INTO `tb_product_sell_daily` VALUES (26, 2, 1, '2019-12-21 00:00:00', 3);
INSERT INTO `tb_product_sell_daily` VALUES (27, 2, 1, '2019-12-20 00:00:00', 4);
INSERT INTO `tb_product_sell_daily` VALUES (28, 2, 1, '2019-12-19 00:00:00', 6);
INSERT INTO `tb_product_sell_daily` VALUES (29, 2, 1, '2019-12-18 00:00:00', 1);
INSERT INTO `tb_product_sell_daily` VALUES (30, 2, 1, '2019-12-17 00:00:00', 9);
INSERT INTO `tb_product_sell_daily` VALUES (31, 2, 1, '2019-12-16 00:00:00', 9);
COMMIT;

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `area_id` int(5) DEFAULT NULL,
  `shop_category_id` int(11) DEFAULT NULL,
  `shop_name` varchar(256) NOT NULL,
  `shop_desc` varchar(1024) DEFAULT NULL,
  `shop_addr` varchar(200) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `shop_img` varchar(1024) DEFAULT NULL,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `advice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_shopcate` (`shop_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
BEGIN;
INSERT INTO `tb_shop` VALUES (1, 1, 3, 14, '鬼王达', '懦夫救星', '无敌风火轮66号', '666683792103', '/upload/item/shop/1/2019122915020724519.jpg', 110, '2019-11-13 20:05:00', '2019-12-29 15:02:08', 1, '审核中');
INSERT INTO `tb_shop` VALUES (10, 1, 3, 22, '深岩之洲', '巨石之核', '格瑞姆巴托', '13503609930', '/upload/item/shop/10/2019112017181140711.jpg', 50, '2019-11-20 17:18:11', '2019-11-20 17:18:11', 1, NULL);
INSERT INTO `tb_shop` VALUES (18, 1, 2, 22, '北京祝福你', '武动乾坤', '三兄弟终聚首', '999999', '/upload/item/shop/18/2019112520203720046.jpg', 40, '2019-11-23 10:43:23', '2019-11-25 20:20:38', 1, NULL);
INSERT INTO `tb_shop` VALUES (19, 1, 2, 20, '火焰之地', '拉格纳罗斯', '萨弗拉斯燃灭之手', '999999666', '/upload/item/shop/19/2019112515244481298.jpg', 30, '2019-11-25 15:24:45', '2019-11-25 15:24:45', 1, NULL);
INSERT INTO `tb_shop` VALUES (20, 1, 2, 20, '巨龙之魂惩戒骑', '不眠的约萨希 ', '实验标本切片斧410等级', '66669999666', '/upload/item/shop/20/2019112515330168265.jpg', 20, '2019-11-25 15:33:01', '2019-11-25 15:33:01', 1, NULL);
INSERT INTO `tb_shop` VALUES (21, 1, 2, 22, '雷电王座', '雷神雷电之王', '金陵雷神范儿', '66666666', '/upload/item/shop/21/2019112519585593555.jpg', 16, '2019-11-25 19:58:56', '2019-11-25 19:58:56', 1, NULL);
INSERT INTO `tb_shop` VALUES (22, 1, 2, 20, '忆往昔梦泉包子铺', '梦幻般的味道', '远望', '333333', '/upload/item/shop/22/2019121808094958315.png', 66, '2019-12-09 09:33:44', '2019-12-18 08:09:49', 1, NULL);
INSERT INTO `tb_shop` VALUES (23, 1, 2, 24, '不二心肮脏包子铺', '50元一个肉包', '华南安盛身后', '999876', '/upload/item/shop/23/2019121213182531966.jpeg', 88, '2019-12-09 09:39:24', '2019-12-12 13:18:26', 1, NULL);
INSERT INTO `tb_shop` VALUES (35, 3, 2, 22, '奶茶来了', '奶茶来了', '北苑6栋', NULL, NULL, 0, NULL, NULL, 1, NULL);
INSERT INTO `tb_shop` VALUES (36, 1, 2, 30, '叫我金陵雷神', '飞向未来的城堡', '永兴远洋', '999999', '/upload/item/shop/36/2019121808083995961.jpg', NULL, '2019-12-18 08:08:40', '2019-12-18 08:08:40', 1, NULL);
INSERT INTO `tb_shop` VALUES (37, 1, 2, 29, '远洋荣域YTY', '贼狠', '6就完事儿了', '666666', '/upload/item/shop/37/2019122115350891050.jpg', NULL, '2019-12-21 15:35:09', '2019-12-21 15:35:09', 1, NULL);
INSERT INTO `tb_shop` VALUES (38, 1, 2, 3, '快乐阿拉蕾', 'mayiya一杯咖啡', '大庆市大庆中学', '123456', '/upload/item/shop/38/2019122913022612592.jpg', NULL, '2019-12-29 13:02:26', '2019-12-29 13:02:26', 1, NULL);
INSERT INTO `tb_shop` VALUES (39, 1, 2, 25, '男人海洋', '我的爱是折下自己的翅膀送给你飞翔', '周传雄', '小刚', '/upload/item/shop/39/2019122915441721743.jpg', NULL, '2019-12-29 15:44:17', '2019-12-29 15:44:17', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_shop_auth_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_auth_map`;
CREATE TABLE `tb_shop_auth_map` (
  `shop_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `employee_id` int(10) NOT NULL,
  `shop_id` int(10) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `title_flag` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`shop_auth_id`),
  UNIQUE KEY `uq_shop_auth_map` (`employee_id`,`shop_id`) USING BTREE,
  KEY `fk_shop_auth_map_shop` (`shop_id`),
  CONSTRAINT `fk_shop_auth_map_employee` FOREIGN KEY (`employee_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_auth_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop_auth_map
-- ----------------------------
BEGIN;
INSERT INTO `tb_shop_auth_map` VALUES (1, 1, 1, '店家', 0, '2019-12-20 07:40:20', '2019-12-20 07:40:20', 1);
INSERT INTO `tb_shop_auth_map` VALUES (3, 1, 37, '店家', 0, '2019-12-21 15:35:09', '2019-12-21 15:35:09', 1);
INSERT INTO `tb_shop_auth_map` VALUES (4, 1, 38, '店家', 0, '2019-12-29 13:02:26', '2019-12-29 13:02:26', 1);
INSERT INTO `tb_shop_auth_map` VALUES (5, 1, 39, '店家', 0, '2019-12-29 15:44:18', '2019-12-29 15:44:18', 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(100) NOT NULL DEFAULT '',
  `shop_category_desc` varchar(1000) DEFAULT '',
  `shop_category_img` varchar(2000) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_shop_category_self` (`parent_id`),
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_shop_category` VALUES (1, '咖啡奶茶', '咖啡奶茶', '/upload/item/shopcategory/1.jpg', 1, NULL, NULL, NULL);
INSERT INTO `tb_shop_category` VALUES (3, '咖啡', '测试类别', 'test3', 0, NULL, NULL, 1);
INSERT INTO `tb_shop_category` VALUES (10, '二手市场', '二手商品交易', '/upload/item/shopcategory/2.jpg', 100, '2019-11-30 09:19:43', '2019-11-30 09:19:43', NULL);
INSERT INTO `tb_shop_category` VALUES (11, '美容美发', '美容美发', '/upload/item/shopcategory/3.jpg', 99, '2019-11-30 09:22:32', '2019-11-30 09:22:32', NULL);
INSERT INTO `tb_shop_category` VALUES (12, '美食饮品', '美食饮品', '/upload/item/shopcategory/4.jpg', 98, '2019-11-30 09:28:12', '2019-11-30 09:28:12', NULL);
INSERT INTO `tb_shop_category` VALUES (13, '休闲娱乐', '休闲娱乐', '/upload/item/shopcategory/5.jpg', 97, '2019-11-30 09:28:12', '2019-11-30 09:28:12', NULL);
INSERT INTO `tb_shop_category` VALUES (14, '二手车', '二手车', '店铺种类14', 80, '2019-11-30 09:32:13', '2019-11-30 09:32:13', 10);
INSERT INTO `tb_shop_category` VALUES (15, '二手书籍', '二手书籍', '店铺种类15', 79, '2019-11-30 09:32:13', '2019-11-30 09:32:13', 10);
INSERT INTO `tb_shop_category` VALUES (17, '美容', '美容', '店铺种类17', 76, '2019-11-30 09:34:43', '2019-11-30 09:34:43', 11);
INSERT INTO `tb_shop_category` VALUES (18, '美发', '美发', '店铺种类18', 74, '2019-11-30 09:34:43', '2019-11-30 09:34:43', 11);
INSERT INTO `tb_shop_category` VALUES (20, '大排档', '大排档', '店铺种类20', 59, '2019-11-30 09:36:46', '2019-11-30 09:36:46', 12);
INSERT INTO `tb_shop_category` VALUES (22, '奶茶店', '奶茶店', '店铺种类22', 58, '2019-11-30 09:36:46', '2019-11-30 09:36:46', 12);
INSERT INTO `tb_shop_category` VALUES (24, '密室逃生', '密室逃生', '店铺种类24', 56, '2019-11-30 09:38:19', '2019-11-30 09:38:19', 13);
INSERT INTO `tb_shop_category` VALUES (25, 'KTV', 'KTV', '店铺种类25', 57, '2019-11-30 09:38:19', '2019-11-30 09:38:19', 13);
INSERT INTO `tb_shop_category` VALUES (27, '培训教育', '培训教育', '/upload/item/shopcategory/6.jpg', 96, '2019-11-30 09:39:57', '2019-11-30 09:39:57', NULL);
INSERT INTO `tb_shop_category` VALUES (28, '租赁市场', '租赁市场', '/upload/item/shopcategory/7.jpg', 95, '2019-11-30 09:39:57', '2019-11-30 09:39:57', NULL);
INSERT INTO `tb_shop_category` VALUES (29, '程序设计', '程序设计', '店铺种类29', 50, '2019-11-30 09:41:32', '2019-11-30 09:41:32', 27);
INSERT INTO `tb_shop_category` VALUES (30, '声乐舞蹈', '声乐舞蹈', '店铺种类30', 49, '2019-11-30 09:41:32', '2019-11-30 09:41:32', 27);
INSERT INTO `tb_shop_category` VALUES (31, '演出道具', '演出道具', '店铺种类31', 45, '2019-11-30 09:42:26', '2019-11-30 09:42:26', 28);
INSERT INTO `tb_shop_category` VALUES (32, '交通工具', '交通工具', '店铺种类32', 44, '2019-11-30 09:42:26', '2019-11-30 09:42:26', 28);
INSERT INTO `tb_shop_category` VALUES (33, '乐哈哈笑嘻嘻', '经济实惠乐观', '/upload/item/shopcategory/2019122915171020509.jpeg', 123, '2019-12-29 15:17:10', '2019-12-29 15:17:10', 13);
COMMIT;

-- ----------------------------
-- Table structure for tb_user_award_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_award_map`;
CREATE TABLE `tb_user_award_map` (
  `user_award_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `award_id` int(10) NOT NULL,
  `shop_id` int(10) NOT NULL,
  `operator_id` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `used_status` int(2) NOT NULL DEFAULT '0',
  `point` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_award_id`),
  KEY `fk_user_award_map_profile` (`user_id`),
  KEY `fk_user_award_map_award` (`award_id`),
  KEY `fk_user_award_map_shop` (`shop_id`),
  KEY `fk_user_award_map_operator` (`operator_id`),
  CONSTRAINT `fk_user_award_map_award` FOREIGN KEY (`award_id`) REFERENCES `tb_award` (`award_id`),
  CONSTRAINT `fk_user_award_map_operator` FOREIGN KEY (`operator_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_award_map_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_award_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_award_map
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_award_map` VALUES (1, 1, 1, 1, 3, '2019-12-18 16:51:01', 1, 1);
INSERT INTO `tb_user_award_map` VALUES (2, 4, 2, 1, 5, '2019-12-18 16:51:02', 1, 2);
INSERT INTO `tb_user_award_map` VALUES (3, 3, 3, 1, 6, '2019-12-24 16:37:33', 1, 3);
INSERT INTO `tb_user_award_map` VALUES (4, 1, 4, 1, 4, '2019-12-24 16:37:34', 0, 6);
INSERT INTO `tb_user_award_map` VALUES (5, 1, 1, 1, 1, '2019-12-25 16:35:42', 0, 1);
INSERT INTO `tb_user_award_map` VALUES (6, 1, 1, 1, 4, '2019-12-25 16:40:54', 0, 1);
INSERT INTO `tb_user_award_map` VALUES (7, 1, 2, 1, 3, '2019-12-25 16:40:57', 0, 2);
INSERT INTO `tb_user_award_map` VALUES (8, 1, 3, 1, 5, '2019-12-25 19:31:31', 0, 6);
INSERT INTO `tb_user_award_map` VALUES (9, 1, 6, 36, 6, '2019-12-26 11:23:05', 0, 2);
INSERT INTO `tb_user_award_map` VALUES (10, 1, 2, 1, NULL, '2019-12-28 16:16:07', 0, 2);
INSERT INTO `tb_user_award_map` VALUES (11, 1, 2, 1, NULL, '2019-12-29 12:57:36', 0, 2);
INSERT INTO `tb_user_award_map` VALUES (12, 1, 1, 1, NULL, '2019-12-29 12:57:41', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_user_product_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_product_map`;
CREATE TABLE `tb_user_product_map` (
  `user_product_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `product_id` int(100) DEFAULT NULL,
  `shop_id` int(10) DEFAULT NULL,
  `operator_id` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `point` int(10) DEFAULT '0',
  PRIMARY KEY (`user_product_id`),
  KEY `fk_user_product_map_profile` (`user_id`),
  KEY `fk_user_product_map_product` (`product_id`),
  KEY `fk_user_product_map_shop` (`shop_id`),
  KEY `fk_user_product_map_operator` (`operator_id`),
  CONSTRAINT `fk_user_product_map_operator` FOREIGN KEY (`operator_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_product_map_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`),
  CONSTRAINT `fk_user_product_map_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_user_product_map_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_product_map
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_product_map` VALUES (1, 1, 1, 1, 1, '2019-12-22 21:02:08', 1);
INSERT INTO `tb_user_product_map` VALUES (2, 1, 1, 1, 3, '2019-12-22 21:02:08', 2);
INSERT INTO `tb_user_product_map` VALUES (3, 1, 2, 1, 4, '2019-12-22 21:02:08', 3);
INSERT INTO `tb_user_product_map` VALUES (4, 1, 9, 23, 1, '2019-12-22 21:02:08', 4);
COMMIT;

-- ----------------------------
-- Table structure for tb_user_shop_map
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_shop_map`;
CREATE TABLE `tb_user_shop_map` (
  `user_shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `shop_id` int(10) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `point` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_shop_id`),
  UNIQUE KEY `uq_user_shop` (`user_id`,`shop_id`),
  KEY `fk_user_shop_shop` (`shop_id`),
  KEY `fk_user_shop_user` (`user_id`) USING BTREE,
  CONSTRAINT `fk_user_shop_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`),
  CONSTRAINT `fk_user_shop_user` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_shop_map
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_shop_map` VALUES (3, 1, 19, '2019-12-19 11:44:22', 3);
INSERT INTO `tb_user_shop_map` VALUES (4, 1, 36, '2019-12-19 11:44:22', 3);
INSERT INTO `tb_user_shop_map` VALUES (5, 1, 1, '2019-12-24 11:35:04', 0);
INSERT INTO `tb_user_shop_map` VALUES (6, 4, 1, '2019-12-24 11:35:04', 6);
COMMIT;

-- ----------------------------
-- Table structure for tb_wechat_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth` (
  `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `open_id` varchar(1024) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `open_id` (`open_id`),
  KEY `fk_wechatauth_profile` (`user_id`),
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wechat_auth
-- ----------------------------
BEGIN;
INSERT INTO `tb_wechat_auth` VALUES (2, 3, 'darunfawoerma', '2019-12-10 11:26:46');
INSERT INTO `tb_wechat_auth` VALUES (3, 4, 'yyryYTY', '2019-12-17 10:04:17');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
