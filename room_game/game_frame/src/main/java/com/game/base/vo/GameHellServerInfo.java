package com.game.base.vo;

import akka.actor.ActorRef;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 *
 * @date : 2019/5/15
 */
public class GameHellServerInfo implements Serializable {
    private static final long serialVersionUID = 7278085517726532333L;

    /**
     * 服务器名称
     */
    private String serviceName;
    /**
     * 服务器类型
     */
    private String serverType;
    /**
     * 服务器netty ip
     */
    private String serviceIP;
    /**
     * 服务器netty 端口
     */
    private String servicePort;
    /**
     * 服务器 状态
     */
    private String serviceState;
    private ActorRef actorRef;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getServiceIP() {
        return serviceIP;
    }

    public void setServiceIP(String serviceIP) {
        this.serviceIP = serviceIP;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public String getServiceState() {
        return serviceState;
    }

    public void setServiceState(String serviceState) {
        this.serviceState = serviceState;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }

    public void setActorRef(ActorRef actorRef) {
        this.actorRef = actorRef;
    }
}
