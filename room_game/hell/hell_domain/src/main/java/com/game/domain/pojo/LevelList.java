package com.game.domain.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/28 14:26
 * @Since 0.0.1
 */
@XStreamAlias("def")
public class LevelList {
    @XStreamImplicit(itemFieldName = "unit")
    private List<Level> levels;

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
}
