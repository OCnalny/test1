package com.gec.hrm.dao.impl;

import com.gec.hrm.dao.DeptDao;
import com.gec.hrm.pojo.Dept;
import com.gec.hrm.pojo.User;
import com.gec.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeptDaoImpl extends JDBCUtils<Dept> implements DeptDao {
    @Override
    public Dept getBean(ResultSet rs) {
        Dept dept = new Dept();
        try{
            dept.setId(rs.getInt("id"));
            dept.setState(rs.getInt("state"));
            dept.setName(rs.getString("NAME"));
            dept.setRemark(rs.getString("REMARK"));
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return dept;
    }

//    查询
    @Override
    public List<Dept> deptlist(String name, String state, int page, int limit) {
        String sql = "select * from dept_inf where 1=1";
        if(name!=null && !"".equals(name)){
            sql+=" and NAME like '%"+name +"%'";
        }
        sql+=" limit "+(page-1)*limit+","+limit+"";
        List<Dept> depts= query(sql);
        return depts;
    }

//    修改
    @Override
    public int updDept(Dept dept) {
        return update("update dept_inf set name= ?,remark=? where id=? ",
                dept.getName(),dept.getRemark(),dept.getId());
    }

    @Override
    public int count() {
        List<Dept> depts = query("select * from dept_inf ");
        return depts.size() ;
    }

//添加
    @Override
    public int addDept(Dept dept) {
        return update("insert into dept_inf values(null,?,?,?)",
                dept.getName(),dept.getRemark(),0);
    }

    @Override
    public boolean checkDeptName(String name) {
        List<Dept> depts = query("select * from dept_inf where name = ?",name);
//        判断集合中是否存在数据
        if(depts.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Dept> getAllDept() {
        return query("select * from dept_inf");
    }

    @Override
    public int delDept(int id) {
        return update("delete from dept_inf where id=?",id);
    }

}
