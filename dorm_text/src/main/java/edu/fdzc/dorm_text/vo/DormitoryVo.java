package edu.fdzc.dorm_text.vo;

public class DormitoryVo {
    private String id; // 宿舍号
    private String buildingId; // 楼号
    private String buildingName; // 楼栋名称(新增)
    private String dormitorymanagerId; // 宿管号
    private String dormitoryManagerName; // 宿管姓名(新增)
    private String floor; // 楼层
    private Integer maxNumber; // 最大可住人数
    private Integer livedNumber; // 已住人数

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDormitorymanagerId() {
        return dormitorymanagerId;
    }

    public void setDormitorymanagerId(String dormitorymanagerId) {
        this.dormitorymanagerId = dormitorymanagerId;
    }

    public String getDormitoryManagerName() {
        return dormitoryManagerName;
    }

    public void setDormitoryManagerName(String dormitoryManagerName) {
        this.dormitoryManagerName = dormitoryManagerName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Integer getLivedNumber() {
        return livedNumber;
    }

    public void setLivedNumber(Integer livedNumber) {
        this.livedNumber = livedNumber;
    }
}