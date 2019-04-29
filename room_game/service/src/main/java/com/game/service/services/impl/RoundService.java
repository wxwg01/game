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
import com.game.domain.enums.CommonValue;
import com.game.domain.enums.ErrorCodeEnum;
import com.game.domain.pojo.RoundData;
import com.game.domain.pojo.client.CRound;
import com.game.domain.pojo.server.PlayerData;
import com.game.domain.pojo.server.SFastJoinRoom;
import com.game.domain.pojo.server.SStartRound;
import com.game.netBase.message.IMessage;
import com.game.netBase.session.Session;
import com.game.netBase.session.SessionManager;
import com.game.service.room.RoomManager;
import com.game.service.room.RoundRunnable;
import com.game.service.services.IRoundService;
import com.game.service.threadPool.TaskExecutePool;
import org.apache.logging.log4j.LogManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈RoundService的实现类〉
 *
 * @author wanggang
 * @date 2018/7/5 9:53
 * @since 1.0.0
 */
@Service
public class RoundService implements IRoundService {

    @Override
    public void doFastJoinRoom(IMessage message, Session session) {
        LogManager.getLogger("data").info("服务器收到的数据内容：data=" + message);
        int roleId = 1;
        RoundRunnable room = joinRoom(session, roleId);

        SFastJoinRoom sFastJoinRoom = new SFastJoinRoom();
        sFastJoinRoom.setErrorcode(ErrorCodeEnum.SUC.getCode());
        if (Optional.ofNullable(room).isPresent()) {
            try {
                sFastJoinRoom.setRoomid(room.getRoomId());
                PlayerData p1 = room.getP1();
                PlayerData p2 = room.getP2();
                List<PlayerData> list = new ArrayList<>();
                list.add(p1);
                list.add(p2);
                sFastJoinRoom.setPlayerArr(list);
            } catch (Exception e) {
                e.printStackTrace();
                sFastJoinRoom.setErrorcode(ErrorCodeEnum.JOIN_FAILED.getCode());
            }
            TaskExecutePool.getInstance().getRunRoom().execute(room);
            System.out.println("run room suc");
        } else {
            // 如果房间没有 则报错
            sFastJoinRoom.setErrorcode(ErrorCodeEnum.JOIN_FAILED.getCode());
        }

        try {
            System.out.println(JSONObject.toJSONString(sFastJoinRoom));
            SessionManager.getInstance().sendMessage(session, sFastJoinRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SStartRound startRound = new SStartRound();
        startRound.setRound((short) CommonValue.rounds);
        startRound.setTimeOut(CommonValue.roundWait);
        try {
            SessionManager.getInstance().sendMessage(session, startRound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doRound(IMessage message, Session session) {

        CRound cRound = (CRound) message.getParam();
        LogManager.getLogger("data").info("服务器收到的数据内容：data=" + message + " object=" + JSONObject.toJSONString(cRound));

        RoundRunnable room = RoomManager.getInstance().getRoomByUid(session.getPlayerData().getUuid());

        if (Optional.ofNullable(room).isPresent() /*&& Optional.ofNullable(room.getP1()).isPresent() && Optional.ofNullable(room.getP2()).isPresent()*/) {
            RoundData roundData1 = null;
            RoundData roundData2 = null;
            if (Optional.ofNullable(room.getP1()).get().getUuid().equals(session.getPlayerData().getUuid())) {
                roundData1 = room.getRoundDataListP1().get(room.getNum());
                if (roundData1 == null) {
                    roundData1 = new RoundData();
                    if (cRound.getType() == 1) {
                        roundData1.left = cRound;
                    } else {
                        roundData1.right = cRound;
                    }
                } else if (roundData1.left == null && cRound.getType() == 1) {
                    roundData1.left = cRound;
                } else if (roundData1.right == null && cRound.getType() == 2) {
                    roundData1.right = cRound;
                }
                room.getRoundDataListP1().set(room.getNum(), roundData1);
            } else if (Optional.ofNullable(room.getP2()).get().equals(session.getPlayerData().getUuid())) {
                roundData2 = room.getRoundDataListP2().get(room.getNum());
                if (roundData2 == null) {
                    roundData2 = new RoundData();
                    if (cRound.getType() == 1) {
                        roundData2.left = cRound;
                    } else {
                        roundData2.right = cRound;
                    }
                } else if (roundData2.left == null && cRound.getType() == 1) {
                    roundData2.left = cRound;
                } else if (roundData2.right == null && cRound.getType() == 2) {
                    roundData2.right = cRound;
                }
                room.getRoundDataListP2().set(room.getNum(), roundData2);
            }
           /* if ((Optional.ofNullable(roundData1.left).isPresent() && Optional.ofNullable(roundData1.right).isPresent() &&
                    Optional.ofNullable(roundData2.left).isPresent() && Optional.ofNullable(roundData2.right).isPresent()) || (System.currentTimeMillis()-room.getCreateTime())>CommonValue.roundWait*1000) {
                // 双方出招完毕
                // 进行计算
                SRoundResult roundResult = new SRoundResult();

                try {
                    SessionManager.getInstance().sendMessage(session, roundResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 触发 等级 经验 金钱变化
//                EventUtil.fireEvent(SEnumValue.SROUNDRESULT.getCode(),roundResult);


                // 返回当前回合数据
                // 有结果后 清空回合数据
                if (!RoomManager.getInstance().cleanRoundData(room)) {
                    EventUtil.fireEvent(SEnumValue.SROUNDEND.getCode(),roundResult);
                }
            }*/
        }
    }

    @Override
    public void doOffline(IMessage message, Session session) {

    }

    @Override
    public void doExitRound(IMessage message, Session session) {

    }

    @Async("joinRoomPool")
    public RoundRunnable joinRoom(Session session, Integer roleId) {
        RoundRunnable room = RoomManager.getInstance().joinRoom(session.getPlayerData().getUuid(), roleId, CommonValue.ROOM_TYPE_NOMALE, CommonValue.rounds);
        if (Optional.ofNullable(room).isPresent()) {
            long createTime = room.getCreateTime();
            if (createTime == 0) {
                createTime = System.currentTimeMillis();
                room.setCreateTime(createTime);
            }
            if (System.currentTimeMillis() - createTime > CommonValue.joinRoomTime * 1000) {
                return null;
            }
        } else return null;

        if (Optional.ofNullable(room.getP1()).isPresent() && Optional.ofNullable(room.getP2()).isPresent()) {
            return room;
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return joinRoom(session, roleId);
        }
    }

}
