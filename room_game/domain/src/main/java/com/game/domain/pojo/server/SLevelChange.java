package com.game.domain.pojo.server;

/**
 * 20108 :
 */
public class SLevelChange {

    private String uuid;// 人物唯一ID
    private long level;// 当前
    private long levelChange;//

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getLevel() {
        return level;
    }

    public void setLevelChange(long levelChange) {
        this.levelChange = levelChange;
    }

    public long getLevelChange() {
        return levelChange;
    }
}
