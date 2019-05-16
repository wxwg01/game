package com.game.domain.pojo.client;

/**
 * 10002 : 创建角色
 */
public class CCreateRole {

    private String name;// 账号
    private byte sex;// 0未知1男2女
    private String headUrl;// 头像地址

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public byte getSex() {
        return sex;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }
}
