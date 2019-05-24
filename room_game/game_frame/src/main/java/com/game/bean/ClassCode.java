package com.game.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @date : 2019/5/17
 */
public class ClassCode implements Serializable {
    private static final long serialVersionUID = -1442946233409291946L;
    private String desc;
    private Integer code;
    private Class clazz;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public ClassCode(int code, String desc, Class clazz){
        this.code = code;
        this.desc = desc;
        this.clazz = clazz;
    }

}
