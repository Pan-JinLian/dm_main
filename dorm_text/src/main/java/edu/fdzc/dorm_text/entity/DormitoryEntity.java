package edu.fdzc.dorm_text.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "dormitory")
public class DormitoryEntity extends AbstractBaseEntity implements Serializable {

    /**
     * 宿舍编号
     */
    @Column(name = "id", unique = true, length = 20, nullable = false)
    private String id;

    /**
     * 所属宿管号
     */
    @Column(name = "dormitorymanager_id", length = 20, nullable = false)
    private String dormitorymanagerId;

    /**
     * 所属楼宇
     */
    @Column(name = "building_id", length = 10, nullable = false)
    private String buildingId;

    /**
     * 所属楼层
     */
    @Column(length = 10, nullable = false)
    private String floor;

    /**
     * 最大可住人数
     */
    @Column(name = "max_number", nullable = false)
    private Integer maxNumber;

    /**
     * 已住人数
     */
    @Column(name = "lived_number")
    private Integer livedNumber = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDormitorymanagerId() {
        return dormitorymanagerId;
    }

    public void setDormitorymanagerId(String dormitorymanagerId) {
        this.dormitorymanagerId = dormitorymanagerId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
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