package com.game.domain.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.List;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/27 10:51
 * @Since 0.0.1
 */
@XStreamAlias("def")
public class SkillList {
    @XStreamAsAttribute
    @XStreamAlias("unit")
    private List<Skill> skillList;

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }
}
