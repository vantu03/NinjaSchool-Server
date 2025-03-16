package com.tgame.ninja.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPool.class);

    public ExecutorService executor;

    public Vector<Runnable> workList;

    public long time;

    public static ThreadPool getInstance() {
        return MyHolder.INSTANCE;
    }

    public ThreadPool() {
        executor = Executors.newFixedThreadPool(350);
        workList = new Vector<>();
        time = System.currentTimeMillis();
        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    while (workList.size() > 0) {
                        if (++count % 200 == 0) {
                            LOGGER.info("===>Thread Snap===Works Remain={}===200 works in {} ms", workList.size(), (System.currentTimeMillis() - time));
                            time = System.currentTimeMillis();
                            if (count > 1000) {
                                count = 0;
                            }
                            NJUtil.sleep(100L);
                        }
                        try {
                            executor.execute(workList.remove(0));
                        } catch (Exception e) {
                            LOGGER.error("Execute task error", e);
                        }
                    }
                    NJUtil.sleep(100L);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    private static class MyHolder {

        public static ThreadPool INSTANCE = new ThreadPool();
    }
}
