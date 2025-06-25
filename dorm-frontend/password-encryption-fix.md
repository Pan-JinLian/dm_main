# 宿管登录密码加密问题修复

## 问题描述

用户报告：输入正确的宿管账号密码显示"错误的账号或密码"

## 问题原因

数据库中的密码是明文存储（如：123456），但后端的登录验证逻辑期望的是MD5加密后的密码（如：e10adc3949ba59abbe56e057f20f883e）。

## 解决方案

### 方案1：修改后端登录逻辑（推荐）

修改后端的 `DormitoryManagerServiceImpl.java` 中的 `login` 方法，支持明文密码和MD5加密密码两种验证方式。

```java
@Override
public DormitoryManagerEntity login(String id, String password) {
    DormitoryManagerEntity manager = dormitoryManagerMapper.selectById(id);
    if (manager != null) {
        // 先尝试明文密码匹配
        if (manager.getPassword().equals(password)) {
            return manager;
        }
        // 再尝试MD5加密密码匹配
        if (manager.getPassword().equals(encryptPassword(password))) {
            return manager;
        }
    }
    return null;
}
```

### 方案2：更新数据库密码为MD5加密

执行以下SQL语句更新数据库中的密码：

```sql
-- 更新宿管密码为MD5加密
UPDATE DormitoryManager SET password = 'e10adc3949ba59abbe56e057f20f883e' WHERE id = 'DM001';
UPDATE DormitoryManager SET password = 'e10adc3949ba59abbe56e057f20f883e' WHERE id = 'DM002';
UPDATE DormitoryManager SET password = 'e10adc3949ba59abbe56e057f20f883e' WHERE id = 'DM003';
```

## 测试账号

### 当前数据库中的明文密码账号
- **工号**: DM001, **密码**: 123456, **姓名**: 张三
- **工号**: DM002, **密码**: 123456, **姓名**: 李四
- **工号**: DM003, **密码**: 123456, **姓名**: 王五

### MD5加密后的密码
- **密码**: 123456 → **MD5**: e10adc3949ba59abbe56e057f20f883e

## 验证步骤

### 1. 检查数据库密码格式
```sql
SELECT id, name, password FROM DormitoryManager;
```

### 2. 测试登录
- 使用工号：DM001，密码：123456
- 检查后端日志，看密码验证过程

### 3. 验证修复结果
- 登录成功应该跳转到宿管工作台
- 不再显示"工号或密码错误"

## 技术细节

### MD5加密方法
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

### 密码验证逻辑
```java
// 双重验证：明文 + MD5加密
if (manager.getPassword().equals(password) || 
    manager.getPassword().equals(encryptPassword(password))) {
    return manager;
}
```

## 建议

1. **短期修复**: 使用方案1，修改后端逻辑支持双重验证
2. **长期规划**: 逐步将数据库中的密码更新为MD5加密
3. **安全考虑**: 考虑使用更安全的加密算法（如bcrypt）
4. **数据迁移**: 在添加新用户时统一使用MD5加密

## 注意事项

1. **向后兼容**: 方案1确保现有明文密码仍可正常使用
2. **数据一致性**: 方案2需要确保所有密码都正确更新
3. **测试验证**: 修复后需要全面测试所有账号的登录功能
4. **日志记录**: 建议添加密码验证过程的日志记录

选择方案1可以立即解决问题，同时保持系统的稳定性。 