package com.example.susheguanli.service.impl;

import com.example.susheguanli.entity.Building;
import com.example.susheguanli.mapper.BuildingMapper;
import com.example.susheguanli.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public Building getBuildingById(String id) {
        return buildingMapper.selectById(id);
    }

    @Override
    public List<Building> getAllBuildings() {
        return buildingMapper.selectAll();
    }

    @Override
    public boolean addBuilding(Building building) {
        if (buildingMapper.selectById(building.getId()) != null) {
            return false;
        }
        return buildingMapper.insert(building) > 0;
    }

    @Override
    public boolean updateBuilding(Building building) {
        return buildingMapper.update(building) > 0;
    }

    @Override
    public boolean deleteBuilding(String id) {
        return buildingMapper.delete(id) > 0;
    }

    @Override
    public List<Building> getBuildingsByType(String type) {
        return buildingMapper.selectByType(type);
    }
}