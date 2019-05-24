/*
 * Copyright (C), 2015-2018
 * FileName: TimeUtil
 * Author:   wanggang
 * Date:     2018/6/22 17:07
 * Description: TimeUtil时间工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈TimeUtil时间工具〉
 *
 * @author wanggang
 * @date 2018/6/22 17:07
 * @since 1.0.0
 */
public class TimeUtil {

    private TimeUtil() {
    }

    /**
     * 获取系统距1970年1月1日总毫秒
     *
     * @return 距1970年1月1日总毫秒
     */
    public static long getSysCurTimeMillis() {
        return getCalendar().getTimeInMillis();
    }

    /**
     * 获取系统时间
     *
     * @return 系统时间
     */
    private static Calendar getCalendar() {
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        return nowCalendar;
    }

    /**
     * 获取当前UTC日期
     */
    public static Date getUTCTime() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }
}
