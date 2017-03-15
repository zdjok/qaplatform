DROP TABLE IF EXISTS `test_task_log`;
CREATE TABLE `test_task_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `userName` varchar(20) DEFAULT NULL COMMENT '用户名',
  `excelFile` varchar(256) DEFAULT NULL COMMENT '用例excel文件路径名',
  `sheetName` varchar(50) DEFAULT NULL COMMENT '用例sheet名称',
  `caseName` varchar(50) DEFAULT NULL COMMENT '用例名称',
  `method` varchar(10) DEFAULT NULL COMMENT 'Post/Get方法',
  `path` varchar(128) DEFAULT NULL COMMENT '路径',
  `port` varchar(4) DEFAULT NULL COMMENT '端口号',
  `inputNum` int(3) DEFAULT 0 COMMENT '入参个数',
  `expectResult` VARCHAR(5) DEFAULT NULL COMMENT '预期结果',
	`testResult` VARCHAR(5) DEFAULT NULL COMMENT '测试结果',
	`response` varchar(256) DEFAULT NULL COMMENT '接口响应结果',
  `deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志，默认0不删除，1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试条目记录表';
