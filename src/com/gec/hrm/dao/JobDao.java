package com.gec.hrm.dao;

import com.gec.hrm.pojo.Job;

import java.util.List;

public interface JobDao {
    List<Job> jobList(String name, int page, int limit);

    int countJob();

    boolean checkJobName(String name);

    int addJob(String name, String remark);

    int updJob(int id, String name, String remark);

    int delJob(int id);

    public List<Job> getAllJob();
}
