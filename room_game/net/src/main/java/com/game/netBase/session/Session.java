/*
 * Copyright (C), 2015-2018
 * FileName: Session
 * Author:   wanggang
 * Date:     2018/6/22 15:44
 * Description: Session会话
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.netBase.session;

import com.game.domain.pojo.server.PlayerData;
import com.game.domain.utils.TimeUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈Session会话，包括管道及用户信息〉
 * 里面的所有信息都需要交由SessionManager操作
 * 外部无法直接访问到这个类的方法
 *
 * @author wanggang
 * @date 2018/6/22 15:44
 * @since 1.0.0
 */
public class Session {

    private static final Logger logger = LoggerFactory.getLogger(Session.class);

    private String protocolType;
    /**
     * 与客户端的管道
     */
    private final Channel channel;
    /**
     * 用户信息
     * 用户信息可能为空。
     * 只有在register(登录)之后，里面才会赋值
     */
    private PlayerData playerData;

//  private int roomId;
    /**
     * 创建时间
     */
    private final long createTime;

    private final HashMap<String, Object> hashMap;

    public Session(Channel channel) {
        this.channel = channel;
        this.createTime = TimeUtil.getSysCurTimeMillis();
        hashMap = new HashMap<>();
    }

    /**
     * 玩session里面写入user，一般是在登录之后调用a
     *
     * @param playerData 用户 信息
     */
    void registerUser(PlayerData playerData) {
        this.playerData = playerData;
    }

//  void registerRoom(int roomId) {
//    this.roomId = roomId;
//  }

    /**
     * 关闭与客户端的连接
     */
    void close() {
        if (channel == null) {
            return;
        }
        try {
            if (channel.isActive() || channel.isOpen()) {
                channel.close().sync();
            }
        } catch (InterruptedException e) {
            logger.error("channel.close find error ", e);
        }

    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

//  public int getRoom() {
//    return roomId;
//  }

    Channel getChannel() {
        return channel;
    }

    void put(String key, Object value) {
        hashMap.put(key, value);
    }

    Object getByKey(String key) {
        return hashMap.getOrDefault(key, null);
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }
}
