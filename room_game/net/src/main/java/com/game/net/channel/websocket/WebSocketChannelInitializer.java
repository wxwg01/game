/*
 * Copyright (C), 2015-2018
 * FileName: WebSocketChannelInitializer
 * Author:   wanggang
 * Date:     2018/8/10 11:42
 * Description: webSocket的channel
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.channel.websocket;

import com.game.domain.config.ServerConfig;
import com.game.domain.utils.SslUtil;
import com.game.net.channel.idle.IdleHandle;
import com.game.net.codec.IMessageToWebSocketFrameEncoder;
import com.game.net.codec.WebSocketFrameToIMessageDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;

/**
 * 〈一句话功能简述〉<br>
 * 〈webSocket的channel〉
 *
 * @author wanggang
 * @date 2018/8/10 11:42
 * @since 1.0.1
 */
@Component
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServerConfig serverConfig;

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        if (serverConfig.getSslOpen()) {
            try {
                SSLContext sslContext = null;
                // sslContext = SslUtil.createSSLContext("JKS", "D:\\workdir\\doc\\jks\\wss.jks", "netty123");
                sslContext = SslUtil
                        .createSSLContext(serverConfig.getSslType(), serverConfig.getSslPath(),
                                serverConfig.getSslPassword());
                // SSLEngine 此类允许使用ssl安全套接层协议进行安全通信
                SSLEngine engine = sslContext.createSSLEngine();
                // 服务器模式
                engine.setUseClientMode(false);
                //ssl双向认证
                engine.setNeedClientAuth(false);
                engine.setWantClientAuth(false);
                // engine.setEnabledProtocols(new String[] { "SSLv3", "TLSv1" })
                // TLSv1.2包括了SSLv3
                engine.setEnabledProtocols(new String[]{"TLSv1.2"});
                pipeline.addLast("ssl", new SslHandler(engine));

            } catch (SSLException e) {
                e.printStackTrace();
                LogManager.getLogger().debug("添加ssl出现错误", e);
            }
        }
        pipeline.addLast("idleTimer", new IdleStateHandler(serverConfig.getHeartBeatTimeout(), 0, 0));
        pipeline.addLast("idleHandle", new IdleHandle());

        pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
        pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
        // 消息编解码
        pipeline.addLast("encoder", new IMessageToWebSocketFrameEncoder());
        pipeline.addLast("decoder", new WebSocketFrameToIMessageDecoder());

        WebSocketHandler webSocketHandler = (WebSocketHandler) applicationContext.getBean("webSocketHandler");
        pipeline.addLast(webSocketHandler);

    }

}
