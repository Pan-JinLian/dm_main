-- �������ݿ�
CREATE DATABASE IF NOT EXISTS dm default char set utf8 collate utf8_general_ci;

USE dm;

-- ����Ա��
CREATE TABLE Admin (
    name VARCHAR(50) PRIMARY KEY COMMENT '����Ա�˺�',
    password VARCHAR(100) NOT NULL COMMENT '����Ա����'
) COMMENT '����Ա��Ϣ��';

-- �޹ܱ�
CREATE TABLE DormitoryManager (
    id VARCHAR(20) PRIMARY KEY COMMENT '�޹ܺ�',
    name VARCHAR(50) NOT NULL COMMENT '�޹�����',
    password VARCHAR(100) NOT NULL COMMENT '�޹�����',
    gender ENUM('��', 'Ů') NOT NULL COMMENT '�޹��Ա�',
    phone VARCHAR(20) NOT NULL COMMENT '�޹���ϵ�绰'
) COMMENT '�޹���Ϣ��';

-- ����¥��
CREATE TABLE Building (
    id VARCHAR(10) PRIMARY KEY COMMENT '¥��',
    name VARCHAR(50) NOT NULL COMMENT '¥����',
    type ENUM('����', 'Ů��', '���') NOT NULL COMMENT '¥����',
    floors INT NOT NULL COMMENT '�ܲ���',
    rooms INT NOT NULL COMMENT '�ܷ�����',
    dormitoryManagerId VARCHAR(20) COMMENT '�޹ܺ�',
    FOREIGN KEY (dormitoryManagerId) REFERENCES DormitoryManager(id) ON DELETE SET NULL
) COMMENT '����¥��Ϣ��';

-- �����
CREATE TABLE Dormitory (
    id VARCHAR(20) PRIMARY KEY COMMENT '�����',
    dormitorymanagerId VARCHAR(20) NOT NULL COMMENT '�����޹ܺ�',
    buildingId VARCHAR(10) NOT NULL COMMENT '����¥��',
    floor VARCHAR(10) NOT NULL COMMENT '����¥��',
    maxNumber INT NOT NULL COMMENT '����ס����',
    livedNumber INT DEFAULT 0 COMMENT '��ס����',
    FOREIGN KEY (dormitorymanagerId) REFERENCES DormitoryManager(id) ON DELETE CASCADE,
    FOREIGN KEY (buildingId) REFERENCES Building(id) ON DELETE CASCADE
) COMMENT '������Ϣ��';

-- ѧ����
CREATE TABLE Student (
    id VARCHAR(20) PRIMARY KEY COMMENT 'ѧ��',
    name VARCHAR(50) NOT NULL COMMENT '����',
    id_card VARCHAR(18) NOT NULL UNIQUE COMMENT '���֤��',
    password VARCHAR(100) NOT NULL COMMENT '����',
    gender ENUM('��', 'Ů') NOT NULL COMMENT '�Ա�',
    department VARCHAR(50) NOT NULL COMMENT 'ϵ',
    major VARCHAR(50) NOT NULL COMMENT 'רҵ',
    grade INT NOT NULL COMMENT '�꼶',
    class INT NOT NULL COMMENT '�༶',
    phone VARCHAR(20) NOT NULL COMMENT '��ϵ�绰'
) COMMENT 'ѧ����Ϣ��';

-- ѧ����ס��Ϣ��
CREATE TABLE Live (
    studentId VARCHAR(20) NOT NULL COMMENT 'ѧ��ѧ��',
    dormitoryId VARCHAR(20) NOT NULL COMMENT '�����',
    bed_id VARCHAR(10) NOT NULL COMMENT '��λ��',
    liveInDate DATE NOT NULL COMMENT '��סʱ��',
    liveOutDate DATE COMMENT '����ʱ��',
    status TINYINT DEFAULT 0 COMMENT '0-��ס��1-������',
    PRIMARY KEY (studentId, dormitoryId),
    FOREIGN KEY (studentId) REFERENCES Student(id) ON DELETE CASCADE,
    FOREIGN KEY (dormitoryId) REFERENCES Dormitory(id) ON DELETE CASCADE
) COMMENT 'ѧ����ס��Ϣ��';

-- �������ݣ����벻���ܣ�
INSERT INTO Admin (name, password) VALUES 
('admin1', '123456'),
('admin2', '123456');

INSERT INTO DormitoryManager (id, name, password, gender, phone) VALUES 
('DM001', '�Ź���Ա', '123456', '��', '13800138001'),
('DM002', '�����Ա', '123456', 'Ů', '13800138002');

INSERT INTO Building (id, name, type, floors, rooms, dormitoryManagerId) VALUES 
('A01', '����¥', '����', 6, 120, 'DM001'),
('B02', '��ܰ¥', 'Ů��', 5, 100, 'DM002');

INSERT INTO Dormitory (id, dormitorymanagerId, buildingId, floor, maxNumber, livedNumber) VALUES 
('A01-101', 'DM001', 'A01', '1', 4, 2),
('B02-201', 'DM002', 'B02', '2', 4, 1);

INSERT INTO Student (id, name, id_card, password, gender, department, major, grade, class, phone) VALUES 
('2023001', '����', '110101200001011234', '123456', '��', '�����ϵ', '�������', 2023, 1, '13800138003'),
('2023002', '����', '110101200002021235', '123456', 'Ů', '����ϵ', 'Ӣ��', 2023, 2, '13800138004');

INSERT INTO Live (studentId, dormitoryId, bed_id, liveInDate, liveOutDate, status) VALUES 
('2023001', 'A01-101', 'A01-101-1', '2023-09-01', NULL, 0),
('2023002', 'B02-201', 'B02-201-1', '2023-09-01', NULL, 0);