package com.game.server.runner;

import com.game.config.ServerConfig;
import com.game.event.EventUtil;
import com.game.net.base.concurrent.dictionary.IMessageDictionary;
import com.game.net.base.server.IServer;
import com.game.server.event.ExpChangeEvent;
import com.game.server.event.GoldChangeEvent;
import com.game.server.event.LevelChangeEvent;
import com.game.server.event.RoundEndEvent;
import com.game.server.handler.ClientLoginHandler;
import com.game.server.handler.ClientRoundHandler;
import com.game.service.services.IRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <一句话简单说明类功能>
 * 服务监听类</br>
 *
 * @Author wanggang
 * @Date 2018/9/6 18:05
 * @Since 0.0.1
 */
@Component
public class ServerListener implements ServletContextListener {

    @Autowired
    @Qualifier("basicServerImpl")
    private IServer basicServerImpl;

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private IMessageDictionary messageDictionary;
    @Autowired
    private ExpChangeEvent expChangeEvent;
    @Autowired
    private GoldChangeEvent goldChangeEvent;
    @Autowired
    private LevelChangeEvent levelChangeListener;
    @Autowired
    private RoundEndEvent roundEndEvent;
    @Autowired
    private IRoundService roundService;

    /**
     * 启动
     *
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            readConfig();
            handelRegister();
            eventRegister();
            receiveStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载配置信息
     */
    private void readConfig() throws Exception {
    }

    /**
     * 注册事件
     */
    private void eventRegister() {
        EventUtil.addListener(2001, levelChangeListener);
        EventUtil.addListener(2002, goldChangeEvent);
        EventUtil.addListener(2003, expChangeEvent);
        EventUtil.addListener(2004, roundEndEvent);
    }

    /**
     * 注册解析帧
     */
    private void handelRegister() {
        messageDictionary.register(1001, ClientLoginHandler.class);
        messageDictionary.register(1002, ClientRoundHandler.class);
    }

    /**
     * 信息帧接收
     *
     * @throws Exception
     */
    private void receiveStart() throws Exception {
        basicServerImpl.start();
    }


    /**
     * 结束
     *
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
