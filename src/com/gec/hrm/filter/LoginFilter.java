//package com.gec.hrm.filter;
//
//import com.gec.hrm.pojo.User;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter({"*.action","*.html"})
//public class LoginFilter implements Filter{//    初始化的方法
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//    //
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
////     由于目前的执行方法doFIlter执行的请求是Servlet的方式，我们需要转换为HttpServlet
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
////      获取信息路径
//        String path = req.getServletPath();
////        判断过滤器拦截的信息请求
//        if ("/login.action".equals(path)||"/register.action".equals(path)||"/jsp/login.html".equals(path)){
//            chain.doFilter(req,res);
//        }else {
//            HttpSession session = req.getSession();
//            User user = (User) session.getAttribute("user");
//            /*注意事项：如果登录成功的话，登录状态会一直记录到session当中，直到你退出位置，如果你没有退出重新直接访问首页
//            * 如果没有退出重新，直接访问首页，是不会拦截，因为session中的登录状态一直记录在上没有退出！可以进行访问到首页
//            * */
//            if(user!=null){
//                chain.doFilter(req,res);
//            }else {
//                res.sendRedirect("/jsp/login.html");
//            }
//        }
//    }
//    //  结束方法，终止
//    @Override
//    public void destroy() {
//
//    }
//}
