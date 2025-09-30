-- 修复 wm_warehouse 表缺少 frozen_flag 列的问题
-- 在 charge 列后面添加 frozen_flag 列

ALTER TABLE wm_warehouse 
ADD COLUMN frozen_flag CHAR(1) DEFAULT 'N' COMMENT '冻结标识' 
AFTER charge;

-- 验证列是否添加成功
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT, COLUMN_COMMENT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'wm_warehouse' 
AND COLUMN_NAME = 'frozen_flag';

