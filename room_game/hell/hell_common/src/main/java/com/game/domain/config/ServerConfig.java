/*
 * Copyright (C), 2015-2018
 * FileName: ServerConfig
 * Author:   wanggang
 * Date:     2018/6/12 11:16
 * Description: 服务的配置内容
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.domain.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @author : wanggang
 * @description : 服务的配置内容
 * @date : 2019/4/29 10:28
 */
@Configuration
@Service
public class ServerConfig {
    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    @Value("${port}")
    private Integer port;
    @Value("${channelType}")
    private String channelType;
    @Value("${protocolType}")
    private String protocolType;
    @Value("${messageType}")
    private String messageType;

    @Value("${sslOpen}")
    private Boolean sslOpen;

    @Value("${sslType}")
    private String sslType;
    @Value("${sslPath}")
    private String sslPath;
    @Value("${sslPassword}")
    private String sslPassword;

    @Value("${heartBeatTimeout}")
    private int heartBeatTimeout;

    @Value("${joinRoomTime}")
    private int joinRoomTime;

    @Value("${roundMxTime}")
    private int roundMxTime;

    @Value("${rounds}")
    private int rounds;

    @Value("${rolePath}")
    private String rolePath;

    @Value("${levelPath}")
    private String levelPath;

    @Value("${skillPath}")
    private String skillPath;

    public Integer getPort() {
        return port;
    }

    public String getChannelType() {
        return channelType;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public String getMessageType() {
        return messageType;
    }

    public Boolean getSslOpen() {
        return sslOpen;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String getSslType() {
        return sslType;
    }

    public String getSslPath() {
        return sslPath;
    }

    public String getSslPassword() {
        return sslPassword;
    }

    public int getHeartBeatTimeout() {
        return heartBeatTimeout;
    }

    public int getJoinRoomTime() {
        return joinRoomTime;
    }

    public int getRoundMxTime() {
        return roundMxTime;
    }

    public int getRounds() {
        return rounds;
    }

    public String getRolePath() {
        return rolePath;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public String getSkillPath() {
        return skillPath;
    }
}
