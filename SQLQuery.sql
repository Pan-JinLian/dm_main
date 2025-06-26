-- 创建数据库
CREATE DATABASE IF NOT EXISTS dm default char set utf8 collate utf8_general_ci;

USE dm;

-- 管理员表
CREATE TABLE Admin (
    name VARCHAR(50) PRIMARY KEY COMMENT '管理员账号',
    password VARCHAR(100) NOT NULL COMMENT '管理员密码'
) COMMENT '管理员信息表';

-- 宿管表
CREATE TABLE DormitoryManager (
    id VARCHAR(20) PRIMARY KEY COMMENT '宿管号',
    name VARCHAR(50) NOT NULL COMMENT '宿管姓名',
    password VARCHAR(100) NOT NULL COMMENT '宿管密码',
    gender ENUM('男', '女') NOT NULL COMMENT '宿管性别',
    phone VARCHAR(20) NOT NULL COMMENT '宿管联系电话'
) COMMENT '宿管信息表';

-- 宿舍楼表
CREATE TABLE Building (
    id VARCHAR(10) PRIMARY KEY COMMENT '楼号',
    name VARCHAR(50) NOT NULL COMMENT '楼名称',
    type ENUM('男生', '女生', '混合') NOT NULL COMMENT '楼类型',
    floors INT NOT NULL COMMENT '总层数',
    rooms INT NOT NULL COMMENT '总房间数',
    dormitoryManagerId VARCHAR(20) COMMENT '宿管号',
    FOREIGN KEY (dormitoryManagerId) REFERENCES DormitoryManager(id) ON DELETE SET NULL
) COMMENT '宿舍楼信息表';

-- 宿舍表
CREATE TABLE Dormitory (
    id VARCHAR(20) PRIMARY KEY COMMENT '宿舍号',
    dormitorymanagerId VARCHAR(20) NOT NULL COMMENT '所属宿管号',
    buildingId VARCHAR(10) NOT NULL COMMENT '所属楼号',
    floor VARCHAR(10) NOT NULL COMMENT '所属楼层',
    maxNumber INT NOT NULL COMMENT '最大可住人数',
    livedNumber INT DEFAULT 0 COMMENT '已住人数',
    FOREIGN KEY (dormitorymanagerId) REFERENCES DormitoryManager(id) ON DELETE CASCADE,
    FOREIGN KEY (buildingId) REFERENCES Building(id) ON DELETE CASCADE
) COMMENT '宿舍信息表';

-- 学生表
CREATE TABLE Student (
    id VARCHAR(20) PRIMARY KEY COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    id_card VARCHAR(18) NOT NULL UNIQUE COMMENT '身份证号',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    gender ENUM('男', '女') NOT NULL COMMENT '性别',
    department VARCHAR(50) NOT NULL COMMENT '系',
    major VARCHAR(50) NOT NULL COMMENT '专业',
    grade INT NOT NULL COMMENT '年级',
    class INT NOT NULL COMMENT '班级',
    phone VARCHAR(20) NOT NULL COMMENT '联系电话'
) COMMENT '学生信息表';

-- 学生入住信息表
CREATE TABLE Live (
    studentId VARCHAR(20) NOT NULL COMMENT '学生学号',
    dormitoryId VARCHAR(20) NOT NULL COMMENT '宿舍号',
    bed_id VARCHAR(10) NOT NULL COMMENT '床位号',
    liveInDate DATE NOT NULL COMMENT '入住时间',
    liveOutDate DATE COMMENT '退宿时间',
    status TINYINT DEFAULT 0 COMMENT '0-在住，1-已退宿',
    PRIMARY KEY (studentId, dormitoryId),
    FOREIGN KEY (studentId) REFERENCES Student(id) ON DELETE CASCADE,
    FOREIGN KEY (dormitoryId) REFERENCES Dormitory(id) ON DELETE CASCADE
) COMMENT '学生入住信息表';

-- 插入数据（密码不加密）
INSERT INTO Admin (name, password) VALUES 
('admin1', '123456'),
('admin2', '123456');

INSERT INTO DormitoryManager (id, name, password, gender, phone) VALUES 
('DM001', '张管理员', '123456', '男', '13800138001'),
('DM002', '李管理员', '123456', '女', '13800138002');

INSERT INTO Building (id, name, type, floors, rooms, dormitoryManagerId) VALUES 
('A01', '博雅楼', '男生', 6, 120, 'DM001'),
('B02', '文馨楼', '女生', 5, 100, 'DM002');

INSERT INTO Dormitory (id, dormitorymanagerId, buildingId, floor, maxNumber, livedNumber) VALUES 
('A01-101', 'DM001', 'A01', '1', 4, 2),
('B02-201', 'DM002', 'B02', '2', 4, 1);

INSERT INTO Student (id, name, id_card, password, gender, department, major, grade, class, phone) VALUES 
('2023001', '张三', '110101200001011234', '123456', '男', '计算机系', '软件工程', 2023, 1, '13800138003'),
('2023002', '李四', '110101200002021235', '123456', '女', '外语系', '英语', 2023, 2, '13800138004');

INSERT INTO Live (studentId, dormitoryId, bed_id, liveInDate, liveOutDate, status) VALUES 
('2023001', 'A01-101', 'A01-101-1', '2023-09-01', NULL, 0),
('2023002', 'B02-201', 'B02-201-1', '2023-09-01', NULL, 0);