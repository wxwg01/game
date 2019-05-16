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
public class Level {
    @XStreamAsAttribute
    @XStreamAlias("level")
    private int level;//等级
    @XStreamAsAttribute
    @XStreamAlias("exp")
    private long exp;//升级需要的经验

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }
}
