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

import com.game.event.IEventListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
public class GoldChangeEvent implements IEventListener {

    @Override
    public void execute(Object param) {

    }


}
