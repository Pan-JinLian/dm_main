package com.example.susheguanli.entity;

public class Admin {
    private String name;     // 管理员账号
    private String password; // 管理员密码

    // 构造方法
    public Admin() {}

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getter和Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}