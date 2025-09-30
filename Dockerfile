# 使用官方Maven镜像构建应用
FROM maven:3.8.4-openjdk-8 AS builder

# 设置工作目录
WORKDIR /app

# 复制pom.xml文件
COPY pom.xml .

# 复制所有源代码
COPY . .

# 构建应用
RUN mvn clean package -Dmaven.test.skip=true

# 使用官方OpenJDK镜像运行应用
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制jar文件
COPY --from=builder /app/ktg-admin/target/ktg-admin.jar app.jar

# 创建必要的目录
RUN mkdir -p /app/logs /app/upload /app/config

# 暴露端口
EXPOSE 8080

# 设置JVM参数
ENV JAVA_OPTS="-Xmx512m -Xms256m -Djava.security.egd=file:/dev/./urandom"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.config.location=/app/config/"]