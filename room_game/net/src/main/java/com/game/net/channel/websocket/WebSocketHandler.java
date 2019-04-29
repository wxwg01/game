/*
 * Copyright (C), 2015-2018
 * FileName: WebSocketHandler
 * Author:   wanggang
 * Date:     2018/8/10 11:44
 * Description: websocket的消息处理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.channel.websocket;

import com.game.domain.config.ServerConfig;
import com.game.domain.constant.ConstantValue;
import com.game.domain.exception.MessageCodecException;
import com.game.domain.utils.HttpResponseUtil;
import com.game.net.codec.MessageDecoder;
import com.game.netBase.customer.INetworkConsumer;
import com.game.netBase.listener.INetworkEventListener;
import com.game.netBase.message.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈websocket的消息处理〉
 *
 * @author wanggang
 * @date 2018/8/10 11:44
 * @since 1.0.1
 */
@Component
@Scope("prototype")
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {
    @Autowired
    private INetworkEventListener listener;
    @Autowired
    private INetworkConsumer consumer;

    @Autowired
    private ServerConfig serverConfig;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws MessageCodecException {
        if (msg instanceof FullHttpRequest) {
            // 传统的HTTP接入
            handleHttpMessage(ctx, msg);
        } else if (msg instanceof WebSocketFrame) {
            // WebSocket接入
            handleWebSocketMessage(ctx, msg);
        } else if (msg instanceof IMessage) {
            // 这里已经通过WebSocketFrameToIMessageDecoder进行解码，获得我们设置好的IMessage类了
            consumer.consume((IMessage) msg, ctx.channel());
        }
    }

    /**
     * 处理WebSocket中的Http消息
     *
     * @param ctx 上下文
     * @param msg 消息
     */
    private void handleHttpMessage(ChannelHandlerContext ctx, Object msg) {
        // 传统的HTTP接入
        FullHttpRequest request = (FullHttpRequest) msg;

        // 如果HTTP解码失败，返回HHTP异常
        if (!request.decoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            HttpResponseUtil.sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 正常WebSocket的Http连接请求，构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://" + request.headers().get(HttpHeaderNames.HOST), null, false);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) { // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else { // 向客户端发送websocket握手,完成握手
            handshaker.handshake(ctx.channel(), request);
        }
    }

    /**
     * 处理WebSocket中的WebSocket消息
     *
     * @param ctx 上下文
     * @param msg 消息
     */
    private void handleWebSocketMessage(ChannelHandlerContext ctx, Object msg) throws MessageCodecException {
        ByteBuf content = ((WebSocketFrame) msg).content();
        MessageDecoder messageDecoder = new MessageDecoder(ConstantValue.MESSAGE_CODEC_MAX_FRAME_LENGTH,
                ConstantValue.MESSAGE_CODEC_LENGTH_FIELD_LENGTH, ConstantValue.MESSAGE_CODEC_LENGTH_FIELD_OFFSET,
                ConstantValue.MESSAGE_CODEC_LENGTH_ADJUSTMENT, ConstantValue.MESSAGE_CODEC_INITIAL_BYTES_TO_STRIP, false,
                serverConfig.getMessageType());
        IMessage iMessage = messageDecoder.decodePub(ctx, content);
        // WebSocket接入
        consumer.consume(iMessage, ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        listener.onConnected(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        listener.onDisconnected(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        listener.onExceptionCaught(ctx, throwable);
        throwable.printStackTrace();
    }

}
