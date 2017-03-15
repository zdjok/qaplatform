DROP TABLE IF EXISTS `report_log`;
CREATE TABLE `report_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `currentReportPath` varchar(256) DEFAULT NULL COMMENT '报告文件路径名',
  `moduleName` varchar(50) DEFAULT NULL COMMENT '接口模块名称',
  `apiName` varchar(50) DEFAULT NULL COMMENT '接口名称',
  `caseName` varchar(50) DEFAULT NULL COMMENT '用例名称',
  `expectedResult` varchar(5) DEFAULT NULL COMMENT '预期结果',
  `actualResult` varchar(50) DEFAULT NULL COMMENT '实际结果',
  `testResult` varchar(50) DEFAULT NULL COMMENT '测试结果',
  `response` blob COMMENT '接口响应结果',
  `deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志，默认0不删除，1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试报告日志';
