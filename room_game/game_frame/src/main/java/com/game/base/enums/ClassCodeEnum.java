package com.game.base.enums;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 *
 * @date : 2019/5/15
 */
public enum ClassCodeEnum {
    ;

    private final int code;
    private final String desc;
    private final Class bean;

    ClassCodeEnum(int code, String desc, Class bean) {
        this.code = code;
        this.desc = desc;
        this.bean = bean;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public Class getBean() {
        return this.bean;
    }

    public static int getCodeByBean(Class bean) {
        ClassCodeEnum[] enums = values();
        for (ClassCodeEnum cEnumValue : enums) {
            if (cEnumValue.getBean().getName().equals(bean.getName())) {
                return cEnumValue.getCode();
            }
        }
        return 0;
    }

    public static Class getBeanByCode(int code) {
        ClassCodeEnum[] sEnumValues = values();
        for (ClassCodeEnum codeEnum : sEnumValues) {
            if (codeEnum.getCode() == code) {
                return codeEnum.getBean();
            }
        }
        return null;
    }


}
