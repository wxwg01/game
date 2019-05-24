/*
 * Copyright (C), 2015-2018
 * FileName: TestFirstHandler
 * Author:   wanggang
 * Date:     2018/6/25 16:24
 * Description: 用于测试的第一个handler
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.server.handler;

import com.game.net.base.concurrent.handler.AbstractHandler;
import com.game.net.base.message.IMessage;
import com.game.net.base.session.Session;
import com.game.service.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈用于测试的第一个handler〉
 *
 * @author wanggang
 * @date 2018/6/25 16:24
 * @since 1.0.0
 */
@Service("clientMatchingHandler")
public class ClientMatchingHandler extends AbstractHandler<IMessage, Session> {
    @Autowired
    private IUserService userService;

    @Override
    public void doAction() {
        userService.doMatching(message, param);
    }
}
