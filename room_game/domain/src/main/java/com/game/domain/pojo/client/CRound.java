package com.game.domain.pojo.client;

/**
 * 10102 : 回合数据
 */
public class CRound {

    private byte type;// 1左2右
    private int action;// 0未操作，1打2防3畜力4技能
    private int skillId;//

    public void setType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillId() {
        return skillId;
    }
}
