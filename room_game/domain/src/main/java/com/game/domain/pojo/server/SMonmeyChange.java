package com.game.domain.pojo.server;

/**
 * 20107 :
 */
public class SMonmeyChange {

    private String uuid;// 人物唯一ID
    private int monmey;// 当前
    private int monmeyChange;//

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setMonmey(int monmey) {
        this.monmey = monmey;
    }

    public int getMonmey() {
        return monmey;
    }

    public void setMonmeyChange(int monmeyChange) {
        this.monmeyChange = monmeyChange;
    }

    public int getMonmeyChange() {
        return monmeyChange;
    }
}
