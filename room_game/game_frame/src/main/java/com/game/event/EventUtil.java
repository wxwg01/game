package com.game.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 〈一句话功能简述〉<br>
 * 〈事件工具类〉
 *
 * @author wanggang
 * @date 2018/7/20 11:16
 * @since 1.0.1
 */
public final class EventUtil {

    private static final Map<Integer, List<IEventListener>> PREPARED_LISTENERS = new HashMap<>(16);

    /**
     * 添加事件
     * 一个注册码，可以触发多个事件
     *
     * @param listener 事件
     * @param type     唯一码
     */
    public static void addListener(Integer type, IEventListener listener) {
        List<IEventListener> listenerList = PREPARED_LISTENERS.computeIfAbsent(type, k -> new ArrayList<>());
        listenerList.add(listener);
    }

    public static void fireEvent(Integer type) {
        fireEvent(type, null);
    }

    public static void fireEvent(Integer type, Object obj) {

        List<IEventListener> listenerList = PREPARED_LISTENERS.get(type);
        if (listenerList != null) {
            for (IEventListener listener : listenerList) {
                try {
                    listener.execute(obj);
                } catch (Exception e) {
                }
            }
        }
    }

    private EventUtil() {
    }

}
