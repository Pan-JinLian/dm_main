package com.example.susheguanli.controller;

import com.example.susheguanli.entity.Building;
import com.example.susheguanli.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/{id}")
    public Building getById(@PathVariable String id) {
        return buildingService.getBuildingById(id);
    }

    @GetMapping
    public List<Building> getAll() {
        return buildingService.getAllBuildings();
    }

    @PostMapping
    public String add(@RequestBody Building building) {
        return buildingService.addBuilding(building) ? "添加成功" : "楼号已存在";
    }

    @PutMapping
    public String update(@RequestBody Building building) {
        return buildingService.updateBuilding(building) ? "更新成功" : "更新失败";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return buildingService.deleteBuilding(id) ? "删除成功" : "删除失败";
    }

    @GetMapping("/type/{type}")
    public List<Building> getByType(@PathVariable String type) {
        return buildingService.getBuildingsByType(type);
    }
}