package com.game.domain.pojo.server;

/**
 * 20001 : 登陆
 */
public class SLogin {

    private String uuid;// 人物唯一ID
    private PlayerData player;// 玩家信息,未创角色返回null

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public PlayerData getPlayer() {
        return player;
    }
}
