package com.game.service.config.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Address;
import akka.actor.Terminated;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.japi.pf.ReceiveBuilder;
import com.game.base.service.enums.ServerType;
import com.game.base.service.vo.GameHellServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @email : wb-wg471966@alibaba-inc.com
 * @date : 2019/5/15
 */
public class ServerActor extends AbstractActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerActor.class);
    public static final String BACKEND_REGISTRATION_HELL = "hell";

    Cluster cluster = null;

    public ServerActor() {
        if (cluster == null) {
            System.out.println("init");
            cluster = Cluster.get(getContext().system());
            cluster.subscribe(self(), ClusterEvent.MemberRemoved.class, ClusterEvent.UnreachableMember.class
                /*,ClusterEvent.CurrentClusterState.class,ClusterEvent.MemberRemoved.class */);
        }
        receive(ReceiveBuilder.match(ClusterEvent.MemberJoined.class, m -> {
            System.out.println("Member is Joined: {}" + m.member());
        }).match(ClusterEvent.MemberUp.class, m -> {
            System.out.println("Member is Up: {}" + m.member());
        }).match(ClusterEvent.UnreachableMember.class, m -> {
            LOGGER.info("Member detected as unreachable: {}" + m.member());
            removeGameHellServer(m.member().getRoles());
        }).match(ClusterEvent.MemberRemoved.class, m -> {
            LOGGER.info("Member is Removed: {}" + m.member());
            removeGameHellServer(m.member().getRoles());
        }).match(ClusterEvent.MemberExited.class, m -> {
            LOGGER.info("Member is Exited: {}" + m.member());
            removeGameHellServer(m.member().getRoles());
        }).match(ClusterEvent.CurrentClusterState.class, m -> {
            LOGGER.info("Member allMembers" + m.members());
        }).match(GameHellServerInfo.class, m -> {
            LOGGER.info("client conn," + sender());
            getContext().watch(sender());
            m.setActorRef(sender());
            CenterAkkaFactory.getInfoMap().put(m.getServiceName(), m);
        }).match(Terminated.class, m -> {
            LOGGER.info("Terminated conn," + m);
            removeGameHellServer(m.getActor());
        }).build());
    }

    /**
     * 约定 akka服务中配置的角色与对应服务名一致
     *
     * @param roles
     */
    private void removeGameHellServer(Set<String> roles) {
        for (String role : roles) {
            if (role.startsWith(ServerType.HELL.name())) {
                CenterAkkaFactory.getInfoMap().remove(role);
            }
        }
    }

    private void removeGameHellServer(ActorRef actorRef) {
        Set<String> stringSet = CenterAkkaFactory.getInfoMap().keySet();
        String address = actorRef.path().address().toString();
        for (String key : stringSet) {
            GameHellServerInfo serverInfo = CenterAkkaFactory.getInfoMap().get(key);

            if (serverInfo != null) {
                String add = serverInfo.getActorRef().path().address().toString();
                if (address.equals(add)) {
                    CenterAkkaFactory.getInfoMap().remove(key);

                }
            }
        }
    }

}
