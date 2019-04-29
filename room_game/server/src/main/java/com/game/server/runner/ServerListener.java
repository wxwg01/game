package com.game.server.runner;

import com.alibaba.fastjson.JSONObject;
import com.game.domain.config.ServerConfig;
import com.game.domain.enums.CEnumValue;
import com.game.domain.enums.CommonValue;
import com.game.domain.enums.SEnumValue;
import com.game.domain.pojo.*;
import com.game.domain.pojo.server.PlayerData;
import com.game.domain.utils.FileUtil;
import com.game.domain.utils.XmlUtil;
import com.game.event.EventUtil;
import com.game.netBase.IServer;
import com.game.netBase.concurrent.dictionary.IMessageDictionary;
import com.game.netBase.session.Session;
import com.game.netBase.session.SessionManager;
import com.game.server.event.ExpChangeEvent;
import com.game.server.event.GoldChangeEvent;
import com.game.server.event.LevelChangeEvent;
import com.game.server.event.RoundEndEvent;
import com.game.server.handler.CCreateRoleHandler;
import com.game.server.handler.CFastJoinRoomHandler;
import com.game.server.handler.CLoginHandler;
import com.game.server.handler.CRoundHandler;
import com.game.service.room.RoomManager;
import com.game.service.room.RoundRunnable;
import com.game.service.services.IRoundService;
import com.game.service.threadPool.TaskExecutePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
            CommonValue.msgType = serverConfig.getMessageType();
            CommonValue.roundWait = serverConfig.getRoundMxTime();
            CommonValue.joinRoomTime = serverConfig.getJoinRoomTime();
            CommonValue.rounds = serverConfig.getRounds();

            readConfig();

            System.out.println(JSONObject.toJSONString(CommonValue.levelMap));
            System.out.println(JSONObject.toJSONString(CommonValue.roleMap));

            handelRegister();
            eventRegister();

//            receiveStart();

            // 模拟一个房间

            PlayerData p1 = new PlayerData();
            p1.setName("aa");
            p1.setUuid("1");
            PlayerData p2 = new PlayerData();
            p2.setName("bb");
            p2.setUuid("1");

            System.out.println(JSONObject.toJSONString(p1));
            Session session1 = new Session(null);
            Session session2 = new Session(null);

            SessionManager.getInstance().register(session1,p1);
            SessionManager.getInstance().register(session2,p2);

            RoomManager.getInstance().addRoom((byte)1,1,p1);

            RoundRunnable room = RoomManager.getInstance().joinRoom(p2.getUuid(),1,(byte)1,1);

            room.setPreRoundTime(System.currentTimeMillis());


            TaskExecutePool.getInstance().getRunRoom().execute(room);

            Thread.sleep(30000);
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载配置信息
     */
    private void readConfig() throws Exception {

        RoleList list = (RoleList) XmlUtil.xml2Object(FileUtil.readFile(serverConfig.getRolePath()), RoleList.class);
        Optional.ofNullable(list).ifPresent(item -> {
            list.getRoles().forEach(it -> {
                JSONObject j = JSONObject.parseObject(it.getProp());

                it.setHp(j.getIntValue("HP"));
                it.setMpMax(j.getIntValue("MP"));
                it.setAtt(j.getIntValue("ATT"));
                it.setBlock(j.getIntValue("BLOCK"));
                it.setBlockHit(j.getIntValue("BATTACK"));
                it.setMiss(j.getIntValue("MISS"));
                it.setAntiCrit(j.getIntValue("ANTICRIT"));
                it.setCd(j.getIntValue("CRIT"));
                it.setIntitate(j.getIntValue("INTITATE"));
            });
            CommonValue.roleMap = item.getRoles().stream().collect(Collectors.toMap(Role::getId, Function.identity()));
        });

        System.out.println(JSONObject.toJSONString(list));

        LevelList levels = (LevelList) XmlUtil.xml2Object(FileUtil.readFile(serverConfig.getLevelPath()), LevelList.class);
        Optional.ofNullable(levels).ifPresent(item -> CommonValue.levelMap = item.getLevels().stream().collect(Collectors.toMap(Level::getLevel, Level::getExp)));

        System.out.println(JSONObject.toJSONString(levels));

        SkillList skillList = (SkillList) XmlUtil.xml2Object(FileUtil.readFile(serverConfig.getSkillPath()), SkillList.class);
        Optional.ofNullable(skillList).ifPresent(item -> CommonValue.skillMap = item.getSkillList().stream().collect(Collectors.toMap(Skill::getId, Function.identity())));

        System.out.println(JSONObject.toJSONString(skillList));
    }

    /**
     * 注册事件
     */
    private void eventRegister() {
        EventUtil.addListener(SEnumValue.SROUNDRESULT.getCode(), levelChangeListener);
        EventUtil.addListener(SEnumValue.SROUNDRESULT.getCode(), goldChangeEvent);
        EventUtil.addListener(SEnumValue.SROUNDRESULT.getCode(), expChangeEvent);

        EventUtil.addListener(SEnumValue.SROUNDEND.getCode(), roundEndEvent);
    }

    /**
     * 注册解析帧
     */
    private void handelRegister() {
        messageDictionary.register(CEnumValue.CLOGIN.getCode(), CLoginHandler.class);
        messageDictionary.register(CEnumValue.CCREATEROLE.getCode(), CCreateRoleHandler.class);
        messageDictionary.register(CEnumValue.CFASTJOINROOM.getCode(), CFastJoinRoomHandler.class);
        messageDictionary.register(CEnumValue.CROUND.getCode(), CRoundHandler.class);
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
