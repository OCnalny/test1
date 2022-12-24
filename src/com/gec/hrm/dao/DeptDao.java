package com.gec.hrm.dao;

import com.gec.hrm.pojo.Dept;
import com.gec.hrm.pojo.Job;
import com.gec.hrm.pojo.User;

import java.util.List;

//定义对dept_inf表的操作方法
public interface DeptDao {
    public List<Dept> deptlist(String name, String status, int page, int limit);

    public int updDept(Dept dept);

    public int count();

    int addDept(Dept dept);

    boolean checkDeptName(String name);

    int delDept(int id);

    public List<Dept> getAllDept();
}
