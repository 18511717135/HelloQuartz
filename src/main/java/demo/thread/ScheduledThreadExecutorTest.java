package demo.thread;


import demo.util.DateUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadExecutorTest {

    //ScheduledThreadPoolExecutor 继承自ThreadPoolExecutor实现了ScheduledExecutorService接口。
    // 主要完成定时或者周期的执行线程任务。
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
        System.out.println("main开始时间:" + DateUtil.now());
        for (int i = 0; i < 3; i++) {
            MyRunnable myRunnable = new MyRunnable("thread-" + i);
            System.out.println(myRunnable.getName() + "开始时间:" + DateUtil.now());
            pool.schedule(myRunnable, 5, TimeUnit.SECONDS);//延时5秒执行
            //在一次调用完成和下一次调用开始之间有长度为delay的延迟
            //pool.scheduleWithFixedDelay(myRunnable,5,5,TimeUnit.SECONDS);
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();//7秒后不再接受执行线程
        while (!pool.isTerminated()) {
            //等待所有线程结束
        }
        System.out.println("main结束时间:" + DateUtil.now());
    }
}
class MyRunnable implements Runnable {
    private String name;
    public MyRunnable(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    @Override
    public void run() {
        System.out.println(getName() + "true start:" + DateUtil.now());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + "true end:" + DateUtil.now());
    }

}