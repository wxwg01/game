package com.game.center.controller;

import com.game.base.util.GameResultUtils;
import com.game.base.vo.GameHellServerInfo;
import com.game.base.vo.GameResult;
import com.game.center.akka.CenterAkkaFactory;
import com.game.center.vo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @date : 2019/5/15
 */
@RestController("game")
public class GameController {


    @PostMapping("/login")
    public GameResult<User> login(String accountName, String password){
        User user = new User();
        user.setName("阿三");
        user.setNikName("东北玩泥巴");
        return GameResultUtils.buildSuccessResult(user);
    }

    @PostMapping("/queryGameServiceList")
    public GameResult<List<GameHellServerInfo>> queryGameServiceList(){
        List<GameHellServerInfo> list = CenterAkkaFactory.getInfoMap().entrySet().stream()
            .map(Map.Entry::getValue)
            .peek(it->it.setActorRef(null))
            .sorted(Comparator.comparing(GameHellServerInfo::getServiceName))
            .collect(Collectors.toList());
        return GameResultUtils.buildSuccessResult(list);
    }
}
