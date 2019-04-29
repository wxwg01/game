package com.game.domain.pojo;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/27 10:51
 * @Since 0.0.1
 */
public class Buff {
    int skillId;
    double hitAdd = 1;//攻击加成
    double blockHitMpAdd =1;// 被反击蓝量加成
    double beHitAdd =1;// 被攻击伤害增加
    double blockHitAdd = 1;//反击率加成
    int round ;// 失效回合
}
