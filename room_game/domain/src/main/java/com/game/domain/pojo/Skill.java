package com.game.domain.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/27 10:51
 * @Since 0.0.1
 */
@XStreamAlias("unit")
public class Skill {
    @XStreamAsAttribute
    @XStreamAlias("id")
    private int id;
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;
    @XStreamAsAttribute
    @XStreamAlias("hitAdd")
    private  double hitAdd = 1;//攻击加成
    @XStreamAsAttribute
    @XStreamAlias("blockHitMpAdd")
    private double blockHitMpAdd =1;// 被反击蓝量加成
    @XStreamAsAttribute
    @XStreamAlias("beHitAdd")
    private double beHitAdd =1;// 被攻击伤害增加
    @XStreamAsAttribute
    @XStreamAlias("blockHitAdd")
    private double blockHitAdd = 1;//反击率加成
    @XStreamAsAttribute
    @XStreamAlias("ignoreDodge")
    private double ignoreDodge = 0;//无视闪避

    @XStreamAsAttribute
    @XStreamAlias("initTime")
    private int initTime = 3;// 先攻等级 1》2》3
    @XStreamAsAttribute
    @XStreamAlias("round")
    private int round ;// 失效回合


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHitAdd() {
        return hitAdd;
    }

    public void setHitAdd(double hitAdd) {
        this.hitAdd = hitAdd;
    }

    public double getBlockHitMpAdd() {
        return blockHitMpAdd;
    }

    public void setBlockHitMpAdd(double blockHitMpAdd) {
        this.blockHitMpAdd = blockHitMpAdd;
    }

    public double getBeHitAdd() {
        return beHitAdd;
    }

    public void setBeHitAdd(double beHitAdd) {
        this.beHitAdd = beHitAdd;
    }

    public double getBlockHitAdd() {
        return blockHitAdd;
    }

    public void setBlockHitAdd(double blockHitAdd) {
        this.blockHitAdd = blockHitAdd;
    }

    public int getInitTime() {
        return initTime;
    }

    public void setInitTime(int initTime) {
        this.initTime = initTime;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIgnoreDodge() {
        return ignoreDodge;
    }

    public void setIgnoreDodge(double ignoreDodge) {
        this.ignoreDodge = ignoreDodge;
    }
}
