package com.game.base.service.vo;

import akka.actor.ActorRef;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @email : wb-wg471966@alibaba-inc.com
 * @date : 2019/5/15
 */
@Data
public class GameHellServerInfo implements Serializable {
    private static final long serialVersionUID = 7278085517726532333L;

    private String serviceName;
    private String serverType;
    private String serviceIP;
    private String servicePort;
    private String serviceState;
    private ActorRef actorRef;
}
