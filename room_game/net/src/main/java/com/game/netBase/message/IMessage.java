/*
 * Copyright (C), 2015-2018
 * FileName: IMessage
 * Author:   wanggang
 * Date:     2018/7/12 10:53
 * Description: 消息的基类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.netBase.message;

import com.game.domain.exception.MessageCodecException;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈消息的基类〉
 *
 * @author wanggang
 * @date 2018/7/12 10:53
 * @since 1.0.1
 */
public interface IMessage {
    short getFlag();

    void setFlag(short flag);

    short getMessageId();

    void setMessageId(short messageId);

    byte[] getBodyByte();

    void setBodyByte(byte[] body) throws MessageCodecException;

    Object getParam();

    void setParam(Object object);

    List<Object> getReturnObj();

    void setReturnObj(List<Object> returnObj);
}
