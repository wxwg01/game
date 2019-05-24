package com.game.service.services;

import com.game.net.base.message.IMessage;
import com.game.net.base.session.Session;

/**
 * 客户端简单事件
 * @author WG
 */
public interface IRoundService {

    /**
     * 客户端提交回合数据
     *
     * @param message
     * @param session
     */
    void doRound(IMessage message, Session session);

    /**
     * 掉线
     *
     * @param message
     * @param session
     */
    void doOffline(IMessage message, Session session);

    /**
     * 退出
     *
     * @param message
     * @param session
     */
    void doExit(IMessage message, Session session);
}
