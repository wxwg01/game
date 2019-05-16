/*
 * Copyright (C), 2015-2018
 * FileName: UserService
 * Author:   wanggang
 * Date:     2018/7/5 9:52
 * Description: 用户管理的业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.service.services;

import com.game.netBase.message.IMessage;
import com.game.netBase.session.Session;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户管理的业务层〉
 *
 * @author wanggang
 * @date 2018/7/5 9:52
 * @since 1.0.0
 */
public interface IUserService {
    /**
     * 登录
     *
     * @param message
     * @param session
     */
    void doLogin(IMessage message, Session session);

    /**
     * 注册用户
     *
     * @param message
     * @param session
     */
    void doCreate(IMessage message, Session session);


    /**
     * 创建房间
     * @param message
     * @param session
     */
    void doCreateRoom(IMessage message,Session session);

    /**
     * 房间列表
     * @param message
     * @param session
     */
    void doRomeList(IMessage message,Session session);

    /**
     * 快速加入方法
     *
     * @param message
     * @param session
     */
    void doMatching(IMessage message, Session session);

    /**
     * 邀请
     * @param message
     * @param session
     */
    void doInviting(IMessage message,Session session);

    /**
     * 同意邀请
     * @param message
     * @param session
     */
    void doInvitingAccept(IMessage message,Session session);

    /**
     * 队伍匹配
     * @param message
     * @param session
     */
    void doTeamMatchimg(IMessage message,Session session);


}
