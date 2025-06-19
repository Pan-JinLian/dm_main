package com.example.wym.service.impl;

import com.example.wym.dao.LiveDao;
import com.example.wym.entity.Live;
import com.example.wym.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveServiceImpl implements LiveService {
    @Autowired
    private LiveDao liveDao;

    @Override
    public int addLive(Live live) {
        return liveDao.addLive(live);
    }

    @Override
    public int deleteLiveByStudentId(String studentId) {
        return liveDao.deleteLiveByStudentId(studentId);
    }

    @Override
    public int updateLive(Live live) {
        return liveDao.updateLive(live);
    }

    @Override
    public Live getLiveByStudentId(String studentId) {
        return liveDao.getLiveByStudentId(studentId);
    }

    @Override
    public List<Live> getAllLives() {
        return liveDao.getAllLives();
    }
}