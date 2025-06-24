package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.Building;
import edu.fdzc.dorm_text.mapper.BuildingMapper;
import edu.fdzc.dorm_text.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public List<Building> getAllBuildings() {
        return buildingMapper.selectAllBuildings();
    }

    @Override
    public Building getBuildingById(String id) {
        return buildingMapper.selectBuildingById(id);
    }

    @Override
    public int addBuilding(Building building) {
        return buildingMapper.insertBuilding(building);
    }

    @Override
    public int updateBuilding(Building building) {
        return buildingMapper.updateBuilding(building);
    }

    @Override
    public int deleteBuilding(String id) {
        return buildingMapper.deleteBuildingById(id);
    }

    @Override
    public List<Building> getBuildingsByManagerId(String managerId) {
        return buildingMapper.selectBuildingsByManagerId(managerId);
    }
}