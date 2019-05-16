package com.game.domain.pojo.server;

import lombok.Data;

/**
 * 20001 : 登陆
 * @author WG
 */
@Data
public class ServerLoginVO {
    private String uuid;
    private String nickName;
    private String gold;
    private Integer level;

}
