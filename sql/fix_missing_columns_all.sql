-- =========================================
-- 修复数据库表缺失列的问题（汇总）
-- 日期: 2025-09-30
-- 说明: 修复代码中引用但数据库表中缺失的列
-- =========================================

-- 1. 修复 pro_workorder 表缺少 unit_name 列
ALTER TABLE pro_workorder 
ADD COLUMN IF NOT EXISTS unit_name VARCHAR(64) NULL COMMENT '单位名称' 
AFTER unit_of_measure;

-- 2. 修复 wm_warehouse 表缺少 frozen_flag 列
ALTER TABLE wm_warehouse 
ADD COLUMN IF NOT EXISTS frozen_flag CHAR(1) DEFAULT 'N' COMMENT '冻结标识' 
AFTER charge;

-- 3. 修复 md_workshop 表缺少 charge_id 列
ALTER TABLE md_workshop 
ADD COLUMN IF NOT EXISTS charge_id BIGINT(20) NULL COMMENT '负责人ID' 
AFTER charge;

-- 验证修复结果
SELECT 'pro_workorder.unit_name' AS table_column, 
       COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_COMMENT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'pro_workorder' AND COLUMN_NAME = 'unit_name'

UNION ALL

SELECT 'wm_warehouse.frozen_flag' AS table_column,
       COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_COMMENT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'wm_warehouse' AND COLUMN_NAME = 'frozen_flag'

UNION ALL

SELECT 'md_workshop.charge_id' AS table_column,
       COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_COMMENT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'md_workshop' AND COLUMN_NAME = 'charge_id';

