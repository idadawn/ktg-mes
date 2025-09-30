# KTG-MES 数据库初始化说明

## 📋 概述

本目录包含KTG-MES系统完整的数据库初始化脚本和工具。

## 🗄️ 数据库统计

- **总表数**: 176个
- **字符集**: UTF-8 (utf8mb4)
- **数据库引擎**: InnoDB

### 模块表数量分布

| 模块 | 表数量 | 说明 |
|------|--------|------|
| **仓储模块 (wm_*)** | 64 | 库存、出入库、盘点等 |
| **系统表 (sys_*)** | 23 | 用户、角色、菜单、字典等 |
| **生产模块 (pro_*)** | 20 | 工单、工艺路线、报工等 |
| **质量模块 (qc_*)** | 16 | 质检、检验单、缺陷等 |
| **主数据 (md_*)** | 14 | 物料、客户、供应商等 |
| **设备模块 (dv_*)** | 13 | 设备管理、维护保养等 |
| **Quartz (QRTZ_*)** | 11 | 定时任务调度 |
| **日历模块 (cal_*)** | 7 | 工作日历、班次等 |
| **代码生成 (gen_*)** | 2 | 代码生成器 |
| **报表模块 (report_*)** | 2 | 图表组件 |
| **工装模块 (tm_*)** | 2 | 工装夹具管理 |
| **打印模块 (print_*)** | 1 | 打印客户端管理 |
| **其他表** | 1 | 报表文件存储 |

## 🚀 快速开始

### 方法一：使用Shell脚本（推荐）

```bash
# 完整重新初始化数据库（会备份现有数据）
cd /home/dawn/project/mes/ktg-mes
bash sql/reinit_database.sh
```

**注意**: 此脚本会：
1. 自动备份当前数据库到 `sql/backups/` 目录
2. 清空所有表
3. 重新导入所有数据
4. 显示统计信息

### 方法二：Docker命令逐步执行

```bash
cd /home/dawn/project/mes/ktg-mes

# 1. 核心系统表
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < sql/ry_20210908.sql

# 2. Quartz调度表
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < sql/quartz.sql

# 3. 菜单权限数据
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/sys_menu.sql

# 4. 字典数据
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/sys_dict_type.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/sys_dict_data.sql

# 5. 自动编码规则
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/sys_auto_code.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/sys_auto_code_part.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/sys_auto_code_rule.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/sys_auto_code_result.sql

# 6. MES业务模块
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes_sys.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes-md.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes-pro.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes-wm.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes-qc.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes-dv.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes-cal.sql
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/mes-tm.sql

# 7. 报表模块
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < sql/mes-report.sql

# 8. 打印客户端表
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < sql/print_client.sql

# 9. 报表文件表
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/设计文档/数据库设计/ureport_file_tbl.sql

# 10. 初始化配置
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 < doc/实施文档/inital.sql

# 11. 重启后端服务
docker restart ktg-mes-app
```

## 📁 文件说明

### 核心SQL文件

| 文件 | 说明 |
|------|------|
| `ry_20210908.sql` | 若依核心系统表（用户、角色、菜单等） |
| `quartz.sql` | Quartz定时任务调度表 |
| `mes-report.sql` | 报表模块表（新增） |
| `print_client.sql` | 打印客户端表（新增） |

### 实施文档SQL

| 文件路径 | 说明 |
|----------|------|
| `doc/实施文档/sys_menu.sql` | 系统菜单权限完整数据 |
| `doc/实施文档/sys_dict_type.sql` | 字典类型定义 |
| `doc/实施文档/sys_dict_data.sql` | 字典数据明细 |
| `doc/实施文档/sys_auto_code*.sql` | 自动编码规则相关表和数据 |
| `doc/实施文档/inital.sql` | 初始化配置数据 |

### MES业务模块SQL

| 文件路径 | 说明 |
|----------|------|
| `doc/设计文档/数据库设计/mes_sys.sql` | MES系统基础表 |
| `doc/设计文档/数据库设计/mes-md.sql` | 主数据模块（物料、客户、供应商等） |
| `doc/设计文档/数据库设计/mes-pro.sql` | 生产模块（工单、工艺路线等） |
| `doc/设计文档/数据库设计/mes-wm.sql` | 仓储模块（库存、出入库等） |
| `doc/设计文档/数据库设计/mes-qc.sql` | 质量模块（质检、检验单等） |
| `doc/设计文档/数据库设计/mes-dv.sql` | 设备模块（设备管理、维护等） |
| `doc/设计文档/数据库设计/mes-cal.sql` | 日历模块（工作日历、班次等） |
| `doc/设计文档/数据库设计/mes-tm.sql` | 工装夹具模块 |
| `doc/设计文档/数据库设计/ureport_file_tbl.sql` | 报表文件存储 |

### 工具脚本

| 文件 | 说明 |
|------|------|
| `reinit_database.sh` | 完整数据库重新初始化脚本（含备份功能） |
| `ktgmes_init.sql` | SQL导入引导文件 |

## 🔧 字符编码问题

**重要**: 所有SQL文件均使用 `utf8mb4` 字符集，确保中文字符正确显示。

执行SQL时必须指定字符集：
```bash
--default-character-set=utf8mb4
```

## 📦 默认账户

初始化后的默认管理员账户：

- **用户名**: admin
- **密码**: admin123
- **角色**: 超级管理员

## ⚠️ 注意事项

1. **备份重要**: 使用 `reinit_database.sh` 会自动备份，手动执行请先备份数据
2. **字符集**: 必须使用 `utf8mb4` 字符集
3. **执行顺序**: 必须按照README中的顺序执行SQL文件
4. **依赖关系**: 部分表之间存在外键关系，顺序错误会导致失败
5. **重启服务**: 初始化完成后必须重启后端服务

## 🐛 常见问题

### 1. 中文乱码问题

**解决方案**: 确保所有SQL执行时都使用 `--default-character-set=utf8mb4` 参数

### 2. 表已存在错误

**解决方案**: 使用 `reinit_database.sh` 脚本会自动清空所有表后重新创建

### 3. 外键约束错误

**解决方案**: 按照README中的顺序执行SQL文件

## 📊 验证初始化

```bash
# 检查表数量
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 -e "SELECT COUNT(*) as total_tables FROM information_schema.tables WHERE table_schema = 'ktgmes';"

# 预期结果: 176

# 检查各模块表数量
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes --default-character-set=utf8mb4 -e "
SELECT 
    CASE 
        WHEN table_name LIKE 'sys_%' THEN '系统表(sys_*)'
        WHEN table_name LIKE 'pro_%' THEN '生产模块(pro_*)'
        WHEN table_name LIKE 'wm_%' THEN '仓储模块(wm_*)'
        WHEN table_name LIKE 'qc_%' THEN '质量模块(qc_*)'
        WHEN table_name LIKE 'report_%' THEN '报表模块(report_*)'
        ELSE '其他'
    END as module,
    COUNT(*) as count
FROM information_schema.tables 
WHERE table_schema = 'ktgmes'
GROUP BY module
ORDER BY count DESC;
"
```

## 📝 更新日志

### 2025-09-30

- ✅ 创建完整的数据库初始化脚本
- ✅ 修复所有字符编码问题
- ✅ 添加 `sys_dept` 表的 `dept_code` 字段
- ✅ 新增报表模块表 (`report_chart`, `report_chart_role`)
- ✅ 创建自动化初始化Shell脚本
- ✅ 导入所有MES业务模块数据
- ✅ 验证所有176个表正确创建
- ✅ 添加打印客户端表 (`print_client`)

## 📞 技术支持

如有问题，请参考：
- 项目文档: `/doc`
- 数据库设计文档: `/doc/设计文档/数据库设计/数据库设计.xlsx`
- 系统权限清单: `/doc/实施文档/系统权限清单.xlsx`

---

**最后更新**: 2025-09-30  
**维护者**: KTG-MES开发团队

