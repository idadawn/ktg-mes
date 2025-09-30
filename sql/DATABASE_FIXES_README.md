# 数据库表结构修复说明

## 问题描述
代码中引用的数据库列在实际数据库表中缺失，导致SQL查询时出现 `Unknown column` 错误。

## 修复日期
2025-09-30

## 已修复的问题

### 1. pro_workorder 表 - 缺少 unit_name 列
**错误信息:**
```
Unknown column 'unit_name' in 'field list'
```

**修复方案:**
```sql
ALTER TABLE pro_workorder 
ADD COLUMN unit_name VARCHAR(64) NULL COMMENT '单位名称' 
AFTER unit_of_measure;
```

**影响的API:**
- `/prod-api/mes/pro/workorder/getHomeList`
- 其他查询工单的相关接口

---

### 2. wm_warehouse 表 - 缺少 frozen_flag 列
**错误信息:**
```
Unknown column 'w.frozen_flag' in 'field list'
```

**修复方案:**
```sql
ALTER TABLE wm_warehouse 
ADD COLUMN frozen_flag CHAR(1) DEFAULT 'N' COMMENT '冻结标识' 
AFTER charge;
```

**影响的API:**
- `/prod-api/mes/wm/warehouse/getTreeList`
- 其他查询仓库的相关接口

---

### 3. md_workshop 表 - 缺少 charge_id 列
**错误信息:**
```
Unknown column 'charge_id' in 'field list'
```

**修复方案:**
```sql
ALTER TABLE md_workshop 
ADD COLUMN charge_id BIGINT(20) NULL COMMENT '负责人ID' 
AFTER charge;
```

**影响的API:**
- `/prod-api/mes/md/workshop/listAll`
- 其他查询车间的相关接口

---

## 如何使用修复脚本

### 方法1: 使用汇总脚本（推荐）
一次性修复所有问题：
```bash
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes < sql/fix_missing_columns_all.sql
```

### 方法2: 单独执行各个修复脚本
```bash
# 修复 pro_workorder 表
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes < sql/fix_add_unit_name_column.sql

# 修复 wm_warehouse 表
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes < sql/fix_add_frozen_flag_column.sql

# 修复 md_workshop 表
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes < sql/fix_add_charge_id_column.sql
```

### 方法3: 直接在MySQL中执行
```bash
# 进入MySQL容器
docker exec -it ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes

# 在MySQL命令行中执行各个ALTER TABLE语句
```

---

## 验证修复

执行修复后，可以使用以下命令验证：

```bash
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes -e "
SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME IN ('pro_workorder', 'wm_warehouse', 'md_workshop')
  AND COLUMN_NAME IN ('unit_name', 'frozen_flag', 'charge_id')
ORDER BY TABLE_NAME, COLUMN_NAME;
"
```

预期输出应该包含所有三个新添加的列。

---

## 相关文件

- `fix_missing_columns_all.sql` - 所有修复的汇总脚本（推荐使用）
- `fix_add_unit_name_column.sql` - 修复 pro_workorder 表
- `fix_add_frozen_flag_column.sql` - 修复 wm_warehouse 表
- `fix_add_charge_id_column.sql` - 修复 md_workshop 表

---

## 注意事项

1. **备份数据**: 执行任何DDL操作前，务必先备份数据库
2. **生产环境**: 在生产环境执行前，建议先在测试环境验证
3. **权限要求**: 执行ALTER TABLE需要相应的数据库权限
4. **服务重启**: 修复完成后无需重启应用，数据库变更会立即生效

---

## 问题根源分析

这些问题的出现可能是由于：

1. **版本不一致**: 数据库使用的是旧版本的表结构，而代码已经升级
2. **缺少迁移**: 代码升级后，相应的数据库迁移脚本没有执行
3. **环境差异**: 开发环境和生产环境的数据库版本不同步

---

## 建议

1. **使用数据库迁移工具**: 考虑使用 Flyway 或 Liquibase 来管理数据库schema版本
2. **版本控制**: 将所有数据库变更脚本纳入版本控制
3. **自动化部署**: 将数据库迁移纳入CI/CD流程
4. **定期检查**: 定期检查代码与数据库的一致性

---

## 联系信息

如有问题，请联系开发团队。

