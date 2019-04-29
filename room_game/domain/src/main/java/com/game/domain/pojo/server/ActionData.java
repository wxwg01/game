package com.game.domain.pojo.server;

/**
 * :
 */
public class ActionData {

    public String uuid;// 唯一id
    public byte action;// 0未操作，1打2防3畜力4技能
    public byte skillId;// 0未知1男2女
    public int time;// 毫秒
    public int hp;// 血量
    public int changeHp;// 变化的血量
    public int isCrit;// 0不是暴击，1暴击
    public int mp;// 怒气
    public int changeMp;// 变化的怒气
    public int action2;// 0不触发，1格挡，2闪避
    public int action2ChangeHp;// 伤害
    public int action2ChangeMp;// 怒气

}
