package edu.fdzc.dorm_text.service.Impl;

import edu.fdzc.dorm_text.entity.Admin;
import edu.fdzc.dorm_text.mapper.AdminMapper;
import edu.fdzc.dorm_text.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String password) {
        Admin admin = adminMapper.selectByName(name);
        // 直接比较明文密码
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    @Override
    public boolean register(Admin admin) {
        if (adminMapper.existsByName(admin.getName())) {
            return false;
        }
        // 直接存储明文密码
        return adminMapper.insert(admin) > 0;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        Admin existingAdmin = adminMapper.selectByName(admin.getName());
        if (existingAdmin == null) {
            return false;
        }
        // 直接更新明文密码
        return adminMapper.update(admin) > 0;
    }

    @Override
    public boolean deleteAdmin(String name) {
        return adminMapper.deleteByName(name) > 0;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.selectAll();
    }

    @Override
    public Admin getAdminByName(String name) {
        return adminMapper.selectByName(name);
    }
}