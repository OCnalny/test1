package com.gec.hrm.dao.impl;

import com.gec.hrm.dao.JobDao;
import com.gec.hrm.pojo.Job;
import com.gec.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JobDaoImpl extends JDBCUtils<Job> implements JobDao {

    @Override
    public Job getBean(ResultSet rs) {
        Job job = new Job();
//        将数据从返回结果中去取出，
        try {
            job.setId(rs.getInt("ID"));
            job.setName(rs.getString("NAME"));
            job.setRemark(rs.getString("REMARK"));
            job.setState(rs.getInt("state"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return job;
    }

    @Override
    public List<Job> jobList(String name, int page, int limit) {
        String sql = "select * from job_inf where 1=1";//万能条件
        //对条件做判断，不为空
        if (name != null && !"".equals(name)) {
            sql += " and NAME like '%" + name + "%'";
        }
        sql += " limit " + (page - 1) * limit + "," + limit;
        return query(sql);
    }

    @Override
    public int countJob() {
        List<Job> users = query("select * from job_inf");
        return users.size();
    }

    @Override
    public boolean checkJobName(String name) {
        List<Job> jobs = query("select * from job_inf where NAME = ?", name);
        if (jobs.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int addJob(String name, String remark) {
        return update("insert into job_inf values(null,?,?,?)", name, remark, 0);
    }

    @Override
    public int updJob(int id, String name, String remark) {
        return update("update job_inf set NAME=?,REMARK=? where ID=?", name, remark, id);
    }

    @Override
    public int delJob(int id) {
        return update("delete from job_inf where ID=?",id);
    }

    @Override
    public List<Job> getAllJob() {
        return query("select * from job_inf");
    }
}
