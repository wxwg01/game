package com.game.akka.factory;

import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * @author wanggang
 * @create 2018-06-12 9:05
 **/
public class AkkaFactory {
    private static String SYSTEM_NAME = "ClusterSystem";

    private static volatile AkkaFactory instant;

    private  ActorSystem system;

    public static String path;

    public static AkkaFactory getInstant() {
        if(instant ==null){
            instant = new AkkaFactory();
        }
        if(instant !=null && instant.system==null){
            System.out.println(path);
            instant.system = ActorSystem.create(SYSTEM_NAME, ConfigFactory.parseFile(new File(path)));
        }
        return instant;
    }

}
