# KTG-MES 生产执行管理系统

<p align="center">
	<img alt="logo" width="96px" src="https://gitee.com/kutangguo/ktg-mes-ui/raw/master/src/assets/logo/logo.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">苦糖果MES</h1>
<h4 align="center">专业、通用、开源、免费的生产执行管理系统</h4>
<p align="center">
<a href='https://gitee.com/kutangguo/ktg-mes/stargazers'><img src='https://gitee.com/kutangguo/ktg-mes/badge/star.svg?theme=dark' alt='star'></img></a>
	<a href="https://gitee.com/kutangguo/ktg-mes/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

## 📋 系统简介

苦糖果MES系统是一款B/S结构、开源、免费的生产执行管理系统。

此系统基于本人多年离散智造行业的业务经验及J2EE项目经验开发。

主要目的是为国内离散制造业的中小企业提供一个专业化、通用性、低成本的MES系统解决方案。

系统将提供"售前"、"实施"、"用户培训"、"运维"等多个阶段的指导文档和教学视频，以帮助非IT专业人士顺利完成MES项目的实施应用。

## 🚀 快速开始

### 环境要求

- Docker 20.10+
- Docker Compose 1.29+
- 至少 4GB 可用内存
- 至少 10GB 可用磁盘空间

### 一键部署

```bash
# 1. 克隆项目
git clone https://gitee.com/kutangguo/ktg-mes.git
cd ktg-mes

# 2. 启动所有服务
docker-compose up -d

# 3. 等待服务启动完成（约2-3分钟）
docker-compose logs -f
```

### 访问地址

- **前端访问**: http://localhost:8000
- **后端API**: http://localhost:8007
- **数据库管理**: MySQL (localhost:8001)
- **Redis管理**: Redis (localhost:8002)
- **MinIO管理**: http://localhost:8004 (用户名: minioadmin, 密码: minioadmin123)
- **RabbitMQ管理**: http://localhost:8006 (用户名: admin, 密码: admin123)
- **Druid监控**: http://localhost:8000/druid/ (用户名: admin, 密码: 123456)

### 默认账户

- **用户名**: admin
- **密码**: admin123

## 📚 文档目录

### 📖 部署文档
- [Docker部署指南](doc/部署文档/DEPLOYMENT.md) - 详细的Docker部署说明
- [Druid监控配置](doc/部署文档/DRUID_MONITOR_ACCESS.md) - 数据库连接池监控配置

### 🔧 开发文档
- [WebSocket修复说明](doc/开发文档/WEBSOCKET_FIX_COMPLETED.md) - 打印功能WebSocket连接修复
- [ECharts图表修复](doc/开发文档/ECHARTS_FIX_COMPLETED.md) - 图表组件初始化错误修复
- [前端问题修复](doc/开发文档/FRONTEND_ISSUES_FIX.md) - 前端问题详细修复说明

### 📊 系统功能

#### PC端功能
- **系统管理** - 用户、角色、权限管理
- **主数据** - 物料产品管理、工作站设置
- **生产管理** - 生产排产、工单管理
- **质量管理** - 质检流程、缺陷管理
- **生产排班** - 节假日设置、排班日历
- **仓储管理** - 库存现有量、条码管理
- **设备管理** - 设备维护、保养计划
- **统计报表** - 生产报表、质量报表
- **大屏展示** - 生产看板、实时监控

#### 移动端功能
- **Android端** - 生产管理、质量管理、仓储管理、设备管理
- **触控屏端** - 登录、生产、质量、工序、设备
- **PDA端** - 仓储、生产

## 🏗️ 系统架构

当前版本基于RuoYi前后端分离版本开发。详情请参照：https://gitee.com/y_project/RuoYi-Vue

### 技术栈

**后端技术：**
- Spring Boot 2.5.0
- Spring Security 5.3.0
- MyBatis Plus 3.4.0
- Druid 1.2.6
- Redis 6.2
- MySQL 5.7

**前端技术：**
- Vue 2.6.11
- Element UI 2.15.0
- ECharts 5.0.0
- Axios 0.21.0

**部署技术：**
- Docker & Docker Compose
- Nginx 1.21
- MinIO 对象存储

## 📁 项目结构

```
ktg-mes/
├── frontend/                 # 前端源码
├── ktg-admin/               # 后端主模块
├── ktg-common/              # 公共模块
├── ktg-framework/           # 框架核心
├── ktg-generator/           # 代码生成器
├── ktg-mes/                 # MES业务模块
├── ktg-print/               # 打印模块
├── ktg-quartz/              # 定时任务
├── ktg-system/              # 系统管理
├── sql/                     # 数据库脚本
├── config/                  # 配置文件
├── nginx/                   # Nginx配置
├── doc/                     # 项目文档
│   ├── 部署文档/            # 部署相关文档
│   ├── 开发文档/            # 开发相关文档
│   └── 注意事项/            # 重要注意事项
└── docker-compose.yml       # Docker编排文件
```

## 🗄️ 数据库

### 数据库统计
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
| **其他模块** | 8 | 代码生成、报表、工装等 |

## 🔧 常用命令

```bash
# 查看服务状态
docker-compose ps

# 查看服务日志
docker-compose logs -f [service-name]

# 重启服务
docker-compose restart [service-name]

# 停止服务
docker-compose down

# 停止并删除数据
docker-compose down -v

# 重新构建应用
docker-compose build ktg-mes-app
```

## 🐛 故障排除

### 1. 端口冲突
如果端口被占用，可以修改 `docker-compose.yml` 中的端口映射。

### 2. 内存不足
如果内存不足，可以：
- 增加系统内存
- 减少服务数量（如移除RabbitMQ）
- 调整JVM参数

### 3. 数据库连接失败
检查MySQL容器是否正常启动：
```bash
docker-compose logs mysql
```

### 4. 应用启动失败
检查应用日志：
```bash
docker-compose logs ktg-mes-app
```

## 📞 技术支持

- **前端代码地址**: https://gitee.com/kutangguo/ktg-mes-ui
- **PC端在线体验**: [http://www.029tec.com](http://www.029tec.com/) 
- **QQ交流群**: 411641505
- **项目文档**: `/doc` 目录

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源协议。

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者和用户。

支持作者请Star ⭐

---

**最后更新**: 2025-09-30  
**版本**: v1.0.0  
**维护者**: KTG-MES开发团队