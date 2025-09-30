#!/bin/bash
# ========================================
# KTG-MES 数据库完整重新初始化脚本
# ========================================
# 说明: 此脚本将备份并重新初始化MES系统数据库
# 警告: 此操作将删除所有现有数据！
# 使用方法: ./reinit_database.sh
# ========================================

# set -e  # 注释掉，允许脚本继续执行即使有错误

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 数据库连接信息
DB_HOST="mysql"
DB_USER="ktgmes"
DB_PASSWORD="ktgmes123"
DB_NAME="ktgmes"
CHARSET="utf8mb4"

# 脚本所在目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# 备份目录
BACKUP_DIR="$SCRIPT_DIR/backups"
BACKUP_FILE="$BACKUP_DIR/ktgmes_backup_$(date +%Y%m%d_%H%M%S).sql"

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}KTG-MES 数据库完整重新初始化${NC}"
echo -e "${GREEN}========================================${NC}"

# 创建备份目录
mkdir -p "$BACKUP_DIR"

# 备份当前数据库
echo -e "\n${YELLOW}正在备份当前数据库...${NC}"
docker exec ktg-mes-mysql mysqldump -h"$DB_HOST" -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" --default-character-set="$CHARSET" --single-transaction > "$BACKUP_FILE" 2>/dev/null
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ 备份完成: $BACKUP_FILE${NC}"
else
    echo -e "${RED}✗ 备份失败${NC}"
    exit 1
fi

# 删除所有表
echo -e "\n${YELLOW}正在清空数据库...${NC}"
docker exec -i ktg-mes-mysql mysql -h"$DB_HOST" -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" --default-character-set="$CHARSET" -e "
SET FOREIGN_KEY_CHECKS = 0;
SET GROUP_CONCAT_MAX_LEN=32768;
SET @tables = NULL;
SELECT GROUP_CONCAT('\`', table_name, '\`') INTO @tables
  FROM information_schema.tables
  WHERE table_schema = '$DB_NAME';

SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;
" 2>/dev/null

echo -e "${GREEN}✓ 数据库已清空${NC}"

# 函数：执行SQL文件
execute_sql() {
    local file=$1
    local description=$2
    
    if [ ! -f "$file" ]; then
        echo -e "${RED}错误: 文件不存在 - $file${NC}"
        return 1
    fi
    
    echo -e "${BLUE}  → $description${NC}"
    
    docker exec -i ktg-mes-mysql mysql -h"$DB_HOST" -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" --default-character-set="$CHARSET" < "$file" 2>&1 | grep -i "error" | head -5 || true
    
    local exit_code=${PIPESTATUS[0]}
    if [ $exit_code -eq 0 ]; then
        echo -e "${GREEN}    ✓ 完成${NC}"
        return 0
    else
        echo -e "${RED}    ✗ 失败 (退出码: $exit_code)${NC}"
        echo -e "${YELLOW}    继续执行...${NC}"
        return 0
    fi
}

# 开始执行
echo -e "\n${GREEN}开始初始化数据库...${NC}\n"

echo -e "${YELLOW}[1/19] 核心系统表${NC}"
execute_sql "$SCRIPT_DIR/ry_20210908.sql" "若依核心系统表 (sys_user, sys_role, sys_menu等)"

echo -e "\n${YELLOW}[2/19] Quartz调度表${NC}"
execute_sql "$SCRIPT_DIR/quartz.sql" "Quartz定时任务表 (QRTZ_*)"

echo -e "\n${YELLOW}[3/19] 菜单权限数据${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_menu.sql" "系统菜单权限完整数据"

echo -e "\n${YELLOW}[4/19] 字典类型数据${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_dict_type.sql" "字典类型定义"

echo -e "\n${YELLOW}[5/19] 字典数据${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_dict_data.sql" "字典数据明细"

echo -e "\n${YELLOW}[6/19] 自动编码基础${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code.sql" "自动编码基础表"

echo -e "\n${YELLOW}[7/19] 自动编码组成${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code_part.sql" "自动编码组成规则"

echo -e "\n${YELLOW}[8/19] 自动编码规则${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code_rule.sql" "自动编码规则详情"

echo -e "\n${YELLOW}[9/19] 自动编码结果${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code_result.sql" "自动编码结果记录"

echo -e "\n${YELLOW}[10/19] MES系统基础${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes_sys.sql" "MES系统基础表"

echo -e "\n${YELLOW}[11/19] MES主数据模块${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-md.sql" "物料、客户、供应商、仓库等主数据"

echo -e "\n${YELLOW}[12/19] MES生产模块${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-pro.sql" "生产工单、工艺路线、报工等"

echo -e "\n${YELLOW}[13/19] MES仓储模块${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-wm.sql" "库存、出入库、盘点等"

echo -e "\n${YELLOW}[14/19] MES质量模块${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-qc.sql" "质检、检验单、缺陷等"

echo -e "\n${YELLOW}[15/19] MES设备模块${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-dv.sql" "设备管理、维护保养等"

echo -e "\n${YELLOW}[16/19] MES日历模块${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-cal.sql" "工作日历、班次等"

echo -e "\n${YELLOW}[17/19] MES工装夹具模块${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-tm.sql" "工装夹具管理"

echo -e "\n${YELLOW}[18/20] 报表文件表${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/ureport_file_tbl.sql" "报表文件存储"

echo -e "\n${YELLOW}[19/20] 打印客户端表${NC}"
execute_sql "$SCRIPT_DIR/print_client.sql" "打印客户端管理"

echo -e "\n${YELLOW}[20/20] 初始化配置${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/inital.sql" "初始化配置数据"

echo -e "\n${GREEN}========================================${NC}"
echo -e "${GREEN}✓ 数据库初始化完成！${NC}"
echo -e "${GREEN}========================================${NC}"

# 显示统计信息
echo -e "\n${BLUE}统计信息:${NC}"
docker exec -i ktg-mes-mysql mysql -h"$DB_HOST" -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" --default-character-set="$CHARSET" -e "
SELECT 
    COUNT(*) as '总表数' 
FROM 
    information_schema.tables 
WHERE 
    table_schema = '$DB_NAME';" 2>/dev/null | tail -n +2

echo -e "\n${BLUE}主要模块表数量:${NC}"
docker exec -i ktg-mes-mysql mysql -h"$DB_HOST" -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" --default-character-set="$CHARSET" -e "
SELECT 
    CASE 
        WHEN table_name LIKE 'sys_%' THEN '系统表(sys_*)'
        WHEN table_name LIKE 'gen_%' THEN '代码生成(gen_*)'
        WHEN table_name LIKE 'QRTZ_%' THEN 'Quartz(QRTZ_*)'
        WHEN table_name LIKE 'md_%' THEN '主数据(md_*)'
        WHEN table_name LIKE 'pro_%' THEN '生产模块(pro_*)'
        WHEN table_name LIKE 'wm_%' THEN '仓储模块(wm_*)'
        WHEN table_name LIKE 'qc_%' THEN '质量模块(qc_*)'
        WHEN table_name LIKE 'dv_%' THEN '设备模块(dv_*)'
        WHEN table_name LIKE 'cal_%' THEN '日历模块(cal_*)'
        WHEN table_name LIKE 'tm_%' THEN '工装模块(tm_*)'
        WHEN table_name LIKE 'report_%' THEN '报表模块(report_*)'
        ELSE '其他表'
    END as '模块',
    COUNT(*) as '表数量'
FROM 
    information_schema.tables 
WHERE 
    table_schema = '$DB_NAME'
GROUP BY 
    CASE 
        WHEN table_name LIKE 'sys_%' THEN '系统表(sys_*)'
        WHEN table_name LIKE 'gen_%' THEN '代码生成(gen_*)'
        WHEN table_name LIKE 'QRTZ_%' THEN 'Quartz(QRTZ_*)'
        WHEN table_name LIKE 'md_%' THEN '主数据(md_*)'
        WHEN table_name LIKE 'pro_%' THEN '生产模块(pro_*)'
        WHEN table_name LIKE 'wm_%' THEN '仓储模块(wm_*)'
        WHEN table_name LIKE 'qc_%' THEN '质量模块(qc_*)'
        WHEN table_name LIKE 'dv_%' THEN '设备模块(dv_*)'
        WHEN table_name LIKE 'cal_%' THEN '日历模块(cal_*)'
        WHEN table_name LIKE 'tm_%' THEN '工装模块(tm_*)'
        WHEN table_name LIKE 'report_%' THEN '报表模块(report_*)'
        ELSE '其他表'
    END
ORDER BY '表数量' DESC;" 2>/dev/null | tail -n +2

echo -e "\n${GREEN}✓ 初始化完成！请重启后端服务以使更改生效。${NC}"
echo -e "${YELLOW}备份文件: $BACKUP_FILE${NC}"
echo -e "\n${BLUE}重启后端服务命令:${NC}"
echo -e "  ${YELLOW}docker restart ktg-mes-app${NC}\n"

