/*
 * Copyright (C), 2015-2018
 * FileName: IMessageDictionary
 * Author:   wanggang
 * Date:     2018/6/25 14:51
 * Description: 消息字典接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.netBase.concurrent.dictionary;

import com.game.netBase.concurrent.commond.IHandler;

/**
 * 〈一句话功能简述〉<br>
 * 〈消息字典接口〉
 *
 * @author wanggang
 * @date 2018/6/25 14:51
 * @since 1.0.0
 */
public interface IMessageDictionary {

    /**
     * 注册 id--handle
     *
     * @param messageId
     * @param handler
     */
    void register(Short messageId, Class<? extends IHandler> handler);

    /**
     * 根据messageId获取handler
     *
     * @param messageId
     * @return
     */
    IHandler getHandlerFromMessageId(Short messageId);
}
