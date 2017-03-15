CREATE DATABASE `plt_autoteset`;
USE `plt_autoteset`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(50) NOT NULL COMMENT 'email',
  `deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志，默认0不删除，1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY(`id`)
) ENGINE=InnoDB CHARSET=utf8 COMMENT='用户表';