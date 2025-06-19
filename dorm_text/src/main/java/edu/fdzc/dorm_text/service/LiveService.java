package com.example.wym.service;

import com.example.wym.entity.Live;

import java.util.List;

public interface LiveService {
    // 添加入住信息
    int addLive(Live live);

    // 根据学生学号删除入住信息
    int deleteLiveByStudentId(String studentId);

    // 更新入住信息
    int updateLive(Live live);

    // 根据学生学号查询入住信息
    Live getLiveByStudentId(String studentId);

    // 查询所有入住信息
    List<Live> getAllLives();
}