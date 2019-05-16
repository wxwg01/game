package com.game.service.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.game.domain.enums.ErrorCodeEnum;
import com.game.domain.pojo.client.CCreateRole;
import com.game.domain.pojo.client.CLogin;
import com.game.domain.pojo.server.BattleData;
import com.game.domain.pojo.server.PlayerData;
import com.game.domain.pojo.server.SCreateRole;
import com.game.domain.pojo.server.ServerLoginVO;
import com.game.netBase.message.IMessage;
import com.game.netBase.session.Session;
import com.game.netBase.session.SessionManager;
import com.game.service.services.IUserService;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @email : wb-wg471966@alibaba-inc.com
 * @date : 2019/4/29
 */
@Service
public class UserServiceImpl implements IUserService{
    @Override
    public void doLogin(IMessage message, Session session) {
        CLogin cLogin = (CLogin) message.getParam();
        LogManager.getLogger("data").info("服务器收到的数据内容：data=" + message + " object=" + JSONObject.toJSONString(cLogin));

        ServerLoginVO sLogin = new ServerLoginVO();
        sLogin.setUuid(UUID.randomUUID().toString());

        // 查询用户

        try {
            System.out.println(JSONObject.toJSONString(sLogin));
            SessionManager.getInstance().sendMessage(session, sLogin);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doCreate(IMessage message, Session session) {
        CCreateRole cCreateRole = (CCreateRole) message.getParam();
        LogManager.getLogger("data").info("服务器收到的数据内容：data=" + message + " object=" + JSONObject.toJSONString(cCreateRole));
        // 重复登录问题

        // 注册用户
        try {
            //SessionManager.getInstance().sendMessage(session, sCreateRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doCreateRoom(IMessage message, Session session) {

    }

    @Override
    public void doRomeList(IMessage message, Session session) {

    }

    @Override
    public void doMatching(IMessage message, Session session) {

    }

    @Override
    public void doInviting(IMessage message, Session session) {

    }

    @Override
    public void doInvitingAccept(IMessage message, Session session) {

    }

    @Override
    public void doTeamMatchimg(IMessage message, Session session) {

    }
}
