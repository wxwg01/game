/*
 * Copyright (C), 2015-2018
 * FileName: WebSocketFrameToIMessageDecoder
 * Author:   wanggang
 * Date:     2018/8/13 19:25
 * Description: WebSocketFrame转换成IMessage
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.codec;

import com.game.domain.constant.ConstantValue;
import com.game.domain.enums.CommonValue;
import com.game.domain.utils.BytebufUtil;
import com.game.netBase.message.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈WebSocketFrame转换成IMessage〉
 *
 * @author wanggang
 * @date 2018/8/13 19:25
 * @since 1.0.1
 */
public class WebSocketFrameToIMessageDecoder extends MessageToMessageDecoder<Object> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        if (msg instanceof FullHttpRequest) {
            // 传统的HTTP接入
            out.add(msg);
        } else if (msg instanceof WebSocketFrame) {
            // out.add(msg)
            ByteBuf content = Unpooled.buffer();
            ;
            if (msg instanceof TextWebSocketFrame) {
                String str = ((TextWebSocketFrame) msg).text();
                System.out.println(str);
                for (int i = 0; i < str.length() / 2; i++) {
                    int aa = Integer.parseInt(str.substring(i * 2, (i + 1) * 2), 16);
                    System.out.println(aa);
                    content.writeByte(aa);
                }

            } else if (msg instanceof BinaryWebSocketFrame) {
                content = ((BinaryWebSocketFrame) msg).content();
            } else {
                content = ((WebSocketFrame) msg).content();
            }
            // WebSocket接入

            BytebufUtil.hexLog(content, content.readerIndex());

            MessageDecoder messageDecoder = new MessageDecoder(ConstantValue.MESSAGE_CODEC_MAX_FRAME_LENGTH,
                    ConstantValue.MESSAGE_CODEC_LENGTH_FIELD_LENGTH, ConstantValue.MESSAGE_CODEC_LENGTH_FIELD_OFFSET,
                    ConstantValue.MESSAGE_CODEC_LENGTH_ADJUSTMENT, ConstantValue.MESSAGE_CODEC_INITIAL_BYTES_TO_STRIP, false,
                    CommonValue.msgType);
            IMessage iMessage = messageDecoder.decodePub(ctx, content);
            out.add(iMessage);

        } else {
            out.add(msg);
        }

    }
}
