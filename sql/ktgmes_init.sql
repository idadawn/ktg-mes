-- ========================================
-- KTG-MES 完整初始化脚本
-- ========================================
-- 说明: 此脚本包含所有MES系统所需的表结构和初始数据
-- 字符集: UTF-8 (utf8mb4)
-- 执行方式: mysql -u用户名 -p密码 数据库名 < ktgmes_init.sql
-- ========================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 注意：本脚本将按以下顺序导入：
-- 1. ry_20210908.sql - 核心系统表（sys_*）
-- 2. quartz.sql - 定时任务调度表
-- 3. sys_menu.sql - 菜单权限数据
-- 4. sys_dict_type.sql + sys_dict_data.sql - 字典数据
-- 5. sys_auto_code*.sql - 自动编码规则
-- 6. mes-*.sql - MES业务表
-- 7. 其他辅助表

-- ========================================
-- 请按以下顺序执行以下文件：
-- ========================================

-- 第1步：导入核心系统表
SOURCE /home/dawn/project/mes/ktg-mes/sql/ry_20210908.sql;

-- 第2步：导入Quartz调度表
SOURCE /home/dawn/project/mes/ktg-mes/sql/quartz.sql;

-- 第3步：导入菜单权限数据
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/sys_menu.sql;

-- 第4步：导入字典数据
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/sys_dict_type.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/sys_dict_data.sql;

-- 第5步：导入自动编码规则
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/sys_auto_code.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/sys_auto_code_part.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/sys_auto_code_rule.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/sys_auto_code_result.sql;

-- 第6步：导入MES业务模块表
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes_sys.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes-md.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes-pro.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes-wm.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes-qc.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes-dv.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes-cal.sql;
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/mes-tm.sql;

-- 第7步：导入报表文件表
SOURCE /home/dawn/project/mes/ktg-mes/doc/设计文档/数据库设计/ureport_file_tbl.sql;

-- 第8步：导入初始化配置
SOURCE /home/dawn/project/mes/ktg-mes/doc/实施文档/inital.sql;

SET FOREIGN_KEY_CHECKS = 1;

