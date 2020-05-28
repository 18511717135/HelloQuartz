package demo.quartz;

import demo.util.DateUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class JobDetailDemo {
    public static void main(String[] args) throws SchedulerException, InterruptedException {


        //1、 创建一个JobDetail实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob")
                .usingJobData("message","Hello myJobDetail")
                .usingJobData("isOk",true)
                .build();
        /*System.out.println("jobDetil name" + jobDetail.getKey().getName());
        System.out.println("jobDetil group" + jobDetail.getKey().getGroup());
        System.out.println("jobDetil jobClass" + jobDetail.getKey().getClass());*/
        //2、创建Trigger实例，定义job立即执行
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger","group1")
                .usingJobData("trigger","trigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2).repeatForever())
                .build();

        //3、 创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        //4、执行
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("scheduled time is :"+ DateUtil.now());

    }
}
