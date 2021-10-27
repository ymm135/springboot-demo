

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(64) NOT NULL COMMENT '主键id 用户id',
  `username` varchar(32) NOT NULL COMMENT '用户名 用户名',
  `password` varchar(64) NOT NULL COMMENT '密码 密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称 昵称',
  `realname` varchar(128) DEFAULT NULL COMMENT '真实姓名',
  `face` varchar(1024) NOT NULL COMMENT '头像',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号 手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱地址 邮箱地址',
  `sex` int(11) DEFAULT NULL COMMENT '性别 性别 1:男  0:女  2:保密',
  `birthday` date DEFAULT NULL COMMENT '生日 生日',
  `created_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间 更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表 ';

-- ----------------------------
-- Records of users  用户名及密码: test test
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('190815GTKCBSS7MW', 'test', 'CY9rzUYh03PK3k6DJie09g==', 'test', NULL, 'http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png', NULL, NULL, 2, '1900-01-01', '2019-08-15 22:11:58', '2019-08-15 22:11:58');
COMMIT;

