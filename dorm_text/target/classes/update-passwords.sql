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

-- 验证更新结果
SELECT id, name, password FROM DormitoryManager;
SELECT id, name, password FROM Student; 