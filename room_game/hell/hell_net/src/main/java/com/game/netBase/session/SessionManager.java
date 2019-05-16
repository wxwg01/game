/*
 * Copyright (C), 2015-2018
 * FileName: SessionManager
 * Author:   wanggang
 * Date:     2018/6/22 16:40
 * Description: Session管理类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.netBase.session;

import com.game.domain.pojo.server.PlayerData;
import com.game.domain.utils.BytebufUtil;
import com.game.netBase.message.IMessage;
import com.game.netBase.message.impl.StringMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈Session管理类〉
 *
 * @author wanggang
 * @date 2018/6/22 16:40
 * @since 1.0.0
 */
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final SessionManager instance = new SessionManager();

    public static SessionManager getInstance() {
        return instance;
    }

    private SessionManager() {
        logger.info("SessionManager init success");
    }

    /**
     * 已经登录的session管理
     */
    private final ConcurrentMap<String, Session> uidSessionMap = new ConcurrentHashMap<>(16);

    public Session getSession(String uid) {
        return uidSessionMap.get(uid);
    }

    /**
     * 创建session
     *
     * @param channel 与客户端连接的管道
     * @return session
     */
    public Session create(Channel channel) {
        Session session = getSessionByChannel(channel);
        if (session == null) {
            session = new Session(channel);
            AttributeUtil.set(channel, SessionAttributeKey.SESSION, session);
            logger.info("session 创建成功");
        } else {
            logger.error("新连接建立时已存在Session，注意排查原因 " + channel.toString());
        }
        return session;
    }

    /**
     * 注册sesson
     *
     * @param session    session
     * @param playerData 用户
     */
    public void register(Session session, PlayerData playerData) {
        session.registerUser(playerData);
        uidSessionMap.put(playerData.getUuid(), session);
    }

    /**
     * 通过channel关闭session
     *
     * @param channel 与客户端连接的管道
     */
    public void close(Channel channel) {
        Session session = getSessionByChannel(channel);
        if (session != null) {
            close(session);
        }
    }

    /**
     * 关闭session
     *
     * @param session 要关闭的session
     */
    private void close(Session session) {
        unregister(session);
        session.close();
        logger.info("session close success");
    }

    /**
     * 将之前注册好的session从map中移除
     *
     * @param session 将之前注册好的session从map中移除
     */
    private void unregister(Session session) {
        if (session != null) {
            AttributeUtil.set(session.getChannel(), SessionAttributeKey.SESSION, null);

            PlayerData playerData = session.getPlayerData();
            if (playerData != null) {
                boolean remove = uidSessionMap.remove(playerData.getUuid(), session);
                logger.info("Session unregister, userId={}, remove={}", playerData.getUuid(), remove);

        /*uidSessionMap.remove(session.getRoomId());
        logger.info("Session unregister, userId={}, remove={}", session.getRoomId(), remove);*/
            }
        }
    }


    public Session getSessionByChannel(Channel channel) {
        return AttributeUtil.get(channel, SessionAttributeKey.SESSION);
    }

    /**
     * 获取session的数组
     *
     * @return session的数组
     */
    public Session[] getSessionArray() {
        Collection<Session> values = uidSessionMap.values();
        return values.toArray(new Session[values.size()]);
    }

    public void putMapValue(Channel channel, String key, Object value) {
        AttributeUtil.get(channel, SessionAttributeKey.SESSION).put(key, value);
    }

    public Object getMapValueByKey(Channel channel, String key) {
        return AttributeUtil.get(channel, SessionAttributeKey.SESSION).getByKey(key);
    }

    public void sendMessage(Channel channel, String msg) {
        sendMessage(getSessionByChannel(channel), msg);
    }

    public void sendMessage(Session session, String msg) {
        TextWebSocketFrame tws = new TextWebSocketFrame(msg);
        session.getChannel().writeAndFlush(tws);
    }

    public void sendMessage(Session session, IMessage iMessage) {
        session.getChannel().writeAndFlush(iMessage);
    }

    public void sendMessage(Session session, ByteBuf byteBuf) {
//    session.getChannel().writeAndFlush(byteBuf);
        BinaryWebSocketFrame webSocketFrame = new BinaryWebSocketFrame(byteBuf);
        session.getChannel().writeAndFlush(webSocketFrame);
    }


    public void sendMessage(Session session, Object msg) throws Exception {
        String resultStr = BytebufUtil.getStrMsg(msg);
        StringMessage stringMessage = new StringMessage();
        stringMessage.setBody(resultStr);
//  ByteBuf buf = BytebufUtil.objectToByteBuf(msg);
//  BytebufUtil.hexLog(buf,buf.readerIndex());
   /*   System.out.println(resultStr);
    TextWebSocketFrame tws = new TextWebSocketFrame(resultStr);*/
        session.getChannel().writeAndFlush(stringMessage);
    }
}
