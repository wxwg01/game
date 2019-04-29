/*
 * Copyright (C), 2015-2018
 * FileName: IUser
 * Author:   wanggang
 * Date:     2018/6/22 15:46
 * Description: 用户的抽象类，主要用于session之中
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.netBase.session;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户的抽象类，主要用于session之中〉
 *
 * @author wanggang
 * @date 2018/6/22 15:46
 * @since 1.0.0
 */
public interface IUser {

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    String getId();

    /**
     * 获取用户状态id
     *
     * @return 状态id
     */
    Integer getStatus();

}
