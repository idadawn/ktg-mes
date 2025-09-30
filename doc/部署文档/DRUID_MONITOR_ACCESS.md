# Druid 监控页面访问说明

## ✅ 配置已完成

Druid 数据库连接池监控页面已成功启用并配置。

## 📍 访问地址

您可以通过以下任意一个地址访问 Druid 监控页面：

1. **直接访问**：
   ```
   http://localhost:8000/druid/
   ```

2. **通过监控路径访问**：
   ```
   http://localhost:8000/monitor/druid/
   ```

3. **直接访问应用端口**：
   ```
   http://localhost:8007/druid/
   ```

## 🔐 登录凭证

- **用户名**: `admin`
- **密码**: `123456`

## 📊 功能说明

Druid 监控页面提供以下功能：

1. **数据源监控**：查看数据库连接池的状态、活跃连接数等
2. **SQL监控**：查看执行的SQL语句、执行时间、执行次数等
3. **SQL防火墙**：查看被拦截的SQL语句
4. **Web应用**：查看Web应用的访问统计
5. **URI监控**：查看各个URI的访问情况
6. **Session监控**：查看Session信息
7. **Spring监控**：查看Spring Bean的信息

## ⚙️ 配置详情

### 应用配置（config/application.yml）

```yaml
spring:
  datasource:
    druid:
      # 配置DruidStatViewServlet
      statViewServlet:
        enabled: true
        # 设置白名单，不填则允许所有访问
        allow:
        url-pattern: /druid/*
        # 控制台管理用户名和密码
        login-username: admin
        login-password: 123456
      filter:
        stat:
          enabled: true
          # 慢SQL记录
          log-slow-sql: true
          slow-sql-millis: 1000
          merge-sql: true
        wall:
          config:
            multi-statement-allow: true
```

### Nginx 配置（nginx/conf.d/default.conf）

```nginx
# Druid监控页面代理
location /druid/ {
    proxy_pass http://ktg-mes-app:8080/druid/;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
}

# 监控页面代理（兼容 /monitor/druid 路径）
location /monitor/ {
    proxy_pass http://ktg-mes-app:8080/;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
}
```

## 🛠️ 维护说明

### 修改登录密码

如需修改 Druid 监控页面的登录密码，请编辑 `config/application.yml` 文件：

```yaml
spring:
  datasource:
    druid:
      statViewServlet:
        login-username: your-username
        login-password: your-password
```

修改后需要重启应用容器：
```bash
docker restart ktg-mes-app
```

### 设置IP白名单

如需限制访问IP，可以在配置中设置白名单：

```yaml
spring:
  datasource:
    druid:
      statViewServlet:
        allow: 127.0.0.1,192.168.1.0/24
```

### 调整慢SQL阈值

默认超过 1000 毫秒的SQL会被标记为慢SQL，如需调整：

```yaml
spring:
  datasource:
    druid:
      filter:
        stat:
          slow-sql-millis: 2000  # 调整为2秒
```

## 📝 注意事项

1. **生产环境建议**：
   - 修改默认的用户名和密码
   - 设置IP白名单，限制访问
   - 定期查看慢SQL，优化数据库性能

2. **性能影响**：
   - Druid 监控对性能影响很小
   - 建议在生产环境保持启用以便于问题排查

3. **数据重置**：
   - 监控数据存储在内存中
   - 应用重启后监控数据会重置

## 🔗 相关链接

- Druid 官方文档：https://github.com/alibaba/druid/wiki
- Druid 监控配置说明：https://github.com/alibaba/druid/wiki/配置_StatViewServlet配置

---

**配置完成日期**: 2025-09-30

