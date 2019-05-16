/**
 * 用一句话描述该文件做什么
 *
 * @title TaskExecutePool.java
 * @package com.igen.solarman.message.command.ThreadPool
 * @author lvjian
 * @create 2017-6-7 上午10:00:59
 * @version V1.0
 */
package com.game.service.threadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * 创建线程池，用于多线程接收MQ消息
 * @author WG
 * @version 1.0
 */
@Configuration
@EnableAsync
public class TaskExecutePool {
    @Bean
    public Executor joinRoomPool() {
        ExecutorService executor = Executors.newCachedThreadPool();
        return executor;
    }

    public static TaskExecutePool pool = null;

    public ThreadPoolExecutor runRoom;

    public static TaskExecutePool getInstance() {
        if (!Optional.ofNullable(pool).isPresent()) {
            pool = new TaskExecutePool();
            pool.runRoom = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() + 1, 10l, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(1000));
        }
        return pool;
    }

    public ThreadPoolExecutor getRunRoom() {
        return runRoom;
    }


}
