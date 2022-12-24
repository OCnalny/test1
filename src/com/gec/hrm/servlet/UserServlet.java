package com.gec.hrm.servlet;

import com.gec.hrm.dao.UserDao;
import com.gec.hrm.dao.impl.UserDaoImpl;
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

@WebServlet(urlPatterns = {"/userlist.action","/checkName.action","/addUser.action","/updUser.action","/delUser.action","/delUsers.action"})
public class UserServlet extends HttpServlet {
//    先床架你UserDao的对象
    private UserDao userDao = new UserDaoImpl();
//    定义登录方法--查询操作
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        通过request获取请求uri
        String uri = request.getRequestURI();
        String action = uri.substring((uri.lastIndexOf("/") + 1));
        if (action.equals("userlist.action")) {
            userlist(request,response);
        }else if(action.equals("checkName.action")){
            checkName(request,response);
        }else if(action.equals("addUser.action")){
            addUser(request,response);
        }else if(action.equals("updUser.action")){
            updUser(request,response);
        }else if(action.equals("delUser.action")){
            delUser(request,response);
        }else if(action.equals("delUsers.action")){
            delUsers(request,response);
        }
    }

    private void delUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out  = response.getWriter();
        String ids = request.getParameter("ids");
        String[] id =ids.split(",");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        boolean delFlag = true;
        for(int i = 0;i<id.length;i++) {
            if (user.getId() != Integer.parseInt(id[i])) {
                delFlag = false;
            }
        }
                if (delFlag) {
                    for (int i = 0;i<id.length;i++){
                        int flag = userDao.delUser(Integer.parseInt(id[i]));
                        if(flag>0){
                            out.print(0);
                        }else{
                            out.print(-1);
                        }
                    }
                }

    }

    //    删除方法
    private void delUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out  = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
//        删除之前先做判断，判断当前删除的用户是否是已登陆的用户
//        1.先取到当前登录的用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        2.判断登录的id和需要删除的用户id是否一致，不一致就删除，一致就响应回页面
        if(user.getId()!=id) {
            int i = userDao.delUser(id);
            if (i > 0) {
                out.print(0);
            } else {
                out.print(-1);
            }
        }else {//删除的id与当前登录一致
                out.print(user.getId());
        }
    }

    private void updUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out  = response.getWriter();
        String loginname = request.getParameter("loginname");
        String username = request.getParameter("username");
        int status = Integer.parseInt(request.getParameter("status"));
        int id = Integer.parseInt(request.getParameter("id"));
        User user = new User(id,loginname,status,username);
        int i = userDao.updUser(user);
        if(i>0){
//            成功
            out.print(1);
        }else {
//            失败
            out.print(0);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out  = response.getWriter();
        String loginname = request.getParameter("loginname");
        String username = request.getParameter("username");
        int status = Integer.parseInt(request.getParameter("status"));
        String password = request.getParameter("password");
        User user = new User(loginname,password,status,username);
        int i = userDao.addUser(user);
        if(i>0){
            out.print(1);
        }else{
            out.print(0);
        }

    }

    private void checkName(HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
//        接受登录名
        String logginname = request.getParameter("loginname");
        boolean flag = userDao.checkName(logginname);
        if(flag){//用户名存在
            out.print(0);
        }else{
            out.print(1);
        }
    }

    private void userlist(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String loginname = request.getParameter("username");
        String status = request.getParameter("status");
//        当前页数
        int page = Integer.parseInt(request.getParameter("page"));
//        每页展示多少条数据
        int limit = Integer.parseInt(request.getParameter("limit"));

        List<User> users = userDao.userlist(loginname, status,page,limit);

        int count = userDao.count();

        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", users);
        r.put("count", count);
        r.put("code", 0);

        out.print(new Gson().toJson(r));
    }

    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        doGet(req,resp);
    }
}
