# 使用Node.js镜像构建前端
FROM node:18-alpine

# 设置工作目录
WORKDIR /app

# 复制package.json和package-lock.json
COPY package*.json ./

# 安装依赖
RUN npm config set registry https://registry.npmmirror.com/
RUN npm install

# 复制源代码
COPY . .

# 构建生产版本
RUN npm run build:prod

# 复制构建结果到输出目录
CMD ["cp", "-r", "dist/", "/app/"]