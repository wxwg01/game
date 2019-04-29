/*
 * Copyright (C), 2015-2018
 * FileName: MessageHandlerDictionary
 * Author:   wanggang
 * Date:     2018/6/25 16:39
 * Description: 消息字典绑定
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.server.handler.dictionary;

import com.game.domain.utils.StringUtil;
import com.game.netBase.concurrent.commond.IHandler;
import com.game.netBase.concurrent.dictionary.IMessageDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈消息字典绑定〉
 *
 * @author wanggang
 * @date 2018/6/25 16:39
 * @since 1.0.0
 */
@Component
@Scope("singleton")
public class MessageHandlerDictionary implements IMessageDictionary {
    private final Map<Short, Class<? extends IHandler>> idHandleMap = new HashMap<>(10);

    @PostConstruct
    public void init() {
   /* register(CEnumValue.CLOGIN.getCode(), CEnumValue.CLOGIN.getC());
    register(CEnumValue.CCREATEROLE.getCode(), CEnumValue.CCREATEROLE.getC());*/
    }

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void register(Short messageId, Class<? extends IHandler> handler) {
        idHandleMap.put(messageId, handler);
    }

    @Override
    public IHandler getHandlerFromMessageId(Short messageId) {
        Class<? extends IHandler> clazz = idHandleMap.get(messageId);
        if (clazz != null) {
            try {
                String clazzName = StringUtil.toLowerCaseFirstOne(clazz.getSimpleName());
                return (IHandler) applicationContext.getBean(clazzName);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
