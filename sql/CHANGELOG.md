# KTG-MES 数据库初始化变更日志

## [2025-10-01] - 完整数据库初始化完成

### ✅ 新增功能

#### 1. 完整的数据库初始化体系
- **新增** `reinit_database.sh` - 智能数据库重新初始化脚本
  - 自动备份功能
  - 彩色输出显示
  - 详细的执行日志
  - 模块化表统计
  
- **新增** `mes-report.sql` - 报表模块表
  - `report_chart` - 图形报表组件表
  - `report_chart_role` - 图形报表角色关联表
  
- **新增** `print_client.sql` - 打印客户端表
  - `print_client` - 打印客户端管理表
  
- **新增** `README.md` - 完整的使用文档和说明

#### 2. 数据库表统计

**总表数**: 176个

| 模块 | 表数量 | 主要表 |
|------|--------|--------|
| 仓储模块 (wm_*) | 64 | 库存、出入库、盘点等 |
| 系统表 (sys_*) | 23 | 用户、角色、菜单、字典等 |
| 生产模块 (pro_*) | 20 | 工单、工艺路线、报工等 |
| 质量模块 (qc_*) | 16 | 质检、检验单、缺陷等 |
| 主数据 (md_*) | 14 | 物料、客户、供应商等 |
| 设备模块 (dv_*) | 13 | 设备管理、维护保养等 |
| Quartz (QRTZ_*) | 11 | 定时任务调度 |
| 日历模块 (cal_*) | 7 | 工作日历、班次等 |
| 报表模块 (report_*) | 2 | 图表组件、角色权限 |
| 工装模块 (tm_*) | 2 | 工装夹具管理 |
| 代码生成 (gen_*) | 2 | 代码生成器 |
| 打印模块 (print_*) | 1 | 打印客户端管理 |
| 其他表 | 1 | 报表文件存储 |

### 🔧 问题修复

#### 字符编码问题
- ✅ 修复 `sys_dept` 表中文乱码
- ✅ 修复 `sys_role` 表中文乱码
- ✅ 修复 `sys_menu` 表中文乱码
- ✅ 修复 `sys_user` 表中文乱码
- ✅ 修复 `sys_dict_data` 表中文乱码
- ✅ 修复 `sys_job` 表中文乱码
- ✅ 统一使用 `utf8mb4` 字符集

#### 表结构问题
- ✅ 添加 `sys_dept.dept_code` 字段
- ✅ 创建 `report_chart` 表
- ✅ 创建 `report_chart_role` 表
- ✅ 创建 `print_client` 表

#### 配置问题
- ✅ 修复 `application.yml` 中 `captchaType` 配置
- ✅ 添加国际化配置 `spring.messages.basename`
- ✅ 优化数据库连接参数（添加重连机制）
- ✅ 配置正确的字符编码（utf8mb4）

### 📊 接口验证

所有关键接口均已验证通过：

```bash
✅ /prod-api/login                           # 登录接口
✅ /prod-api/captchaImage                    # 验证码接口
✅ /prod-api/getInfo                         # 用户信息接口
✅ /prod-api/getRouters                      # 路由菜单接口
✅ /prod-api/system/dept/treeselect          # 部门树接口
✅ /prod-api/system/menu/treeselect          # 菜单树接口
✅ /prod-api/system/user/list                # 用户列表接口
✅ /prod-api/system/role/list                # 角色列表接口
✅ /prod-api/system/dict/data/type/*         # 字典接口
✅ /prod-api/print/client/getAll             # 打印客户端接口
✅ /prod-api/mes/report/chart/getMyCharts    # 报表图表接口
✅ /prod-api/mes/pro/workorder/getHomeList   # 工单列表接口
```

### 📦 SQL文件清单

#### 核心系统
- `sql/ry_20210908.sql` - 若依核心系统表
- `sql/quartz.sql` - Quartz定时任务表

#### 新增模块
- `sql/mes-report.sql` - 报表模块（新增）
- `sql/print_client.sql` - 打印客户端（新增）

#### 实施文档
- `doc/实施文档/sys_menu.sql` - 菜单权限数据
- `doc/实施文档/sys_dict_type.sql` - 字典类型
- `doc/实施文档/sys_dict_data.sql` - 字典数据
- `doc/实施文档/sys_auto_code*.sql` - 自动编码规则
- `doc/实施文档/inital.sql` - 初始化配置

#### MES业务模块
- `doc/设计文档/数据库设计/mes_sys.sql` - MES系统基础
- `doc/设计文档/数据库设计/mes-md.sql` - 主数据模块
- `doc/设计文档/数据库设计/mes-pro.sql` - 生产模块
- `doc/设计文档/数据库设计/mes-wm.sql` - 仓储模块
- `doc/设计文档/数据库设计/mes-qc.sql` - 质量模块
- `doc/设计文档/数据库设计/mes-dv.sql` - 设备模块
- `doc/设计文档/数据库设计/mes-cal.sql` - 日历模块
- `doc/设计文档/数据库设计/mes-tm.sql` - 工装夹具模块
- `doc/设计文档/数据库设计/ureport_file_tbl.sql` - 报表文件

### 🚀 使用方式

#### 方法一：一键初始化（推荐）
```bash
cd /home/dawn/project/mes/ktg-mes
bash sql/reinit_database.sh
```

#### 方法二：Docker命令
详见 `sql/README.md`

### ⚠️ 重要提示

1. **字符集要求**: 必须使用 `utf8mb4` 字符集
2. **执行顺序**: 必须按照指定顺序执行SQL文件
3. **备份建议**: 使用 `reinit_database.sh` 会自动备份
4. **重启服务**: 初始化后必须重启后端服务

### 🎯 系统状态

- ✅ 数据库表: 176/176 (100%)
- ✅ 字符编码: UTF-8 (utf8mb4)
- ✅ 中文显示: 正常
- ✅ 接口测试: 全部通过
- ✅ 功能完整: 100%

### 📝 已知问题

无已知问题。所有功能正常运行。

### 🔜 下一步计划

1. 持续监控系统运行状态
2. 根据业务需求优化表结构
3. 完善数据字典和文档
4. 添加更多测试数据

---

## 技术细节

### 数据库连接配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://mysql:3306/ktgmes?useUnicode=true&characterEncoding=utf8mb4&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8&autoReconnect=true&useOldAliasMetadataBehavior=true
```

### 重要参数说明

- `characterEncoding=utf8mb4` - 使用utf8mb4字符集
- `autoReconnect=true` - 自动重连
- `useOldAliasMetadataBehavior=true` - 兼容性设置

### 备份文件位置

所有自动备份文件存储在：
```
/home/dawn/project/mes/ktg-mes/sql/backups/
```

文件名格式：
```
ktgmes_backup_YYYYMMDD_HHMMSS.sql
```

---

**维护者**: KTG-MES开发团队  
**更新日期**: 2025-10-01  
**版本**: v1.0.0

