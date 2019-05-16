package com.game.service.config.akka;

import com.game.base.service.factory.AkkaFactory;
import com.game.base.service.vo.GameHellServerInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @email : wb-wg471966@alibaba-inc.com
 * @date : 2019/5/15
 */
@Component
public class CenterAkkaFactory extends AkkaFactory {

    private static Map<String, GameHellServerInfo> infoMap = new ConcurrentHashMap<>();

    @Value("${akka.file.path}")
    private String akkaFilePath;

    @PostConstruct
    public void init() {
        AkkaFactory.path = akkaFilePath;
        AkkaFactory.getInstant();

    }

    public static Map<String, GameHellServerInfo> getInfoMap() {
        if (infoMap == null) {
            infoMap = new ConcurrentHashMap<>(128);
        }
        return infoMap;
    }
}
