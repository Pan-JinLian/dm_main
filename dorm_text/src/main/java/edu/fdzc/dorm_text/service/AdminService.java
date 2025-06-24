package edu.fdzc.dorm_text.service;


import edu.fdzc.dorm_text.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin login(String name, String password);
    boolean register(Admin admin);
    boolean updateAdmin(Admin admin);
    boolean deleteAdmin(String name);
    List<Admin> getAllAdmins();
    Admin getAdminByName(String name);
}