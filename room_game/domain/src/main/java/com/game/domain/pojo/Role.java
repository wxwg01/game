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
public class Role {
    @XStreamAsAttribute
    @XStreamAlias("id")
    private int id;

    @XStreamAsAttribute
    @XStreamAlias("sex")
    private int sex;

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;

    @XStreamAsAttribute
    @XStreamAlias("icon")
    private String icon;

    @XStreamAsAttribute
    @XStreamAlias("prop")
    private String prop;

    private int level = 1;
    private int hp = 5;
    private int skillId;
    private int mpMax = 300;
    private int mp;
    private int miss;
    private int block;
    private int blockHit;//格挡反击
    private int cs;//Critical strike 暴击
    private int cd = 100;//Critical damage 暴击伤害

    private int att;// 初始攻击
    private int antiCrit;// 暴抗
    private int intitate;//先手

    private int ignoreDodge;// 无视闪避

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getMpMax() {
        return mpMax;
    }

    public void setMpMax(int mpMax) {
        this.mpMax = mpMax;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getBlockHit() {
        return blockHit;
    }

    public void setBlockHit(int blockHit) {
        this.blockHit = blockHit;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getAntiCrit() {
        return antiCrit;
    }

    public void setAntiCrit(int antiCrit) {
        this.antiCrit = antiCrit;
    }

    public int getIntitate() {
        return intitate;
    }

    public void setIntitate(int intitate) {
        this.intitate = intitate;
    }

    public int getAtt() {
        return att;
    }

    public void setAtt(int att) {
        this.att = att;
    }

    public int getIgnoreDodge() {
        return ignoreDodge;
    }

    public void setIgnoreDodge(int ignoreDodge) {
        this.ignoreDodge = ignoreDodge;
    }
}
