package com.game.domain.pojo.server;

/**
 * 20105 : 人物经验变化
 */
public class SExpChange {

    private String uuid;// 人物唯一ID
    private long exp;// 当前人物经验
    private long expChange;//

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getExp() {
        return exp;
    }

    public void setExpChange(long expChange) {
        this.expChange = expChange;
    }

    public long getExpChange() {
        return expChange;
    }
}
