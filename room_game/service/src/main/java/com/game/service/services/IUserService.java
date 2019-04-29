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
    void doCreateRole(IMessage message, Session session);


}
