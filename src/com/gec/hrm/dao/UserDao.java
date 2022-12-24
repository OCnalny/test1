package com.gec.hrm.dao;

import com.gec.hrm.pojo.User;

import java.util.List;

/*
* 定义队user_inf表的操作方法
* */
public interface UserDao {
//    登录方法
    public User login(String username,String password);
//  查询方法
    public List<User> userlist(String loginname, String status, int page, int limit);
//    根据登录名查询是否重复
    public boolean checkName(String loginname);
//    添加用户方法
    public int addUser(User user);
//    修改用户方法
    public int updUser(User user);
//    查询总记录条数的方法
    public int count();
//    删除用户方法
        public int delUser(int id);
}
