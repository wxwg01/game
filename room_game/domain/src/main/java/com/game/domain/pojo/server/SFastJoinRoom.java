package com.game.domain.pojo.server;

import java.util.List;

/**
 * 20101 : 快速加入房间返回
 */
public class SFastJoinRoom {

    private int roomid;// 房间号
    private int errorcode;// 0成功，其它为错误码
    private List<PlayerData> playerArr;// 所有玩家

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setPlayerArr(List<PlayerData> playerArr) {
        this.playerArr = playerArr;
    }

    public List<PlayerData> getPlayerArr() {
        return playerArr;
    }
}
