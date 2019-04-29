/*
 * Copyright (C), 2015-2018
 * FileName: UserServiceImpl
 * Author:   wanggang
 * Date:     2018/7/5 9:53
 * Description: UserService的实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.service.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.game.domain.enums.ErrorCodeEnum;
import com.game.domain.pojo.client.CCreateRole;
import com.game.domain.pojo.client.CLogin;
import com.game.domain.pojo.server.BattleData;
import com.game.domain.pojo.server.PlayerData;
import com.game.domain.pojo.server.SCreateRole;
import com.game.domain.pojo.server.SLogin;
import com.game.netBase.message.IMessage;
import com.game.netBase.session.Session;
import com.game.netBase.session.SessionManager;
import com.game.service.services.IUserService;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈UserService的实现类〉
 *
 * @author wanggang
 * @date 2018/7/5 9:53
 * @since 1.0.0
 */
@Service
public class UserServic implements IUserService {

    public void doTestByte(IMessage message, Session session) {

    /*LogManager.getLogger("data").info("服务器收到的doTestByte 数据内容：data=" + message);
    ByteMessage byteMessage = new ByteMessage();
    byteMessage.setMessageId(CommonValue.CM_MSG_CREATE_ROLE);
    byteMessage.setStatusCode(CommonValue.MSG_STATUS_CODE_SUCCESS);
    byteMessage.addAttr(2);
    SessionManager.getInstance().sendMessage(session, byteMessage);

    EventUtil.fireEvent(CommonValue.EVENT_TYPE_LOGIN);*/
    }

    @Override
    public void doLogin(IMessage message, Session session) {

        CLogin cLogin = (CLogin) message.getParam();
        LogManager.getLogger("data").info("服务器收到的数据内容：data=" + message + " object=" + JSONObject.toJSONString(cLogin));

        SLogin sLogin = new SLogin();
        sLogin.setUuid(UUID.randomUUID().toString());

        // 查询数据库，查看有无账号
        PlayerData user = new PlayerData();
        if (true) {
            user.setUuid(sLogin.getUuid());
            user.setName("test");
            user.setExp(1000000000L);
            user.setLevel((short) 100);
            user.setSex((byte) 1);
            user.setGold(100000L);
            user.setMonmey(1000);

            BattleData round = new BattleData();
            round.setUuid(user.getUuid());
            round.setBattlenum(10);
            round.setAttacknum(11);
            round.setBlocknum(12);
            round.setWinnum(13);
            round.setXulinum(14);

            user.setBattleData(round);
            sLogin.setPlayer(user);
        } else {
            user.setUuid(sLogin.getUuid());
            sLogin.setPlayer(user);
        }

        try {
            System.out.println(JSONObject.toJSONString(sLogin));
            SessionManager.getInstance().sendMessage(session, sLogin);

            SessionManager.getInstance().register(session, user);


//      ByteBuf result = BytebufUtil.doReturnByteBuf(list);
//      BytebufUtil.hexLog(result,result.readerIndex());
//      SessionManager.getInstance().sendMessage(session, result);
//      SessionManager.getInstance().sendMessage(session, result);
//      String resultStr = BytebufUtil.doResultString(result);
//      System.out.println(resultStr);
//      SessionManager.getInstance().sendMessage(session, resultStr);
//      SessionManager.getInstance().sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doCreateRole(IMessage message, Session session) {
        CCreateRole cCreateRole = (CCreateRole) message.getParam();
        LogManager.getLogger("data").info("服务器收到的数据内容：data=" + message + " object=" + JSONObject.toJSONString(cCreateRole));
        // 重复登录问题

        PlayerData playerData = session.getPlayerData();
        SCreateRole sCreateRole = new SCreateRole();
        sCreateRole.setErrorcode(ErrorCodeEnum.SUC.getCode());

        playerData.setSex(cCreateRole.getSex());
        playerData.setName(cCreateRole.getName());

        if (!Optional.ofNullable(playerData).isPresent()) {
            sCreateRole.setErrorcode(ErrorCodeEnum.NO_ACCOUNT.getCode());
        } else if (!Optional.ofNullable(playerData.getName()).isPresent()) {
            sCreateRole.setErrorcode(ErrorCodeEnum.CREATRE_ROLE_FAILED.getCode());
        } else {
            // 获取是否有角色

            // 获取是否有战绩

            BattleData round = new BattleData();
            round.setUuid(playerData.getUuid());
            round.setBattlenum(10);
            round.setAttacknum(11);
            round.setBlocknum(12);
            round.setWinnum(13);
            round.setXulinum(14);
            playerData.setBattleData(round);

            SessionManager.getInstance().register(session, playerData);
        }
        try {
            SessionManager.getInstance().sendMessage(session, sCreateRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
