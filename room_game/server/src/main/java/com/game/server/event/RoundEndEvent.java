/*
 * Copyright (C), 2015-2018
 * FileName: LoginEventListener
 * Author:   wanggang
 * Date:     2018/7/20 14:57
 * Description: 登录事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.server.event;

import com.game.domain.pojo.server.SRoundEnd;
import com.game.event.IEventListener;
import com.game.netBase.session.SessionManager;
import com.game.service.room.RoomManager;
import com.game.service.room.RoundRunnable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈等级变化〉
 *
 * @author wanggang
 * @date 2018/7/20 14:57
 * @since 1.0.1
 */
@Component
@Scope("singleton")
public class RoundEndEvent implements IEventListener {

    @Override
    public void execute(Object param) {
        RoundRunnable room = (RoundRunnable) param;
        Optional.ofNullable(RoomManager.getInstance().getRoomByRoomId(room.getRoomId(), room.getRoomType())).ifPresent(item -> {
            roundEnd(room.getP1().getUuid());
            roundEnd(room.getP2().getUuid());
        });
        RoomManager.getInstance().roundEnd(room);
    }

    public void roundEnd(String uid) {
        Optional.ofNullable(SessionManager.getInstance().getSession(uid)).ifPresent(session -> {
            // 回合全部结束
            SRoundEnd roundEnd = new SRoundEnd();
            try {
                SessionManager.getInstance().sendMessage(session, roundEnd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
