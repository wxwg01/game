package com.game.domain.pojo.server;

/**
 * 20002 : 创建角色返回
 */
public class SCreateRole {

    private int errorcode;// 0成功1001:保存角色数据失败!,1002创建角色角色失败!,1003:没有找到账号，请先登陆!

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getErrorcode() {
        return errorcode;
    }
}
