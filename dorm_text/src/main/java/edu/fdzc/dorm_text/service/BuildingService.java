package edu.fdzc.dorm_text.service;

import edu.fdzc.dorm_text.entity.Building;

import java.util.List;

public interface BuildingService {
    List<Building> getAllBuildings();
    Building getBuildingById(String id);
    int addBuilding(Building building);
    int updateBuilding(Building building);
    int deleteBuilding(String id);
    List<Building> getBuildingsByManagerId(String managerId);
}