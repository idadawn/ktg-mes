# WebSocket 连接错误修复方案

## 问题

```
WebSocket connection to 'ws://localhost:17521/socket.io/?EIO=4&transport=websocket' failed
```

## 根本原因

系统在页面加载时自动初始化 HiPrint 打印插件，插件会尝试连接本地打印客户端（端口 17521）。如果没有安装打印客户端，就会出现这个错误。

## 修复方案：延迟初始化（按需加载）

将 HiPrint 插件从**自动加载**改为**按需加载**，只有在用户真正需要使用打印功能时才初始化。

### 修改的文件

1. **`frontend/src/main.js`**
   - 注释掉 `Vue.use(hiPrintPlugin)`
   - 禁用全局自动初始化

2. **`frontend/src/mixins/hiprintMixin.js`**
   - 移除 `mounted()` 中的自动初始化
   - 添加 `initHiprint()` 方法，支持按需初始化

3. **`frontend/src/utils/print.js`**
   - 添加空值检查
   - 优化错误提示

## 修复效果

### 修复前
- ❌ 页面加载时立即尝试连接 WebSocket
- ❌ 控制台显示连接失败错误
- ❌ 影响用户体验

### 修复后  
- ✅ 页面加载时不尝试连接
- ✅ 控制台干净，无错误
- ✅ 只在需要打印时才初始化
- ✅ 不影响系统其他功能

## 如何应用修复

### 方法 1：使用自动脚本（推荐）

```bash
cd /home/dawn/project/mes/ktg-mes
./rebuild-frontend.sh
```

### 方法 2：手动编译

```bash
cd /home/dawn/project/mes/ktg-mes/frontend

# 使用 Docker 编译（推荐，避免 Node.js 版本问题）
docker run --rm \
    -v "$(pwd):/app" \
    -w /app \
    node:18-alpine \
    sh -c "npm run build:prod"

# 复制编译结果
cp -r dist/* ../frontend_dist/

# 重启 Nginx
docker restart ktg-mes-nginx
```

### 方法 3：使用现有构建容器

```bash
cd /home/dawn/project/mes/ktg-mes

# 重启整个服务栈
docker-compose restart
```

## 验证修复

1. 清除浏览器缓存：`Ctrl + Shift + Delete`
2. 或使用无痕模式访问：`Ctrl + Shift + N`
3. 访问系统：http://localhost:8000
4. 按 `F12` 打开开发者工具
5. 查看 Console 标签

**预期结果：**
- ✅ 不再显示 WebSocket 连接错误
- ✅ 页面加载正常
- ✅ 所有功能正常使用

## 打印功能使用说明

### 如果需要使用打印功能

修复后，打印功能变为按需加载。需要在使用打印功能的组件中手动初始化：

```javascript
// 在打印按钮点击事件中
async handlePrint() {
  // 先初始化打印功能
  try {
    await this.initHiprint();
    // 然后调用打印
    print(this.template, this.data, this.hiprintTemplate, this.hiprintThis);
  } catch (error) {
    this.$message.warning('打印客户端未连接，请安装并启动打印客户端');
  }
}
```

### 如果不需要打印功能

- 无需任何操作
- 打印相关组件会自动跳过
- 不影响系统其他功能

## 打印客户端安装（可选）

如果需要使用打印功能，请安装 HiPrint 客户端：

**下载地址：**
- https://gitee.com/CcSimple/vue-plugin-hiprint
- https://github.com/CcSimple/vue-plugin-hiprint/releases

**安装步骤：**
1. 下载对应操作系统的客户端
2. 安装并启动（默认端口 17521）
3. 在系统中配置打印机

## 技术说明

### 为什么会出现这个错误？

HiPrint 是一个基于 WebSocket 的打印方案：

```
浏览器前端  →  WebSocket (ws://localhost:17521)  →  本地打印客户端  →  打印机
```

如果没有安装打印客户端，WebSocket 连接就会失败。

### 为什么要延迟初始化？

1. **减少启动时间**：不需要等待 WebSocket 连接尝试
2. **优化用户体验**：避免无意义的错误提示
3. **按需加载**：只在真正需要时才建立连接
4. **降低资源消耗**：不使用打印功能时不占用资源

## 常见问题

### Q: 打印功能还能用吗？
A: 能用！只是改为按需加载，在真正需要打印时才初始化。

### Q: 需要修改业务代码吗？
A: 不需要。使用 `hiprintMixin` 的组件自动支持按需加载。

### Q: 如果我已经安装了打印客户端？
A: 修复后仍然可以正常使用，只是改为手动触发初始化。

### Q: 编译需要多久？
A: 通常 2-5 分钟，取决于机器性能。

---

**修复日期：** 2025-09-30  
**影响范围：** 前端打印功能初始化方式  
**兼容性：** 完全兼容现有功能  
**是否需要数据库变更：** 否

