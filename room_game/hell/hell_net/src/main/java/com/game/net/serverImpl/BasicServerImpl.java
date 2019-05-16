/*
 * Copyright (C), 2015-2018
 * FileName: BasicServerImpl
 * Author:   wanggang
 * Date:     2018/6/20 20:23
 * Description: BasicServerImpl
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.serverImpl;

import com.game.domain.config.ServerConfig;
import com.game.domain.constant.ConstantValue;
import com.game.domain.exception.ServerErrException;
import com.game.netBase.IServer;
import com.game.netBase.factory.ServerChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈BasicServerImpl〉
 *
 * @author wanggang
 * @date 2018/6/20 20:23
 * @since 1.0.0
 */
@Component("basicServerImpl")
@Scope("singleton")
public class BasicServerImpl implements IServer {
    private Channel acceptorChannel;
    private static final Logger logger = LoggerFactory.getLogger(BasicServerImpl.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServerConfig serverConfig;

    @Override
    public void start() {
        try {
            init();
            acceptorChannel.closeFuture().sync();
//      acceptorChannel.closeFuture().await();
        } catch (ServerErrException | InterruptedException e) {
            e.printStackTrace();
            logger.debug("服务启动失败，程序即将退出", e);
            stop();
            System.exit(0);
        }
    }

    private void init() throws ServerErrException {
        // 启动通讯服务
        logger.info("启动通讯服务  开始");
        Integer port = serverConfig.getPort();
        String channelType = serverConfig.getChannelType();
        String protocolType = serverConfig.getProtocolType();
        ChannelInitializer<SocketChannel> channelInitializer = null;
        switch (protocolType) {
            case ConstantValue.PROTOCOL_TYPE_TCP:
                channelInitializer = (ChannelInitializer<SocketChannel>) applicationContext
                        .getBean("tcpServerStringInitializer");
                break;
            case ConstantValue.PROTOCOL_TYPE_WEBSOCKET:
                channelInitializer = (ChannelInitializer<SocketChannel>) applicationContext
                        .getBean("webSocketChannelInitializer");
                break;
            default:
                throw new ServerErrException("ChannelInitializer is not defind");
        }
        acceptorChannel = ServerChannelFactory.createAcceptorChannel(port, channelType, channelInitializer);
        logger.info("启动通讯服务  结束");

    }

    @Override
    public void stop() {
        if (acceptorChannel != null) {
            acceptorChannel.close().addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void restart() throws Exception {
        stop();
        start();
    }
}
