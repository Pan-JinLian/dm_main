package edu.fdzc.dorm_text.entity;

public class Building {
    private String id;                // 楼号
    private String name;              // 楼名称
    private String type;              // 楼类型（男生，女生，混合）
    private Integer floors;           // 总层数
    private Integer rooms;            // 总房间数
    private String dormitoryManagerId; // 宿管号

    // 构造方法
    public Building() {}

    public Building(String id, String name, String type, Integer floors, Integer rooms, String dormitoryManagerId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.floors = floors;
        this.rooms = rooms;
        this.dormitoryManagerId = dormitoryManagerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public String getDormitoryManagerId() {
        return dormitoryManagerId;
    }

    public void setDormitoryManagerId(String dormitoryManagerId) {
        this.dormitoryManagerId = dormitoryManagerId;
    }
}