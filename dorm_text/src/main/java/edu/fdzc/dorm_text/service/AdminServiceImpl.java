package com.example.susheguanli.service.impl;

import com.example.susheguanli.entity.Admin;
import com.example.susheguanli.mapper.AdminMapper;
import com.example.susheguanli.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String password) {
        Admin admin = adminMapper.selectByName(name);
        // 直接比对明文密码
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    @Override
    public boolean register(Admin admin) {
        if (adminMapper.selectByName(admin.getName()) != null) {
            return false;
        }
        // 直接存储明文密码（不再加密）
        return adminMapper.insert(admin) > 0;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        // 直接更新明文密码
        return adminMapper.update(admin) > 0;
    }

    @Override
    public boolean deleteAdmin(String name) {
        return adminMapper.delete(name) > 0;
    }
}