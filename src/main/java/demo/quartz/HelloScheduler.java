package demo.quartz;

import demo.util.DateUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        System.out.println("启动任务 : " + DateUtil.now());
        //1、 创建一个JobDetail实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob","group1").build();
        //2、创建Trigger实例，定义job立即执行
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger","group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2).repeatForever())
                .build();

		/*CronTrigger trigger = (CronTrigger) TriggerBuilder
				.newTrigger()
				.withIdentity("myTrigger", "group1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("* * * * * ?"))
				.build();*/
        //3、 创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        //4、执行
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("scheduled time is :"+ DateUtil.now());
        //scheduler执行两秒后挂起
        //Thread.sleep(2000L);
        //shutdown(true)表示等待所有正在执行的job执行完毕之后，再关闭scheduler
        //shutdown(false)即shutdown()表示直接关闭scheduler
        //scheduler.shutdown(false);
        //System.out.println("scheduler is shut down? " + scheduler.isShutdown());
    }
}
