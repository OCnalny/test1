package com.gec.hrm.dao;

import com.gec.hrm.pojo.Employee;

import java.util.List;

//定义对employee表的操作
public interface EmployeeDao {
    List<Employee> getEmployeeList(Employee employee);

    boolean selectCardId(String id);

    int addEmployee(Employee employee);

    int countEmployee();

    int updEmployee(Employee employee);
}
