/*
 * Copyright (C), 2015-2018
 * FileName: MessageEncoder
 * Author:   wanggang
 * Date:     2018/7/16 14:58
 * Description: 消息编码器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.codec;

import com.game.domain.exception.MessageCodecException;
import com.game.domain.utils.BytebufUtil;
import com.game.netBase.message.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 〈一句话功能简述〉<br>
 * 〈消息编码器〉
 *
 * @author wanggang
 * @date 2018/7/16 14:58
 * @since 1.0.1
 */
public class MessageEncoder extends MessageToByteEncoder<IMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IMessage msg, ByteBuf out) throws Exception {
        if (null == msg) {
            throw new MessageCodecException("msg is null");
        }

        ByteBuf result = BytebufUtil.doReturnByteBuf(msg.getReturnObj());
        BytebufUtil.hexLog(result, result.readerIndex());

        out.writeBytes(result);
    }

}
