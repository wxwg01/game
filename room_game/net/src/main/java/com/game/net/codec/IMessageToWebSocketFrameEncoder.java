/*
 * Copyright (C), 2015-2018
 * FileName: WebSocketFrameEncoder
 * Author:   wanggang
 * Date:     2018/8/13 17:12
 * Description: WebSocketFrameEncoder编码器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.codec;

import com.game.domain.constant.ConstantValue;
import com.game.domain.utils.BytebufUtil;
import com.game.netBase.message.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈WebSocketFrameEncoder编码器〉
 *
 * @author wanggang
 * @date 2018/8/13 17:12
 * @since 1.0.1
 */
public class IMessageToWebSocketFrameEncoder extends MessageToMessageEncoder<IMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMessage iMessage, List<Object> list)
            throws Exception {
        //组合缓冲区

//    list.add(new BinaryWebSocketFrame(byteBuf));
        String str = new String(iMessage.getBodyByte(), ConstantValue.PROJECT_CHARSET);
        System.out.println(str);

        ByteBuf buf = BytebufUtil.stringToByteBuf(str);

//    list.add(new TextWebSocketFrame(str));
//    channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(str));
//    channelHandlerContext.channel().writeAndFlush(new BinaryWebSocketFrame(buf));
//    channelHandlerContext.channel().writeAndFlush(buf);
        list.add(new BinaryWebSocketFrame(buf));
    }
}
