package com.example.susheguanli.service;

import com.example.susheguanli.entity.Building;
import java.util.List;

public interface BuildingService {
    Building getBuildingById(String id);
    List<Building> getAllBuildings();
    boolean addBuilding(Building building);
    boolean updateBuilding(Building building);
    boolean deleteBuilding(String id);
    List<Building> getBuildingsByType(String type);
}