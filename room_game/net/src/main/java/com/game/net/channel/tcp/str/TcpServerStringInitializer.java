/*
 * Copyright (C), 2015-2018
 * FileName: TcpServerStringInitializer
 * Author:   wanggang
 * Date:     2018/6/12 15:00
 * Description: TcpServerInitializer请求处理器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.channel.tcp.str;

import com.game.domain.config.ServerConfig;
import com.game.domain.constant.ConstantValue;
import com.game.net.codec.MessageDecoder;
import com.game.net.codec.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈TcpServerInitializer请求处理器〉
 *
 * @author wanggang
 * @date 2018/6/12 15:00
 * @since 1.0.0
 */
@Component
public class TcpServerStringInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServerConfig serverConfig;

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("encoder", new MessageEncoder());
        pipeline.addLast("decoder", new MessageDecoder(ConstantValue.MESSAGE_CODEC_MAX_FRAME_LENGTH,
                ConstantValue.MESSAGE_CODEC_LENGTH_FIELD_LENGTH, ConstantValue.MESSAGE_CODEC_LENGTH_FIELD_OFFSET,
                ConstantValue.MESSAGE_CODEC_LENGTH_ADJUSTMENT, ConstantValue.MESSAGE_CODEC_INITIAL_BYTES_TO_STRIP, false,
                serverConfig.getMessageType()));

        TcpMessageStringHandler tcpMessageStringHandler = (TcpMessageStringHandler) applicationContext.getBean("tcpMessageStringHandler");
        pipeline.addLast(tcpMessageStringHandler);

    }

}
