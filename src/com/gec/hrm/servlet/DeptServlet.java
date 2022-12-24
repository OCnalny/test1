package com.gec.hrm.servlet;

import com.gec.hrm.dao.DeptDao;
import com.gec.hrm.dao.impl.DeptDaoImpl;
import com.gec.hrm.pojo.Dept;
import com.gec.hrm.pojo.R;
import com.gec.hrm.pojo.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/deptList.action","/getAllDept.action","/updDept.action","/addDept.action","/checkDeptName.action","/delDept.action","/delDepts.action"})
public class DeptServlet extends HttpServlet {

    private DeptDao deptDao = new DeptDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String action = uri.substring((uri.lastIndexOf("/") + 1));
        if (action.equals("deptList.action")) {
            deptlist(request, response);
        } else if (action.equals("updDept.action")) {
            updDept(request,response);
        }else if (action.equals("addDept.action")){
            addDept(request,response);
        }else if(action.equals("checkDeptName.action")){
            checkDeptName(request,response);
        }else if(action.equals("delDept.action")){
            delDept(request,response);
        }else if(action.equals("delDepts.action")){
            delDepts(request,response);
        }else if(action.equals("getAllDept.action")){
            getAllDept(request,response);
        }
    }

    private void getAllDept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out  = response.getWriter();
        List<Dept> allDept = deptDao.getAllDept();
        out.print(new Gson().toJson(allDept));
    }


    //    case "getAllDept.action" -> {
//        out.print(new Gson().toJson(deptDao.getAllDept()));
//    }
    private void delDepts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out  = response.getWriter();
        String ids = request.getParameter("ids");
        String[] id =ids.split(",");

            for (int i = 0;i<id.length;i++){
                int flag = deptDao.delDept(Integer.parseInt(id[i]));
                if(flag>0){
                    out.print(1);
                }else{
                    out.print(0);
            }
        }
    }

    private void delDept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out  = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
//        删除之前先做判断，判断当前删除的用户是否是已登陆的用户
//        1.先取到当前登录的用户信息
        HttpSession session = request.getSession();
        Dept dept = (Dept) session.getAttribute("dept");
//        2.判断登录的id和需要删除的用户id是否一致，不一致就删除，一致就响应回页面
            int i = deptDao.delDept(id);
            if (i > 0) {
                out.print(1);
            } else {
                out.print(0);
            }
    }

    private void checkDeptName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
//        接受登录名
        String name = request.getParameter("name");
        boolean flag = deptDao.checkDeptName(name);
        if(flag){//用户名存在
            out.print(0);
        }else{
            out.print(1);
        }
    }

    private void addDept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out  = response.getWriter();
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        Dept dept = new Dept(name,remark);
        int i = deptDao.addDept(dept);
        if(i>0){
            out.print(1);
        }else{
            out.print(0);
        }

    }

    private void updDept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out  = response.getWriter();
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        int id = Integer.parseInt(request.getParameter("id"));
        Dept dept = new Dept(id,name,remark);
        int i = deptDao.updDept(dept);
        if(i>0){
//            成功
            out.print(1);
        }else {
//            失败
            out.print(0);
        }
    }

    private void deptlist(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String status = request.getParameter("state");
//        当前页数
        int page = Integer.parseInt(request.getParameter("page"));
//        每页展示多少条数据
        int limit = Integer.parseInt(request.getParameter("limit"));

        List<Dept> depts = deptDao.deptlist(name, status,page,limit);

        int count = deptDao.count();

        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", depts);
        r.put("count", count);
        r.put("code", 0);

        out.print(new Gson().toJson(r));
    }

    @Override
        protected void doPost (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            doGet(request, response);
        }

}
