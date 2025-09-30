#!/bin/bash
# ========================================
# KTG-MES 数据库完整初始化脚本
# ========================================
# 说明: 此脚本将完整初始化MES系统数据库
# 使用方法: ./init_database.sh
# ========================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
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

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}KTG-MES 数据库初始化${NC}"
echo -e "${GREEN}========================================${NC}"

# 函数：执行SQL文件
execute_sql() {
    local file=$1
    local description=$2
    
    if [ ! -f "$file" ]; then
        echo -e "${RED}错误: 文件不存在 - $file${NC}"
        return 1
    fi
    
    echo -e "${YELLOW}正在执行: $description${NC}"
    echo -e "  文件: $file"
    
    docker exec -i ktg-mes-mysql mysql -h"$DB_HOST" -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" --default-character-set="$CHARSET" < "$file"
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ 完成${NC}\n"
    else
        echo -e "${RED}✗ 失败${NC}\n"
        return 1
    fi
}

# 开始执行
echo -e "${YELLOW}步骤 1/15: 核心系统表 (ry_20210908.sql)${NC}"
execute_sql "$SCRIPT_DIR/ry_20210908.sql" "若依核心系统表"

echo -e "${YELLOW}步骤 2/15: Quartz调度表 (quartz.sql)${NC}"
execute_sql "$SCRIPT_DIR/quartz.sql" "Quartz定时任务表"

echo -e "${YELLOW}步骤 3/15: 菜单权限数据 (sys_menu.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_menu.sql" "系统菜单权限"

echo -e "${YELLOW}步骤 4/15: 字典类型数据 (sys_dict_type.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_dict_type.sql" "字典类型"

echo -e "${YELLOW}步骤 5/15: 字典数据 (sys_dict_data.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_dict_data.sql" "字典数据"

echo -e "${YELLOW}步骤 6/15: 自动编码规则 (sys_auto_code.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code.sql" "自动编码基础数据"

echo -e "${YELLOW}步骤 7/15: 自动编码组成 (sys_auto_code_part.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code_part.sql" "自动编码组成"

echo -e "${YELLOW}步骤 8/15: 自动编码规则详情 (sys_auto_code_rule.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code_rule.sql" "自动编码规则详情"

echo -e "${YELLOW}步骤 9/15: 自动编码结果 (sys_auto_code_result.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/sys_auto_code_result.sql" "自动编码结果"

echo -e "${YELLOW}步骤 10/15: MES系统基础表 (mes_sys.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes_sys.sql" "MES系统基础表"

echo -e "${YELLOW}步骤 11/15: MES主数据模块 (mes-md.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-md.sql" "物料、客户、供应商等主数据"

echo -e "${YELLOW}步骤 12/15: MES生产模块 (mes-pro.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-pro.sql" "生产工单、工艺路线等"

echo -e "${YELLOW}步骤 13/15: MES仓储模块 (mes-wm.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-wm.sql" "仓库、库存、出入库等"

echo -e "${YELLOW}步骤 14/15: MES质量模块 (mes-qc.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-qc.sql" "质检、检验单等"

echo -e "${YELLOW}步骤 15/15: MES设备模块 (mes-dv.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-dv.sql" "设备管理"

echo -e "${YELLOW}步骤 16/15: MES日历模块 (mes-cal.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-cal.sql" "工作日历、班次等"

echo -e "${YELLOW}步骤 17/15: MES工装夹具模块 (mes-tm.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/mes-tm.sql" "工装夹具管理"

echo -e "${YELLOW}步骤 18/15: 报表文件表 (ureport_file_tbl.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/设计文档/数据库设计/ureport_file_tbl.sql" "报表文件存储"

echo -e "${YELLOW}步骤 19/15: 初始化配置 (inital.sql)${NC}"
execute_sql "$PROJECT_ROOT/doc/实施文档/inital.sql" "初始化配置数据"

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✓ 数据库初始化完成！${NC}"
echo -e "${GREEN}========================================${NC}"

# 显示统计信息
echo -e "\n${YELLOW}统计信息:${NC}"
docker exec -i ktg-mes-mysql mysql -h"$DB_HOST" -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" --default-character-set="$CHARSET" -e "
SELECT 
    COUNT(*) as 总表数 
FROM 
    information_schema.tables 
WHERE 
    table_schema = '$DB_NAME';"

echo -e "\n${GREEN}✓ 初始化完成！系统已就绪。${NC}"

