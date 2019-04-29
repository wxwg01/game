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

/**
 * 〈一句话功能简述〉<br>
 * 〈放置常量〉
 */
public enum ErrorCodeEnum {
    SUC(0),
    SAVE_FAILED(1001),
    CREATRE_ROLE_FAILED(1002),
    NO_ACCOUNT(1003),
    JOIN_FAILED(1004);

    private final int code;

    ErrorCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
