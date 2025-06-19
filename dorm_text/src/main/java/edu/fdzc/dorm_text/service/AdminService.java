package com.example.susheguanli.service;

import com.example.susheguanli.entity.Admin;

public interface AdminService {
    Admin login(String name, String password);
    boolean register(Admin admin);
    boolean updateAdmin(Admin admin);
    boolean deleteAdmin(String name);
}