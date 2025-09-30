# KTG-MES Docker 部署指南

## 环境要求

- Docker 20.10+
- Docker Compose 1.29+
- 至少 4GB 可用内存
- 至少 10GB 可用磁盘空间

## 快速开始

### 1. 克隆项目
```bash
git clone https://gitee.com/kutangguo/ktg-mes.git
cd ktg-mes
```

### 2. 构建并启动服务
```bash
# 构建应用镜像
docker-compose build

# 启动所有服务
docker-compose up -d
```

### 3. 查看服务状态
```bash
docker-compose ps
```

### 4. 查看日志
```bash
# 查看应用日志
docker-compose logs -f ktg-mes-app

# 查看所有服务日志
docker-compose logs -f
```

## 服务访问地址

- **前端访问**: http://localhost:8000
- **后端API**: http://localhost:8007
- **数据库管理**: MySQL (localhost:8001)
- **Redis管理**: Redis (localhost:8002)
- **MinIO管理**: http://localhost:8004 (用户名: minioadmin, 密码: minioadmin123)
- **RabbitMQ管理**: http://localhost:8006 (用户名: admin, 密码: admin123)

## 前端构建说明

前端项目会自动构建并部署到Nginx中：
- 前端源码位于 `frontend/` 目录
- 构建过程在 `ktg-mes-ui-builder` 容器中完成
- 构建结果存储在 `frontend_dist` 卷中
- Nginx直接服务构建后的静态文件

## 默认账户

- **用户名**: admin
- **密码**: admin123

## 数据初始化

### 1. 导入数据库
数据库初始化脚本位于 `sql/` 目录，容器启动时会自动执行。

### 2. 手动导入（如果需要）
```bash
# 进入MySQL容器
docker exec -it ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes

# 导入SQL文件
source /docker-entrypoint-initdb.d/mes_sys.sql;
```

## 配置文件说明

### 应用配置
- `config/application-prod.yml`: 生产环境配置
- 可修改数据库连接、Redis配置等

### Nginx配置
- `nginx/conf.d/default.conf`: Nginx代理配置
- 可修改端口、域名等

## 数据持久化

所有数据都保存在Docker volumes中：

- `mysql_data`: MySQL数据库数据
- `redis_data`: Redis缓存数据
- `minio_data`: MinIO文件存储数据
- `rabbitmq_data`: RabbitMQ消息队列数据

## 常用命令

```bash
# 停止服务
docker-compose down

# 停止并删除数据
docker-compose down -v

# 重启服务
docker-compose restart

# 重新构建应用
docker-compose build ktg-mes-app

# 查看服务状态
docker-compose ps

# 查看服务日志
docker-compose logs [service-name]
```

## 故障排除

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

## 生产环境部署

### 1. 安全配置
- 修改默认密码
- 启用HTTPS
- 配置防火墙

### 2. 性能优化
- 调整数据库连接池参数
- 配置Redis集群
- 启用CDN

### 3. 监控配置
- 配置应用监控
- 设置日志收集
- 配置告警规则

## 备份与恢复

### 备份数据
```bash
# 备份MySQL数据
docker exec ktg-mes-mysql mysqldump -uktgmes -pktgmes123 ktgmes > backup.sql

# 备份Redis数据
docker exec ktg-mes-redis redis-cli save
```

### 恢复数据
```bash
# 恢复MySQL数据
docker exec -i ktg-mes-mysql mysql -uktgmes -pktgmes123 ktgmes < backup.sql
```