package com.game.service.services;


import com.game.netBase.message.IMessage;
import com.game.netBase.session.Session;

public interface IRoundService {


    /**
     * 快速加入方法
     *
     * @param message
     * @param session
     */
    void doFastJoinRoom(IMessage message, Session session);

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
     * 退出回合
     *
     * @param message
     * @param session
     */
    void doExitRound(IMessage message, Session session);
}
