-- 修复 pro_workorder 表缺少 unit_name 列的问题
-- 在 unit_of_measure 列后面添加 unit_name 列

ALTER TABLE pro_workorder 
ADD COLUMN unit_name VARCHAR(64) NULL COMMENT '单位名称' 
AFTER unit_of_measure;

-- 验证列是否添加成功
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_COMMENT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'pro_workorder' 
AND COLUMN_NAME = 'unit_name';

