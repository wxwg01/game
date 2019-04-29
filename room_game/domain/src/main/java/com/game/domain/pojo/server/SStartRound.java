package com.game.domain.pojo.server;

/**
 * 20102 : 回合开始
 */
public class SStartRound {

    private short round;// 回合数
    private int timeOut;// 倒计时

    public void setRound(short round) {
        this.round = round;
    }

    public short getRound() {
        return round;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getTimeOut() {
        return timeOut;
    }
}
