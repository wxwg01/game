package com.game.base.service.vo;

import java.io.Serializable;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @email : wb-wg471966@alibaba-inc.com
 * @date : 2019/5/15
 */
public class GameResult<DataType> implements Serializable {


    private static final String EMPTY_MESSAGE = "";

    /**
     * default result code is ${success}
     */
    public GameResult() {
        this(GameCodes.SUCCESS.getCode(), GameCodes.SUCCESS.getMessage(), null);
    }

    public GameResult(int code) {
        this(code, EMPTY_MESSAGE, null);
    }

    public GameResult(int code, String msg) {
        this(code, msg, null);
    }

    public GameResult(GameCode GameCode) {
        this(GameCode.getCode(), GameCode.getMessage(), null);
    }

    public GameResult(GameCode GameCode, String msg) {
        this(GameCode.getCode(), msg, null);
    }

    public GameResult(int code, DataType data) {
        this(code, EMPTY_MESSAGE, data);
    }

    public GameResult(DataType data) {
        this(GameCodes.SUCCESS, data);
    }

    public GameResult(GameCode GameCode, DataType data) {
        this(GameCode.getCode(), GameCode.getMessage(), data);
    }

    public GameResult(int code, String msg, DataType data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    private int code;
    private String message;
    private DataType data;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IoTxResult{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
