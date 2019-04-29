/*
 * Copyright (C), 2015-2018
 * FileName: StringMessage
 * Author:   wanggang
 * Date:     2018/7/12 10:55
 * Description: 发送过来的请求
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.netBase.message.impl;

import com.alibaba.fastjson.JSONObject;
import com.game.domain.constant.ConstantValue;
import com.game.domain.exception.MessageCodecException;
import com.game.netBase.message.IMessage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 〈一句话功能简述〉<br>
 * 〈String类型的请求〉
 *
 * @author wanggang
 * @date 2018/7/12 10:55
 * @since 1.0.1
 */
public class StringMessage extends BaseMessage implements IMessage {

    /**
     * 具体内容
     */
    private String body;

    public StringMessage() {
    }

    public StringMessage(short messageId) {
        this.messageId = messageId;
    }


    public static StringMessage create(String origin) {
        StringMessage stringMessage = JSONObject.parseObject(origin, StringMessage.class);
        return stringMessage;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public byte[] getBodyByte() {
        return body.getBytes(Charset.forName(ConstantValue.PROJECT_CHARSET));
    }

    @Override
    public void setBodyByte(byte[] body) throws MessageCodecException {
        try {
            this.body = new String(body, ConstantValue.PROJECT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new MessageCodecException("setBodyByte UnsupportedEncodingException");
        }
    }

    @Override
    public String toString() {
        return "StringMessage{" + "messageId=" + messageId + ", statusCode=" + ", body='" + body + '\'' + '}';
    }
}
