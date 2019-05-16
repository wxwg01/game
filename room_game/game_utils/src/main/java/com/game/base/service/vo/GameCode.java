package com.game.base.service.vo;

import java.io.Serializable;

/**
 * IoTx code struct
 *
 * @author zhuangyao.zy
 * @date 2017/3/17
 */
public class GameCode implements Serializable {

    public GameCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameCode gameCode = (GameCode)o;

        return code == gameCode.code;

    }

    @Override
    public int hashCode() {
        return code;
    }
}
