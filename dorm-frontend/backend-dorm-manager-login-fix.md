# 后端宿管登录修复指南

## 问题确认

前端已经修改为与管理员登录相同的格式，但仍然显示"账号密码错误"，说明问题在后端。

## 后端需要修改的文件

### 1. DormitoryManagerController.java

```java
@RestController
@RequestMapping("/dorm/dormitory-manager")
public class DormitoryManagerController {
    
    @Autowired
    private DormitoryManagerService dormitoryManagerService;
    
    // 宿管登录接口 - 与管理员登录格式一致
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginData) {
        String name = loginData.get("name");  // 注意：前端发送的是name字段
        String password = loginData.get("password");
        
        System.out.println("宿管登录请求: name=" + name + ", password=" + password);
        
        // 调用服务层验证
        DormitoryManagerEntity manager = dormitoryManagerService.login(name, password);
        
        if (manager != null) {
            System.out.println("宿管登录成功: " + name);
            return "登录成功";
        } else {
            System.out.println("宿管登录失败: " + name);
            return "工号或密码错误";
        }
    }
    
    // 测试连接接口
    @GetMapping("/test")
    public String test() {
        return "连接成功";
    }
    
    // 其他CRUD接口...
}
```

### 2. DormitoryManagerService.java

```java
public interface DormitoryManagerService {
    // 修改方法签名，使用name而不是id
    DormitoryManagerEntity login(String name, String password);
    
    // 其他方法...
}
```

### 3. DormitoryManagerServiceImpl.java

```java
@Service
public class DormitoryManagerServiceImpl implements DormitoryManagerService {
    
    @Autowired
    private DormitoryManagerMapper dormitoryManagerMapper;
    
    @Override
    public DormitoryManagerEntity login(String name, String password) {
        System.out.println("验证宿管登录: name=" + name + ", password=" + password);
        
        // 根据name查询宿管
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
    
    // 其他方法...
}
```

### 4. DormitoryManagerMapper.java

```java
@Mapper
public interface DormitoryManagerMapper {
    
    // 根据ID查询宿管
    @Select("SELECT * FROM DormitoryManager WHERE id = #{id}")
    DormitoryManagerEntity selectById(@Param("id") String id);
    
    // 其他方法...
}
```

## 数据库验证

### 1. 检查数据库表结构

```sql
-- 查看宿管表结构
DESCRIBE DormitoryManager;

-- 查看宿管数据
SELECT * FROM DormitoryManager;
```

### 2. 确保测试数据正确

```sql
-- 插入或更新测试数据
INSERT INTO DormitoryManager (id, name, password, gender, phone) 
VALUES ('DM001', '张三', '123456', '男', '13800138001')
ON DUPLICATE KEY UPDATE 
    name = '张三', 
    password = '123456', 
    gender = '男', 
    phone = '13800138001';

INSERT INTO DormitoryManager (id, name, password, gender, phone) 
VALUES ('DM002', '李四', '123456', '女', '13800138002')
ON DUPLICATE KEY UPDATE 
    name = '李四', 
    password = '123456', 
    gender = '女', 
    phone = '13800138002';

INSERT INTO DormitoryManager (id, name, password, gender, phone) 
VALUES ('DM003', '王五', '123456', '男', '13800138003')
ON DUPLICATE KEY UPDATE 
    name = '王五', 
    password = '123456', 
    gender = '男', 
    phone = '13800138003';
```

## 调试步骤

### 1. 添加详细日志

在Controller和Service中添加System.out.println来跟踪请求：

```java
// Controller中
System.out.println("收到宿管登录请求: " + loginData);

// Service中
System.out.println("开始验证宿管: " + name);
System.out.println("数据库查询结果: " + manager);
```

### 2. 检查请求数据

确保前端发送的数据格式正确：

```javascript
// 前端发送的数据应该是：
{
  "name": "DM001",
  "password": "123456"
}
```

### 3. 验证数据库连接

确保MyBatis能正确查询到数据：

```java
// 在Service中添加测试查询
List<DormitoryManagerEntity> allManagers = dormitoryManagerMapper.selectAll();
System.out.println("所有宿管: " + allManagers);
```

## 常见问题排查

### 1. 字段名不匹配
- 确保数据库字段名与实体类属性名一致
- 检查MyBatis映射是否正确

### 2. 数据类型问题
- 确保密码字段是VARCHAR类型
- 检查是否有空格或特殊字符

### 3. 编码问题
- 确保数据库使用UTF-8编码
- 检查请求和响应的字符编码

### 4. 表名大小写
- MySQL在Windows上可能不区分大小写
- 确保表名和字段名正确

## 测试验证

### 1. 直接测试数据库查询

```sql
-- 直接查询测试
SELECT * FROM DormitoryManager WHERE id = 'DM001';
```

### 2. 测试后端接口

使用Postman或curl测试：

```bash
curl -X POST http://localhost:8080/dorm/dormitory-manager/login \
  -H "Content-Type: application/json" \
  -d '{"name":"DM001","password":"123456"}'
```

### 3. 检查后端日志

启动后端后，查看控制台输出，确认：
- 请求是否正确接收
- 数据库查询是否成功
- 密码比较是否正确

## 完整修复流程

1. **修改Controller**：使用name字段接收请求
2. **修改Service**：实现明文密码验证
3. **检查Mapper**：确保查询方法正确
4. **验证数据**：确保数据库中有正确的测试数据
5. **添加日志**：便于调试和排查问题
6. **重启服务**：应用所有修改
7. **测试登录**：使用DM001/123456测试

按照这个指南修改后端代码，宿管登录应该就能正常工作了！ 