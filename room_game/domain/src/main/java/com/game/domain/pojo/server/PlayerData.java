package com.game.domain.pojo.server;

/**
 * :
 */
public class PlayerData {

    private String uuid;// 唯一id
    private String name;// 姓名
    private byte sex;// 0未知1男2女
    private short level;// 等级
    private long exp;// 经验
    private long gold;// 金钱
    private int monmey;// 钻石
    private BattleData battleData;// 钻石

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

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

    public void setLevel(short level) {
        this.level = level;
    }

    public short getLevel() {
        return level;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getExp() {
        return exp;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getGold() {
        return gold;
    }

    public void setMonmey(int monmey) {
        this.monmey = monmey;
    }

    public int getMonmey() {
        return monmey;
    }

    public void setBattleData(BattleData battleData) {
        this.battleData = battleData;
    }

    public BattleData getBattleData() {
        return battleData;
    }
}
