/*
 * Copyright (C), 2015-2018
 * FileName: CommonValue
 * Author:   wanggang
 * Date:     2018/6/25 16:45
 * Description: 放置常量
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.domain.enums;

import com.game.domain.pojo.server.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈放置常量〉
 */
public enum SEnumValue {
    SLOGIN(20001, "登陆", SLogin.class),
    SCREATEROLE(20002, "创建角色返回", SCreateRole.class),
    SFASTJOINROOM(20101, "快速加入房间返回", SFastJoinRoom.class),
    SSTARTROUND(20102, "回合开始", SStartRound.class),
    SROUNDRESULT(20103, "回合战报", SRoundResult.class),
    SROUNDEND(20104, "所有回合结束", SRoundEnd.class),
    SEXPCHANGE(20105, "人物经验变化", SExpChange.class),
    SGOLDCHANGE(20106, "金币变化", SGoldChange.class),
    SMONMEYCHANGE(20107, "钻石变化", SMonmeyChange.class),
    SLEVELCHANGE(20108, "等级变化", SLevelChange.class);

    private final int code;
    private final String desc;
    private final Class bean;

    SEnumValue(int code, String desc, Class bean) {
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
        SEnumValue[] sEnumValues = values();
        for (SEnumValue cEnumValue : sEnumValues) {
            if (cEnumValue.getBean().getName().equals(bean.getName())) {
                return cEnumValue.getCode();
            }
        }
        return 0;
    }

    public static Class getBeanByCode(int code) {
        SEnumValue[] sEnumValues = values();
        for (SEnumValue cEnumValue : sEnumValues) {
            if (cEnumValue.getCode() == code) {
                return cEnumValue.getBean();
            }
        }
        return null;
    }
}
