package com.gec.hrm.servlet;

import com.gec.hrm.dao.UserDao;
import com.gec.hrm.dao.impl.UserDaoImpl;
import com.gec.hrm.pojo.R;
import com.gec.hrm.pojo.User;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/*登录控制器
* 作用：接受前端向后端发送的请求
* 获取前端向后端发送的参数
* 需要返回数据到前端页面上
* 1、先继承HttpServlet--Tomcat服务器提供的方法-接受请求
* 2、配置请求的路径--指定该类会接受哪些请求--@WebServlet--urlPatterns:配置多个请求路径
* 3、重写doget和dopost方法
* */
@WebServlet(urlPatterns = {"/jsp/login","/getCpacha"})
public class LoginServlet extends HttpServlet {

    //接受get请求
    //    接收get请求
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        验证码功能
        //通过java中awt中提供的类绘制验证码图片
//        1.创建一张图片
        int height=90;//验证码高度
        int width=300;//验证码宽度
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);//实例化一个图片对象
//        2.绘制图片对象，从图片对象中获取绘制图片的笔
        Graphics2D pen = image.createGraphics();
        //a.绘制背景
        pen.setColor(getRandomColor());
        pen.fillRect(0,0,width,height);//绘制实心矩形

        //b.绘制验证码字符串
        int letterNum=4;//验证码图片上的字符的个数
        int space=20;//验证码图片上两个字母之间的空隙
        int letterWidth=(width-(letterNum+1)*space)/letterNum;//计算每个字母占据的宽度

//        for循环每循环一次，绘制一个字母（小写字母的ascii码：97-122）
        Random random = new Random();
        String code ="";
        for (int i=0;i<letterNum;i++){
//            随机生成一个小写字母
            int ascii = random.nextInt(26) + 97;//97-122
            byte[] bytes={(byte) ascii};
            String letter=new String(bytes);
            code = code + letter;//为了保存验证码字符到session
//            绘制字母
            pen.setColor(getRandomColor());
            pen.setFont(new Font("Gulim",Font.BOLD,70));
            pen.drawString(letter,space+(letterWidth+space)*i,height-space);//把该字母写在画布上

        }
        HttpSession session = request.getSession();
//        session--会话--作用，就是将数据保存到会话中，会话优势保存在服务器中
        session.setAttribute("code",code);
        //添加图片干扰，防止机器自动识别
//            图片绘制完成后，将图片通过response输出流响应到客户端
        ImageIO.write(image,"png",response.getOutputStream());
    }

    private Color getRandomColor() {
        Random random = new Random();
        int r=random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        Color color = new Color(r, g, b);
        return color;
    }


    private UserDao userDao = new UserDaoImpl();
    //接受post请求
    //HttpServletREquest req 作用:接收端传过来的数据
    //HttpServletResponse resp 作用：响应数据到页面上，返回数据
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
//        处理乱码问题
        response.setCharacterEncoding("UTF-8");
//        通过响应的response获取打印输出流
        PrintWriter out = response.getWriter();
        //获取用户输入的账号、密码、验证码
//        获取从页面传过来的参数
        String username = request.getParameter("username");//跟页面中表单的username属性值一致
        String password = request.getParameter("password");//跟页面中表单的password属性值一致
        String vcode = request.getParameter("vcode");//跟页面中表单的password属性值一致
//        判断验证码
//        获取会话对象
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
//        判断验证码是否一致
        if(code.equals(vcode)){
            User user = userDao.login(username, password);
//            判断
            if (user!=null){
//                创建你返回结果的对象
                R r = new R();
//                登录成功后将当前用户信息保存
                session.setAttribute("user",user);
//                将user对象保存在user中
                r.put("user",user);
                r.put("msg","登录成功");
                out.print(new Gson().toJson(r));
            }else{
//                登录失败
                out.print(new Gson().toJson(R.error("账号或密码错误")));
            }
        }else {
//            验证码错误的方式
            out.print(new Gson().toJson(R.error("验证码错误，请重新输入")));
        }

    }
}
