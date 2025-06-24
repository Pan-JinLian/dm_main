package edu.fdzc.dorm_text.controller;

import edu.fdzc.dorm_text.entity.Building;
import edu.fdzc.dorm_text.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dorm/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/{id}")
    public Building getBuildingById(@PathVariable String id) {
        return buildingService.getBuildingById(id);
    }

    @PostMapping
    public int addBuilding(@RequestBody Building building) {
        return buildingService.addBuilding(building);
    }

    @PutMapping
    public int updateBuilding(@RequestBody Building building) {
        return buildingService.updateBuilding(building);
    }

    @DeleteMapping("/{id}")
    public int deleteBuilding(@PathVariable String id) {
        return buildingService.deleteBuilding(id);
    }

    @GetMapping("/manager/{managerId}")
    public List<Building> getBuildingsByManagerId(@PathVariable String managerId) {
        return buildingService.getBuildingsByManagerId(managerId);
    }
}