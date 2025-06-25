# 宿管登录统一修复

## 问题描述

用户报告：输入正确的宿管账号密码显示"错误的账号或密码"，希望宿管登录与管理员登录使用相同的验证逻辑。

## 问题分析

通过对比管理员登录和宿管登录的代码，发现了以下差异：

### 1. 数据格式差异
**管理员登录发送的数据：**
```javascript
{
  name: form.value.username,
  password: form.value.password
}
```

**宿管登录发送的数据：**
```javascript
{
  id: form.value.id,
  password: form.value.password
}
```

### 2. 响应处理差异
**管理员登录响应处理：**
```javascript
if (response.data === "登录成功") {
  // 登录成功逻辑
} else {
  ElMessage.error('用户名或密码错误')
}
```

**宿管登录响应处理：**
```javascript
if (response.data.success) {
  // 登录成功逻辑
} else {
  ElMessage.error(response.data.message || '登录失败')
}
```

## 修复内容

### 1. 统一数据格式

修改 `src/views/auth/DormitoryManagerLogin.vue` 中的登录请求：

```javascript
// 修改前
const response = await dormitoryManagerApi.login({
  id: form.value.id,
  password: form.value.password
})

// 修改后
const response = await dormitoryManagerApi.login({
  name: form.value.id,  // 使用name字段，与管理员登录一致
  password: form.value.password
})
```

### 2. 统一响应处理

修改响应处理逻辑：

```javascript
// 修改前
if (response.data.success) {
  const token = 'dorm-manager-token-' + Date.now()
  const userData = {
    id: form.value.id,
    name: response.data.data?.name || form.value.id,
    role: 'dormitory_manager',
    dormitoryId: response.data.data?.dormitoryId
  }
  // ...
} else {
  ElMessage.error(response.data.message || '登录失败')
}

// 修改后
if (response.data === "登录成功") {
  const token = 'dorm-manager-token-' + Date.now()
  const userData = {
    id: form.value.id,
    name: form.value.id,  // 使用工号作为姓名
    role: 'dormitory_manager'
  }
  // ...
} else {
  ElMessage.error('工号或密码错误')
}
```

### 3. 统一错误处理

添加详细的错误日志，与管理员登录保持一致：

```javascript
console.error('错误详情:', {
  message: error.message,
  response: error.response,
  status: error.response?.status,
  data: error.response?.data
})
```

## 后端要求

为了确保宿管登录与管理员登录完全一致，后端需要：

### 1. 接收格式
```json
{
  "name": "DM001",
  "password": "123456"
}
```

### 2. 响应格式
```json
"登录成功"
```
或
```json
"工号或密码错误"
```

### 3. 验证逻辑
- 支持明文密码验证（与管理员登录一致）
- 不要求MD5加密
- 返回简单的字符串响应

## 测试步骤

### 1. 前端测试
- 访问宿管登录页面：`/dorm-manager/login`
- 输入工号：DM001，密码：123456
- 检查浏览器控制台的请求和响应日志

### 2. 后端验证
确保后端宿管登录接口：
- 接收 `name` 和 `password` 字段
- 返回字符串响应："登录成功" 或 "工号或密码错误"
- 使用与管理员登录相同的验证逻辑

### 3. 功能验证
- 登录成功后跳转到宿管工作台
- 显示正确的用户信息
- 权限控制正常工作

## 技术细节

### 1. 请求格式统一
```javascript
// 管理员登录
{
  name: "admin",
  password: "admin"
}

// 宿管登录（修复后）
{
  name: "DM001",
  password: "123456"
}
```

### 2. 响应格式统一
```javascript
// 成功响应
"登录成功"

// 失败响应
"工号或密码错误"
```

### 3. 用户数据结构
```javascript
// 管理员用户数据
{
  username: "admin",
  id: "admin",
  role: "admin"
}

// 宿管用户数据（修复后）
{
  id: "DM001",
  name: "DM001",
  role: "dormitory_manager"
}
```

## 注意事项

1. **向后兼容**: 确保现有功能不受影响
2. **数据一致性**: 前后端数据格式保持一致
3. **错误处理**: 统一的错误提示和日志记录
4. **用户体验**: 保持登录流程的一致性

## 验证清单

- [ ] 宿管登录请求格式与管理员登录一致
- [ ] 宿管登录响应处理与管理员登录一致
- [ ] 错误处理和日志记录统一
- [ ] 登录成功后正确跳转和显示用户信息
- [ ] 权限控制正常工作

修复完成后，宿管登录将与管理员登录使用完全相同的验证逻辑和响应处理方式。 