package com.game.domain.pojo.server;

/**
 * :
 */
public class BattleData {

    private String uuid;// 唯一id
    private int battlenum;// 总战斗数
    private int winnum;// 胜场
    private int attacknum;// 攻击数
    private int blocknum;// 格档数
    private int xulinum;// 畜力数

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setBattlenum(int battlenum) {
        this.battlenum = battlenum;
    }

    public int getBattlenum() {
        return battlenum;
    }

    public void setWinnum(int winnum) {
        this.winnum = winnum;
    }

    public int getWinnum() {
        return winnum;
    }

    public void setAttacknum(int attacknum) {
        this.attacknum = attacknum;
    }

    public int getAttacknum() {
        return attacknum;
    }

    public void setBlocknum(int blocknum) {
        this.blocknum = blocknum;
    }

    public int getBlocknum() {
        return blocknum;
    }

    public void setXulinum(int xulinum) {
        this.xulinum = xulinum;
    }

    public int getXulinum() {
        return xulinum;
    }
}
