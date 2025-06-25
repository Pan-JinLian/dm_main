# 宿管登录问题修复总结

## 问题描述

宿管登录时出现405错误（HTTP方法不被允许），错误信息：
```
Failed to load resource: the server responded with a status of 405 ()
宿管登录错误: AxiosError
```

## 问题原因

1. **缺少登录接口**: 后端宿管控制器没有登录接口
2. **缺少服务方法**: 服务层没有登录验证方法
3. **密码加密问题**: 密码需要MD5加密处理
4. **测试数据缺失**: 数据库中没有宿管测试数据

## 修复内容

### 1. 后端控制器修复

#### DormitoryManagerController.java
- 添加了 `@PostMapping("/login")` 登录接口
- 添加了 `@GetMapping("/test")` 测试连接接口
- 统一了所有接口的响应格式，返回 `ResponseEntity<?>`
- 添加了详细的错误处理和响应信息

```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
    // 登录逻辑
}
```

### 2. 服务层修复

#### DormitoryManagerService.java
- 添加了 `login(String id, String password)` 方法声明

#### DormitoryManagerServiceImpl.java
- 实现了登录验证方法
- 添加了MD5密码加密功能
- 在添加宿管时自动加密密码

```java
@Override
public DormitoryManagerEntity login(String id, String password) {
    DormitoryManagerEntity manager = dormitoryManagerMapper.selectById(id);
    if (manager != null && manager.getPassword().equals(encryptPassword(password))) {
        return manager;
    }
    return null;
}
```

### 3. 密码加密处理

- 添加了 `encryptPassword()` 方法，使用MD5加密
- 默认密码 `123456` 的MD5值为：`e10adc3949ba59abbe56e057f20f883e`
- 在登录时对输入密码进行MD5加密后比较
- 在添加宿管时自动加密密码

### 4. 测试数据

#### init-data.sql
创建了完整的初始化数据，包括：
- **宿管数据**: 3个测试宿管，密码都是123456
- **楼栋数据**: A栋、B栋、C栋
- **宿舍数据**: 5个宿舍，分配给不同宿管
- **学生数据**: 5个测试学生
- **住宿记录**: 学生入住记录

## 测试账号

### 宿管登录测试账号
- **工号**: DM001, **密码**: 123456, **姓名**: 张三
- **工号**: DM002, **密码**: 123456, **姓名**: 李四  
- **工号**: DM003, **密码**: 123456, **姓名**: 王五

### 管理员登录测试账号
- **用户名**: admin, **密码**: admin

## 修复后的功能

### 1. 宿管登录
- 支持工号和密码登录
- 密码MD5加密验证
- 返回登录状态和宿管信息

### 2. 测试连接
- 提供连接测试接口
- 验证后端服务是否正常运行

### 3. 错误处理
- 统一的错误响应格式
- 详细的错误信息提示
- 友好的用户提示

## 使用步骤

### 1. 数据库初始化
```sql
-- 执行 init-data.sql 文件
source init-data.sql;
```

### 2. 后端启动
- 确保数据库连接正常
- 启动Spring Boot应用

### 3. 前端测试
- 访问宿管登录页面：`/dorm-manager/login`
- 使用测试账号登录
- 验证登录成功和跳转

## 技术细节

### 1. 密码加密
```java
private String encryptPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("MD5加密失败", e);
    }
}
```

### 2. 响应格式
```json
{
  "success": true,
  "message": "登录成功",
  "data": {
    "id": "DM001",
    "name": "张三",
    "gender": "男",
    "phone": "13800138001"
  }
}
```

### 3. 错误处理
```json
{
  "success": false,
  "message": "工号或密码错误"
}
```

## 注意事项

1. **密码安全**: 所有密码都使用MD5加密存储
2. **数据一致性**: 确保数据库表结构与实体类匹配
3. **接口规范**: 统一使用RESTful API设计
4. **错误处理**: 完善的异常处理和用户提示

## 验证方法

1. **登录测试**: 使用测试账号验证登录功能
2. **错误测试**: 使用错误密码验证错误处理
3. **连接测试**: 测试后端连接接口
4. **权限测试**: 验证登录后的权限控制

现在宿管登录功能应该可以正常工作了！你可以使用测试账号进行登录验证。 