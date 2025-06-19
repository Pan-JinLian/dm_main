package com.example.wym.entity;

import java.io.Serializable;
import java.util.Date;

public class Live implements Serializable {
    private String studentId;
    private String dormitoryId;
    private String bed_id;
    private Date liveInDate;
    private Date liveOutDate;
    private Integer status;

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDormitoryId() {
        return dormitoryId;
    }

    public void setDormitoryId(String dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public String getBed_id() {
        return bed_id;
    }

    public void setBed_id(String bed_id) {
        this.bed_id = bed_id;
    }

    public Date getLiveInDate() {
        return liveInDate;
    }

    public void setLiveInDate(Date liveInDate) {
        this.liveInDate = liveInDate;
    }

    public Date getLiveOutDate() {
        return liveOutDate;
    }

    public void setLiveOutDate(Date liveOutDate) {
        this.liveOutDate = liveOutDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}