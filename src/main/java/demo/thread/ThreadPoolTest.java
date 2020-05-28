package demo.thread;

import demo.util.DateUtil;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) {
        /**
         * 使用工厂方法初始化一个ScheduledThreadPool
         */
        ScheduledExecutorService newScheduledThreadPool = Executors
                .newScheduledThreadPool(2);

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("task1:  " + DateUtil.now());
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2:  " + DateUtil.now()
                );
            }
        };
        System.out.println("启动时间:  "+DateUtil.now());
        newScheduledThreadPool.schedule(task1, 1000, TimeUnit.MILLISECONDS);
        newScheduledThreadPool.schedule(task2, 3000, TimeUnit.MILLISECONDS);
    }
}
