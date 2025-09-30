SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 1、图形报表组件表
-- ----------------------------
DROP TABLE IF EXISTS `report_chart`;
CREATE TABLE `report_chart` (
  `chart_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图表ID',
  `chart_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图表编号',
  `chart_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图表名称',
  `chart_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图表类型',
  `business_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务类型',
  `api` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口地址',
  `options` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '图表options参数',
  `chart_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缩略图地址',
  `enable_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '是否启用',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  `attr1` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '预留字段1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '预留字段2',
  `attr3` bigint(20) DEFAULT NULL COMMENT '预留字段3',
  `attr4` bigint(20) DEFAULT NULL COMMENT '预留字段4',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`chart_id`),
  UNIQUE KEY `idx_chart_code` (`chart_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='图形报表组件表';

-- ----------------------------
-- 2、图形报表角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `report_chart_role`;
CREATE TABLE `report_chart_role` (
  `chart_id` bigint(20) NOT NULL COMMENT '图表ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`chart_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='图形报表角色关联表';

-- ----------------------------
-- 初始化数据
-- ----------------------------
-- 插入示例图表数据（可选）
INSERT INTO `report_chart` VALUES (1, 'CHART001', '生产工单统计', 'bar', 'WORKORDER', '/mes/pro/workorder/stats', NULL, NULL, 'Y', '生产工单统计图表', NULL, NULL, NULL, NULL, 'admin', NOW(), NULL, NULL);
INSERT INTO `report_chart` VALUES (2, 'CHART002', '产品质量分析', 'pie', 'QUALITY', '/mes/qc/stats', NULL, NULL, 'Y', '产品质量分析图表', NULL, NULL, NULL, NULL, 'admin', NOW(), NULL, NULL);

-- 为管理员角色分配所有图表权限
INSERT INTO `report_chart_role` VALUES (1, 1);
INSERT INTO `report_chart_role` VALUES (2, 1);

SET FOREIGN_KEY_CHECKS = 1;

