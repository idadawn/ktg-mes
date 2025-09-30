-- 修复 md_workshop 表缺少 charge_id 列的问题
-- 在 charge 列后面添加 charge_id 列

ALTER TABLE md_workshop 
ADD COLUMN charge_id BIGINT(20) NULL COMMENT '负责人ID' 
AFTER charge;

-- 验证列是否添加成功
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT, COLUMN_COMMENT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'md_workshop' 
AND COLUMN_NAME = 'charge_id';

