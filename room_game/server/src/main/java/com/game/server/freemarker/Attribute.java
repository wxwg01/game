package com.game.server.freemarker;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/14 16:15
 * @Since 0.0.1
 */
public class Attribute {

    private String type;
    private String name;
    private String desc;

    private String getSetName;

    public Attribute(String type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.getSetName = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGetSetName() {
        return getSetName;
    }

    public void setGetSetName(String getSetName) {
        this.getSetName = getSetName;
    }
}
