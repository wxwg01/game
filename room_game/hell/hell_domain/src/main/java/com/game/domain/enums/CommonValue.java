/*
 * Copyright (C), 2015-2018
 * FileName: CommonValue
 * Author:   wanggang
 * Date:     2018/6/25 16:45
 * Description: 放置常量
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.domain.enums;

import com.game.domain.pojo.Role;
import com.game.domain.pojo.Skill;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈放置常量〉
 *
 * @author wanggang
 * @date 2018/6/25 16:45
 * @since 1.0.0
 */
public class CommonValue {
    public static String msgType;
    public static int joinRoomTime = 30;
    public static int rounds = 5;
    public static int roundWait = 30;

    public static byte ROOM_TYPE_NOMALE = 1;
    public static byte ROOM_TYPE_PAIWEI = 2;

    public static int hitSuccHpDown = 0;// 攻击成功 减血

    public static int hitSuccMpAdd = 20;// 攻击成功 加蓝
    public static int hitBlockMpAdd = 10;// 攻击格挡 加蓝

    public static int beHitMpAdd = 10;// 被击中 加蓝
    public static int beHitHpDown = 10;// 攻击成功 减血

    public static int blockMpAdd = 5;// 格挡成功 加蓝
    public static int blockHpDown = 5;// 格挡成功 减血

    public static int xuliMpAdd = 20;// 蓄力 加蓝

    public static int RATIO_100 = 100;

    public static Map<Integer, Role> roleMap;
    public static Map<Integer, Long> levelMap;
    public static Map<Integer, Skill> skillMap;

    public static int ACTION2_NOMAIL = 0;// 无
    public static int ACTION2_BLOCK = 1;// 格挡
    public static int ACTION2_MISS = 2;// 闪避
    public static int ACTION2_BLOCKATT = 3;// 格挡反击
    public static int ACTION2_BEHIT = 4;// 受到攻击
    public static int ACTION2_HIT = 5;// 攻击


}
