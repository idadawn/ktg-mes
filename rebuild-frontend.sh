#!/bin/bash

# 前端重新编译脚本
# 用于应用 WebSocket 连接错误修复

set -e

echo "============================================"
echo "前端代码重新编译"
echo "============================================"

cd "$(dirname "$0")"

echo ""
echo "📦 步骤 1: 停止相关容器..."
docker stop ktg-mes-nginx || true

echo ""
echo "🔨 步骤 2: 编译前端代码..."
cd frontend

# 检查 Node.js 版本
NODE_VERSION=$(node --version 2>/dev/null || echo "未安装")
echo "Node.js 版本: $NODE_VERSION"

# 如果本地 Node.js 版本不兼容，使用 Docker 编译
if [[ "$NODE_VERSION" < "v16" ]] || [[ "$NODE_VERSION" == "未安装" ]]; then
    echo "⚠️  本地 Node.js 版本不兼容，使用 Docker 容器编译..."
    
    # 创建临时编译容器
    docker run --rm \
        -v "$(pwd):/app" \
        -w /app \
        node:18-alpine \
        sh -c "npm run build:prod"
else
    echo "✅ 使用本地 Node.js 编译..."
    export NODE_OPTIONS=--openssl-legacy-provider
    npm run build:prod
fi

echo ""
echo "📂 步骤 3: 复制编译结果..."
# 确保目标目录存在
mkdir -p ../frontend_dist
# 复制 dist 目录内容
cp -r dist/* ../frontend_dist/

echo ""
echo "🔄 步骤 4: 重启 Nginx..."
docker start ktg-mes-nginx
sleep 3

echo ""
echo "✅ 编译完成！"
echo ""
echo "验证修复："
echo "1. 打开浏览器访问: http://localhost:8000"
echo "2. 按 F12 打开开发者工具"
echo "3. 查看 Console 标签"
echo "4. 应该不再看到 WebSocket 连接错误"
echo ""
echo "============================================"

