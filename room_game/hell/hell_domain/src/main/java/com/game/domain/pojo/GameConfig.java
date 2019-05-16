package com.game.domain.pojo;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/18 14:02
 * @Since 0.0.1
 */
public class GameConfig {
    int hitSuccHpDown = 0;// 攻击成功 减血

    int hitSuccMpAdd = 20;// 攻击成功 加蓝
    int hitBlockMpAdd = 10;// 攻击格挡 加蓝

    int beHitMpAdd = 10;// 被击中 加蓝
    int beHitHpDown = 10;// 攻击成功 减血

    int blockMpAdd = 5;// 格挡成功 加蓝
    int blockHpDown = 5;// 格挡成功 减血

    int xuliMpAdd = 20;// 蓄力 加蓝
}
