package com.game.domain.pojo.server;

/**
 * 20106 :
 */
public class SGoldChange {

    private String uuid;// 人物唯一ID
    private long gold;// 当前
    private long goldChange;//

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getGold() {
        return gold;
    }

    public void setGoldChange(long goldChange) {
        this.goldChange = goldChange;
    }

    public long getGoldChange() {
        return goldChange;
    }
}
