package com.game.net.channel.idle;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/12 14:12
 * @Since 0.0.1
 */
public class IdleHandle extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        //netty自带的心跳校验
        if (evt instanceof IdleStateEvent) {
            IdleState e = ((IdleStateEvent) evt).state();
            if (e == IdleState.READER_IDLE) {
                System.out.println("超时:" + ctx.channel());
//                SessionManager.getInstance().close(ctx.channel());
            }
        }
    }
}
