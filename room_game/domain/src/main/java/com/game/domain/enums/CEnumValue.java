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


import com.game.domain.pojo.client.CCreateRole;
import com.game.domain.pojo.client.CFastJoinRoom;
import com.game.domain.pojo.client.CLogin;
import com.game.domain.pojo.client.CRound;

/**
 * 〈一句话功能简述〉<br>
 * 〈放置常量〉
 */
public enum CEnumValue {
    CLOGIN((short) 10001, "登陆", /*CLoginHandler.class,*/ CLogin.class),
    CCREATEROLE((short) 10002, "创建角色", /*CCreateRoleHandler.class,*/ CCreateRole.class),
    CFASTJOINROOM((short) 10101, "快速加入房间", /*CFastJoinRoomHandler.class,*/ CFastJoinRoom.class),
    CROUND((short) 10102, "回合数据", /*CRoundHandler.class,*/ CRound.class);

    private final short code;
    private final String desc;
    /*private final Class handler;*/
    private final Class bean;

    CEnumValue(short code, String desc,/* Class handler,*/ Class bean) {
        this.code = code;
        this.desc = desc;
        /*this.handler =handler;*/
        this.bean = bean;
    }

    public short getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

  /*public Class getHandler(){
    return this.handler;
  }*/

    public Class getBean() {
        return this.bean;
    }

    public static Class getBeanByCode(int code) {
        CEnumValue[] cEnumValues = values();
        for (CEnumValue cEnumValue : cEnumValues) {
            if (cEnumValue.getCode() == code) {
                return cEnumValue.getBean();
            }
        }
        return null;
    }
}
