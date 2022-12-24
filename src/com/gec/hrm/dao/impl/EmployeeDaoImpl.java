package com.gec.hrm.dao.impl;

import com.gec.hrm.dao.EmployeeDao;
import com.gec.hrm.pojo.Employee;
import com.gec.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDaoImpl extends JDBCUtils<Employee> implements EmployeeDao {
    @Override
    public List<Employee> getEmployeeList(Employee employee) {
        var sql = "select e.*,d.`NAME` deptName,j.`NAME` jobName from employee_inf e,dept_inf d,job_inf j where d.ID=e.dept_id and e.job_id=j.ID";
        if (employee.getName() != null && !employee.getName().isEmpty()) {
            sql += " AND e.name like '%" + employee.getName() + "%'";
        }
        if (employee.getSex() != -1) {
            sql += " AND sex=" + employee.getSex();
        }
        if (employee.getCardId() != null && !employee.getCardId().isEmpty()) {
            sql += " AND card_id=" + employee.getCardId();
        }
        if (employee.getJob_id() != -1) {
            sql += " AND job_id=" + employee.getJob_id();
        }
        if (employee.getDept_id() != -1) {
            sql += " AND dept_id=" + employee.getDept_id();
        }
        if (employee.getPhone() != null && !employee.getPhone().isEmpty()) {
            sql += " AND phone=" + employee.getPhone();
        }
        return query(sql);
    }

    @Override
    public boolean selectCardId(String id) {
        var sql = "select e.*,d.`NAME` deptName,j.`NAME` jobName from employee_inf e,dept_inf d,job_inf j where d.ID=e.dept_id and e.job_id=j.ID" + " and card_id=?";
        var list = query(sql, id);
        return list.size() > 0;
    }

    @Override
    public int addEmployee(Employee employee) {
        return update("insert into employee_inf values (nUll, ?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ? , ?)",
                employee.getName(), employee.getCardId(), employee.getAddress(), employee.getPostCode(), employee.getTel(), employee.getPhone(), employee.getQqNum(), employee.getEmail(), employee.getSex(), employee.getParty(), employee.getBirthday(), employee.getRace(), employee.getEducation(), employee.getSpeciality(), employee.getHobby(), employee.getRemark(), employee.getCreateDate(), employee.getState(), employee.getDept_id(), employee.getJob_id());
    }

    @Override
    public int countEmployee() {
        var users = query("select * from employee_inf");
        return users.size();
    }

    @Override
    public int updEmployee(Employee employee) {
        return update("update employee_inf set NAME=?,CARD_ID=?,ADDRESS=?,TEL=?,POST_CODE=?,PHONE=?,QQ_NUM=?,EMAIL=?,SEX=?,PARTY=?,BIRTHDAY=?,RACE=?,EDUCATION=?,SPECIALITY=?,HOBBY=?,REMARK=?,CREATE_DATE=?,state=?,dept_id=?,job_id=? where ID=?",
                employee.getName(),employee.getCardId(),employee.getAddress(),employee.getTel(),
                employee.getPostCode(),employee.getPhone(),employee.getQqNum(),employee.getEmail(),
                employee.getSex(),employee.getParty(),employee.getBirthday(),employee.getRace(),
                employee.getEducation(),employee.getSpeciality(),employee.getHobby(),employee.getRemark(),
                employee.getCreateDate(), employee.getState(), employee.getDept_id(), employee.getJob_id(),employee.getId());
    }

    @Override
    public Employee getBean(ResultSet rs) {
        Employee employee = new Employee();
        try {
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setPhone(rs.getString("phone"));
            employee.setCardId(rs.getString("card_id"));
            employee.setAddress(rs.getString("address"));
            employee.setEmail(rs.getString("email"));
            employee.setSex(rs.getInt("sex"));
            employee.setEducation(rs.getString("education"));
            employee.setCreateDate(rs.getDate("CREATE_DATE"));
            employee.setDept_id(rs.getInt("dept_id"));
            employee.setJob_id(rs.getInt("job_id"));
            employee.setDept_id(rs.getInt("dept_id"));
            employee.setDeptName(rs.getString("deptName"));
            employee.setJobName(rs.getString("jobName"));
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
