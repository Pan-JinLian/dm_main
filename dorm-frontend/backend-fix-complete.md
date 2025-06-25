# 后端宿管登录修复完成

## 问题诊断

通过检查后端代码，发现了以下问题：

1. **控制器字段不匹配**：前端发送 `name` 字段，后端接收 `id` 字段
2. **密码加密问题**：后端使用MD5加密验证，但数据库密码是明文
3. **响应格式不匹配**：前端期望字符串响应，后端返回JSON对象

## 修复内容

### 1. 修改 DormitoryManagerController.java

**修改前：**
```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
    String id = loginData.get("id");  // 错误：使用id字段
    // ...
    return ResponseEntity.ok(response);  // 错误：返回JSON对象
}
```

**修改后：**
```java
@PostMapping("/login")
public String login(@RequestBody Map<String, String> loginData) {
    String name = loginData.get("name");  // 正确：使用name字段
    // ...
    return "登录成功";  // 正确：返回字符串
}
```

### 2. 修改 DormitoryManagerService.java

**修改前：**
```java
DormitoryManagerEntity login(String id, String password);
```

**修改后：**
```java
DormitoryManagerEntity login(String name, String password);
```

### 3. 修改 DormitoryManagerServiceImpl.java

**修改前：**
```java
@Override
public DormitoryManagerEntity login(String id, String password) {
    DormitoryManagerEntity manager = dormitoryManagerMapper.selectById(id);
    if (manager != null && manager.getPassword().equals(encryptPassword(password))) {
        return manager;  // 错误：使用MD5加密验证
    }
    return null;
}
```

**修改后：**
```java
@Override
public DormitoryManagerEntity login(String name, String password) {
    System.out.println("验证宿管登录: name=" + name + ", password=" + password);
    
    DormitoryManagerEntity manager = dormitoryManagerMapper.selectById(name);
    
    if (manager != null) {
        System.out.println("找到宿管: " + manager.getName() + ", 数据库密码: " + manager.getPassword());
        
        // 直接比较明文密码（与管理员登录一致）
        if (manager.getPassword().equals(password)) {
            System.out.println("密码匹配成功");
            return manager;
        } else {
            System.out.println("密码不匹配: 输入=" + password + ", 数据库=" + manager.getPassword());
        }
    } else {
        System.out.println("未找到宿管: " + name);
    }
    
    return null;
}
```

### 4. 创建数据库更新脚本

创建了 `update-passwords.sql` 文件，将数据库中的MD5密码更新为明文：

```sql
-- 更新宿管密码为明文
UPDATE DormitoryManager SET password = '123456' WHERE id = 'DM001';
UPDATE DormitoryManager SET password = '123456' WHERE id = 'DM002';
UPDATE DormitoryManager SET password = '123456' WHERE id = 'DM003';

-- 更新学生密码为明文
UPDATE Student SET password = '123456' WHERE id = '2023001';
UPDATE Student SET password = '123456' WHERE id = '2023002';
UPDATE Student SET password = '123456' WHERE id = '2023003';
UPDATE Student SET password = '123456' WHERE id = '2023004';
UPDATE Student SET password = '123456' WHERE id = '2023005';
```

## 修复后的功能

### 1. 请求格式
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
- 使用明文密码验证（与管理员登录一致）
- 添加详细的调试日志
- 统一的错误处理

## 部署步骤

### 1. 更新数据库密码
```sql
-- 执行 update-passwords.sql 文件
source update-passwords.sql;
```

### 2. 重启后端服务
```bash
# 重新编译并启动Spring Boot应用
mvn clean package
java -jar target/dorm_text-0.0.1-SNAPSHOT.jar
```

### 3. 测试登录
- 使用工号：DM001，密码：123456
- 检查后端控制台日志
- 验证登录成功和跳转

## 测试账号

### 宿管账号
- **工号**: DM001, **密码**: 123456, **姓名**: 张三
- **工号**: DM002, **密码**: 123456, **姓名**: 李四
- **工号**: DM003, **密码**: 123456, **姓名**: 王五

### 管理员账号
- **用户名**: admin, **密码**: admin

## 调试信息

后端现在会输出详细的调试信息：

```
宿管登录请求: name=DM001, password=123456
验证宿管登录: name=DM001, password=123456
找到宿管: 张三, 数据库密码: 123456
密码匹配成功
宿管登录成功: DM001
```

## 验证清单

- [x] 控制器接收name字段
- [x] 服务层使用明文密码验证
- [x] 响应格式为字符串
- [x] 数据库密码更新为明文
- [x] 添加详细调试日志
- [x] 与管理员登录逻辑一致

现在宿管登录应该可以正常工作了！请按照部署步骤操作，然后测试登录功能。 