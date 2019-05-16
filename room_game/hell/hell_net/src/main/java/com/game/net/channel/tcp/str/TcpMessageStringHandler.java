/*
 * Copyright (C), 2015-2018
 * FileName: TcpMessageStringHandler
 * Author:   wanggang
 * Date:     2018/6/12 15:01
 * Description: tcp消息处理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.channel.tcp.str;

import com.game.netBase.customer.INetworkConsumer;
import com.game.netBase.listener.INetworkEventListener;
import com.game.netBase.message.IMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈tcp消息处理〉
 *
 * @author wanggang
 * @date 2018/6/12 15:01
 * @since 1.0.0
 */
@Component
@Scope("prototype")
public class TcpMessageStringHandler extends SimpleChannelInboundHandler<IMessage> {

    @Autowired
    private INetworkEventListener listener;
    @Autowired
    private INetworkConsumer consumer;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMessage msg) {
        consumer.consume(msg, ctx.channel());
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
    }
}
