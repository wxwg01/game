package com.game.domain.pojo.client;

/**
 * 10001 : 登陆
 */
public class CLogin {

    private String account;// 账号
    private String pwd;// 密码

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }
}
