package com.game.domain.pojo.server;

import java.util.List;

/**
 * 20103 : 回合战报
 */
public class SRoundResult {

    private short round;// 回合数
    private int timeOut;//
    private List<ActionData> left;// 左拳
    private List<ActionData> right;// 右拳

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

    public void setLeft(List<ActionData> left) {
        this.left = left;
    }

    public List<ActionData> getLeft() {
        return left;
    }

    public void setRight(List<ActionData> right) {
        this.right = right;
    }

    public List<ActionData> getRight() {
        return right;
    }
}
