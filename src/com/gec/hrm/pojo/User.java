package com.gec.hrm.pojo;

import java.util.Date;

public class User {
    private int id;
    private String loginname;
    private String password;
    private int status;
    private Date createdate;
    private String username;

    public User(int id, String loginname, int status, String username) {
        this.id = id;
        this.loginname = loginname;
        this.status = status;
        this.username = username;
    }

    public User(){

    }
    public User(String loginname, String password, int status, String username) {
        this.loginname = loginname;
        this.password = password;
        this.status = status;
        this.username = username;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
