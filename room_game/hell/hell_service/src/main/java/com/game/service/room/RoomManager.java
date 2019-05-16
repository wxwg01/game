package com.game.service.room;

import com.alibaba.fastjson.JSONObject;
import com.game.domain.enums.CommonValue;
import com.game.domain.pojo.server.PlayerData;
import com.game.domain.utils.RoundUtil;
import com.game.netBase.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/18 10:34
 * @Since 0.0.1
 */
public class RoomManager {
    private static final Logger logger = LoggerFactory.getLogger(RoomManager.class);
    private static final RoomManager instance = new RoomManager();

    public static RoomManager getInstance() {
        return instance;
    }

    private RoomManager() {
        logger.info("SessionManager init success");
    }


//    private final ConcurrentMap<Integer, Room> roomMap = new ConcurrentHashMap<>(16);

    private static final Map<Byte, ConcurrentMap<Integer, RoundRunnable>> ROOM = new HashMap<>(16);

    private static final Map<String, RoundRunnable> UID_ROOM = new ConcurrentHashMap<>(16);


    public RoundRunnable addRoom(Byte type, Integer roleId, PlayerData playerData) {
        int intUnbounded;
        RoundRunnable room = new RoundRunnable(CommonValue.rounds, type);

        ConcurrentMap<Integer, RoundRunnable> roomMap = getRoomMap(type);

        while (!roomMap.containsKey(intUnbounded = new Random().nextInt())) {
            room.setRoomId(intUnbounded);
            break;
        }
        room.setP1(playerData);
        room.setR1(RoundUtil.getRole(roleId));
        room.setCreateTime(System.currentTimeMillis());

        roomMap.put(room.getRoomId(), room);

        return room;
    }

    public static ConcurrentMap<Integer, RoundRunnable> getRoomMap(byte type) {
        return ROOM.computeIfAbsent(type, k -> new ConcurrentHashMap<>());
    }


    public RoundRunnable getRoomByUid(String uid) {
        RoundRunnable room = UID_ROOM.get(uid);

        System.out.println(JSONObject.toJSONString(room));

        if (Optional.ofNullable(room).isPresent()) {
            if (!Optional.ofNullable(room.getP1()).isPresent() && !Optional.ofNullable(room.getP2()).isPresent()) {
                room.setP1(SessionManager.getInstance().getSession(uid).getPlayerData());
                getRoomMap(room.getRoomType()).put(room.getRoomId(), room);
            }
            return room;
        }
        return null;
    }

    public RoundRunnable getRoomByRoomId(int roomId, byte roomType) {
        return getRoomMap(roomType).get(roomId);
    }


    /**
     * 简单匹配
     * 1.有房间则返回房间
     * 2.获取缺人的房间列表 随机取一个方法
     * 3.没有缺人的方法，自己建一个
     *
     * @param uid
     * @return
     */
    public synchronized RoundRunnable joinRoom(String uid, int roleId, byte roomType, int times) {
        RoundRunnable room = UID_ROOM.get(uid);

        System.out.println(JSONObject.toJSONString(room));

        if (Optional.ofNullable(room).isPresent()) {
            if (!Optional.ofNullable(room.getP1()).isPresent() && !Optional.ofNullable(room.getP2()).isPresent()) {
                room.setP1(SessionManager.getInstance().getSession(uid).getPlayerData());
                getRoomMap(roomType).put(room.getRoomId(), room);
            }
            return room;
        } else {
            UID_ROOM.remove(uid);
        }

        PlayerData playerData = SessionManager.getInstance().getSession(uid).getPlayerData();

        List<RoundRunnable> rooms = getRoomMap(roomType).entrySet().stream()
                .filter(it -> !Optional.ofNullable(it.getValue().getP1()).isPresent() || !Optional.ofNullable(it.getValue().getP2()).isPresent())
                .filter(it -> joinRoomCheck(playerData, it.getValue(), roomType, times))
                .map(it -> it.getValue())
                .collect(Collectors.toList());

        if (Optional.ofNullable(rooms).isPresent() && rooms.size() > 0) {
            int size = (int) (Math.random() * rooms.size());
            System.out.println(size);
            room = rooms.get(size);
            if (!Optional.ofNullable(room.getP1()).isPresent()) {
                room.setP1(SessionManager.getInstance().getSession(uid).getPlayerData());
                room.setR1(RoundUtil.getRole(roleId));
            } else {
                room.setP2(SessionManager.getInstance().getSession(uid).getPlayerData());
                room.setR2(RoundUtil.getRole(roleId));
            }

            room.setPreRoundTime(System.currentTimeMillis());

            getRoomMap(roomType).put(room.getRoomId(), room);
            UID_ROOM.put(uid, room);
            return room;
        } else {
            room = addRoom(roomType, roleId, playerData);
            UID_ROOM.put(uid, room);
            return room;
        }
    }

    public boolean joinRoomCheck(PlayerData playerData, RoundRunnable room, byte roomType, int times) {
        if (times < 1) times = 1;
        if (roomType == CommonValue.ROOM_TYPE_NOMALE) {
            //普通战按等级匹配，匹配范围=自身等级±3*（匹配次数）
            PlayerData p = Optional.ofNullable(room.getP1()).isPresent() ? room.getP1() : room.getP2();
            if (Math.abs(playerData.getLevel() - p.getLevel()) <= 3 * times) {
                return true;
            }
            return false;
        } else if (roomType == CommonValue.ROOM_TYPE_PAIWEI) {
            //排位战按段位和积分匹配，在自己同段位内筛选，积分±10*（匹配次数）
            return false;
        }

        return true;
    }

    public void roundEnd(RoundRunnable room) {
        if (Optional.ofNullable(room).isPresent()) {
            try {
                String uuid1 = room.getP1().getUuid();
                UID_ROOM.remove(uuid1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String uuid2 = room.getP2().getUuid();
                UID_ROOM.remove(uuid2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getRoomMap(room.getRoomType()).remove(room.getRoomId());
        }

    }

}
