package com.gec.hrm.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.dao.JobDao;
import com.gec.hrm.dao.impl.JobDaoImpl;
import com.gec.hrm.pojo.Job;
import com.gec.hrm.pojo.R;
import com.google.gson.Gson;

import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/jobList.action", "/getAllJob.action","/checkJobName.action", "/addJob.action", "/updJob.action", "/delJob.action", "/delJobs.action"})
public class    JobServlet extends HttpServlet {
    private final JobDao jobDao = new JobDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        switch (action) {
            case "jobList.action" ->{
                String name = request.getParameter("name");
                //当前页数
                int page = Integer.parseInt(request.getParameter("page"));
                //每页多少条
                int limit = Integer.parseInt(request.getParameter("limit"));
                //查询方法
                List<Job> jobs = jobDao.jobList(name, page, limit);
                R r = new R();
                r.put("msg", "查询成功");
                r.put("data", jobs);
                r.put("count", jobDao.countJob());
                r.put("code", 0);
                out.print(new Gson().toJson(r));
                break;
            }
            case "getAllJob.action" ->{
                out.print(new Gson().toJson(jobDao.getAllJob()));
            }
            case "checkJobName.action" -> {
                String name = request.getParameter("name");
                if (jobDao.checkJobName(name)) {
                    out.print(0);//用户已存在
                } else {
                    out.print(1);
                }
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        switch (action) {
            case "addJob.action" : {
                String name = request.getParameter("name");
                String remark = request.getParameter("remark");
                if (jobDao.addJob(name, remark) > 0) {
                    out.print(1);//添加成功
                } else {
                    out.print(0);
                }
                break;
            }
            case "updJob.action" : {
                String name = request.getParameter("name");
                String remark = request.getParameter("remark");
                int id = Integer.parseInt(request.getParameter("id"));
                if (jobDao.updJob(id, name, remark) > 0) {
                    out.print(1);//修改成功
                } else {
                    out.print(0);
                }
                break;
            }
            case "delJob.action" : {
                int id = Integer.parseInt(request.getParameter("id"));
                //TODO:级联删除
                if (jobDao.delJob(id) > 0) {
                    out.print(1);//删除成功
                } else {
                    out.print(0);
                }
                break;
            }
            case "delJobs.action" : {
                //TODO:级联删除
                String ids = request.getParameter("ids");
                String[] id =ids.split(",");
                for (int i = 0;i<id.length;i++){
                    int flag = jobDao.delJob(Integer.parseInt(id[i]));
                    if(flag>0){
                        out.print(1);
                    }else{
                        out.print(0);
                    }
            }
                break;
        }
    }
    }
}
