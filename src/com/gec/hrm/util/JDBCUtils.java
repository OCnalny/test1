package com.gec.hrm.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//封装jdbc的操作  查询-添加-修改-删除
public abstract class JDBCUtils<T> {
    //加载驱动的方法
    //static静态代码块
    static {
        //try：捕获异常，处理异常，如果报错，程序不会终止，会接着执行
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//获取数据库连接方法
    public Connection getConn(){
        try {
          return  DriverManager.getConnection("jdbc:mysql://localhost:3306/hrm?characterEncoding=UTF-8","root","root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    public List<T> query(String sql,Object...obj){
        //创建集合保存多条数据
        List<T> list=new ArrayList<>();
        try {
            PreparedStatement ps=getConn().prepareStatement(sql);
            System.out.println("初始化的ps对象："+ps);

            //设置条件参数
            for (int i=0;i<obj.length;i++){
                System.out.println("第"+(i+1)+"次循环，参数是："+obj[i]);
                //对条件进行赋值
                ps.setObject(i+1,obj[i]);
            }
            System.out.println("赋值后的ps对象："+ps);
            //通过api执行sql语句
            ResultSet rs=ps.executeQuery();
            //遍历结果集对象
            while (rs.next()){
                list.add(getBean(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    //    添加 修改 删除的方法 不确定参数个数的情况  ...可变形参，确定参数类型，不确定个数
    public int update(String sql,Object... obj ){
        PreparedStatement ps=null;
        try {
//            创建preparedstatment对象
            ps = getConn().prepareStatement(sql);
//            不确定有多少个条件
//            可以使用循环，遍历
            for (int i = 0; i < obj.length; i++) {
                System.out.println("第"+i+"个条件,参数是："+obj[i]);
                ps.setObject(i+1,obj[i]);
            }
//            执行sql语句
           return ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }


//定义一个抽象方法 没有实现方法，T：该方法会返回一个对象，将结果集对象传入
    //该方法由子类实现
    public  abstract T getBean(ResultSet rs);
    //添加

    // 关闭
    private void getClose(Connection conn, PreparedStatement pst, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (pst != null)
                pst.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
