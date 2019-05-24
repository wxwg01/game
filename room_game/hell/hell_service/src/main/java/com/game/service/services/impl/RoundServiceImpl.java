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

import com.game.net.base.message.IMessage;
import com.game.net.base.session.Session;
import com.game.service.services.IRoundService;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈RoundService的实现类〉
 *
 * @author wanggang
 * @date 2018/7/5 9:53
 * @since 1.0.0
 */
@Service
public class RoundServiceImpl implements IRoundService {

    //@Override
    //public void doFastJoinRoom(IMessage message, Session session) {
    //    LogManager.getLogger("data").info("服务器收到的数据内容：data=" + message);
    //    int roleId = 1;
    //    RoundRunnable room = joinRoom(session, roleId);
    //
    //    SFastJoinRoom sFastJoinRoom = new SFastJoinRoom();
    //    sFastJoinRoom.setErrorcode(ErrorCodeEnum.SUC.getCode());
    //    if (Optional.ofNullable(room).isPresent()) {
    //        try {
    //            sFastJoinRoom.setRoomid(room.getRoomId());
    //            PlayerData p1 = room.getP1();
    //            PlayerData p2 = room.getP2();
    //            List<PlayerData> list = new ArrayList<>();
    //            list.add(p1);
    //            list.add(p2);
    //            sFastJoinRoom.setPlayerArr(list);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            sFastJoinRoom.setErrorcode(ErrorCodeEnum.JOIN_FAILED.getCode());
    //        }
    //        TaskExecutePool.getInstance().getRunRoom().execute(room);
    //        System.out.println("run room suc");
    //    } else {
    //        // 如果房间没有 则报错
    //        sFastJoinRoom.setErrorcode(ErrorCodeEnum.JOIN_FAILED.getCode());
    //    }
    //
    //    try {
    //        System.out.println(JSONObject.toJSONString(sFastJoinRoom));
    //        SessionManager.getInstance().sendMessage(session, sFastJoinRoom);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    SStartRound startRound = new SStartRound();
    //    startRound.setRound((short) CommonValue.rounds);
    //    startRound.setTimeOut(CommonValue.roundWait);
    //    try {
    //        SessionManager.getInstance().sendMessage(session, startRound);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}

    @Override
    public void doRound(IMessage message, Session session) {
    }

    @Override
    public void doOffline(IMessage message, Session session) {

    }

    @Override
    public void doExit(IMessage message, Session session) {

    }


    //@Async("joinRoomPool")
    //public RoundRunnable joinRoom(Session session, Integer roleId) {
    //    RoundRunnable room = RoomManager.getInstance().joinRoom(session.getPlayerData().getUuid(), roleId, CommonValue.ROOM_TYPE_NOMALE, CommonValue.rounds);
    //    if (Optional.ofNullable(room).isPresent()) {
    //        long createTime = room.getCreateTime();
    //        if (createTime == 0) {
    //            createTime = System.currentTimeMillis();
    //            room.setCreateTime(createTime);
    //        }
    //        if (System.currentTimeMillis() - createTime > CommonValue.joinRoomTime * 1000) {
    //            return null;
    //        }
    //    } else {
    //        return null;
    //    }
    //
    //    if (Optional.ofNullable(room.getP1()).isPresent() && Optional.ofNullable(room.getP2()).isPresent()) {
    //        return room;
    //    } else {
    //        try {
    //            Thread.sleep(1000);
    //        } catch (InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //        return joinRoom(session, roleId);
    //    }
    //}

}
