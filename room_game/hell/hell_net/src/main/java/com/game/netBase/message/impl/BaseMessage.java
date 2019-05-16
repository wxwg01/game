/*
 * Copyright (C), 2015-2018
 * FileName: BaseMessage
 * Author:   wanggang
 * Date:     2018/7/18 10:56
 * Description: message的基类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.netBase.message.impl;

import com.game.netBase.message.IMessage;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈message的基类〉
 *
 * @author wanggang
 * @date 2018/7/18 10:56
 * @since 1.0.1
 */
public abstract class BaseMessage implements IMessage {
    /**
     * 消息来源的flag
     */
    protected short flag;
    /**
     * 消息号
     */
    protected short messageId;

    protected Object param;

    protected List<Object> returnObj;

    @Override
    public short getFlag() {
        return flag;
    }

    @Override
    public void setFlag(short flag) {
        this.flag = flag;
    }

    @Override
    public short getMessageId() {
        return messageId;
    }

    @Override
    public void setMessageId(short messageId) {
        this.messageId = messageId;
    }

    @Override
    public Object getParam() {
        return param;
    }

    @Override
    public void setParam(Object param) {
        this.param = param;
    }

    public List<Object> getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(List<Object> returnObj) {
        this.returnObj = returnObj;
    }
}
