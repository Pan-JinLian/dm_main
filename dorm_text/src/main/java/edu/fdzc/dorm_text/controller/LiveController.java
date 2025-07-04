package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.Live;
import edu.fdzc.dorm_text.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dorm/live")
public class LiveController {
    @Autowired
    private LiveService liveService;

    // 添加入住信息
    @PostMapping("/add")
    public int addLive(@RequestBody Live live) {
        return liveService.addLive(live);
    }

    // 根据学生学号删除入住信息
    @DeleteMapping("/delete/{studentId}")
    public int deleteLiveByStudentId(@PathVariable String studentId) {
        return liveService.deleteLiveByStudentId(studentId);
    }

    // 更新入住信息
    @PutMapping("/update")
    public int updateLive(@RequestBody Live live) {
        return liveService.updateLive(live);
    }

    // 根据学生学号查询入住信息
    @GetMapping("/get/{studentId}")
    public Live getLiveByStudentId(@PathVariable String studentId) {
        return liveService.getLiveByStudentId(studentId);
    }

    // 查询所有入住信息
    @GetMapping("/getAll")
    public List<Live> getAllLives() {
        return liveService.getAllLives();
    }

    // 查询所有入住信息（包含学生和宿舍信息）
    @GetMapping("/getAllWithDetails")
    public List<Map<String, Object>> getAllLivesWithDetails() {
        return liveService.getAllLivesWithDetails();
    }
}