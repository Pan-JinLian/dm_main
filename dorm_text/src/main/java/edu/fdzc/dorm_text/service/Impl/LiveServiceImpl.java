package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.Live;
import edu.fdzc.dorm_text.mapper.LiveMapper;
import edu.fdzc.dorm_text.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveServiceImpl implements LiveService {
    @Autowired
    private LiveMapper liveMapper;

    @Override
    public int addLive(Live live) {
        return liveMapper.addLive(live);
    }

    @Override
    public int deleteLiveByStudentId(String studentId) {
        return liveMapper.deleteLiveByStudentId(studentId);
    }

    @Override
    public int updateLive(Live live) {
        return liveMapper.updateLive(live);
    }

    @Override
    public Live getLiveByStudentId(String studentId) {
        return liveMapper.getLiveByStudentId(studentId);
    }

    @Override
    public List<Live> getAllLives() {
        return liveMapper.getAllLives();
    }
}