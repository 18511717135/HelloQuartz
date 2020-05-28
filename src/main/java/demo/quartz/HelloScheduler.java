package demo.quartz;

import demo.util.DateUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

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

        // 2. 定义一个SimpleTrigger，定义该job在4秒后执行,6s后停止
        //2.1设置开始时间为4s后
        Date startTime = new Date();
        startTime.setTime(startTime.getTime() + 4000L);
        //2.2设置结束时间为6s后
        Date endTime = new Date();
        endTime.setTime(endTime.getTime() + 6000L);
        SimpleTrigger trigger2 = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("myTrigger2", "group2")// 定义名字和组
                .startAt(startTime)//定义开始时间为4s后
                .endAt(endTime)//定义结束时间为6s后
                .withSchedule(    //定义任务调度的时间间隔和次数
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInSeconds(2)//定义时间间隔是2秒
                                .withRepeatCount(3)//定义重复执行次数是无限次
                )
                .build();
		/*CronTrigger cronTrigger = (CronTrigger) TriggerBuilder
				.newTrigger()
				.withIdentity("myCronTrigger", "group3")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("* * * * * ?"))
				.build();*/

        // 2. 2020年内每天9点30开始执行，每隔5s执行一次
        CronTrigger cronTrigger2 = (CronTrigger) TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger2", "group4")// 定义名字和组
                .withSchedule(    //定义任务调度的时间间隔和次数
                        CronScheduleBuilder
                                .cronSchedule("0/5 30 9 ? * * 2020")
                )
                .build();
        // 2. 每天的14点整至14点59分55秒，以及18点整至18点59分55秒，每5秒钟执行一次
        CronTrigger cronTrigger3 = (CronTrigger) TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger3", "group5")// 定义名字和组
                .withSchedule(    //定义任务调度的时间间隔和次数
                        CronScheduleBuilder
                                .cronSchedule("0/5 0 14,18 * * ? *")
                )
                .build();
        //3、 创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();

        // 4. 将trigger和jobdetail加入这个调度
        scheduler.scheduleJob(jobDetail, trigger);

        // 5. 启动scheduler
        scheduler.start();

        System.out.println("scheduled time is :"+ DateUtil.now());
        //scheduler执行两秒后挂起
        //Thread.sleep(2000L);
        //shutdown(true)表示等待所有正在执行的job执行完毕之后，再关闭scheduler
        //shutdown(false)即shutdown()表示直接关闭scheduler
        scheduler.shutdown(false);
        System.out.println("scheduler is shut down? " + scheduler.isShutdown());
    }
}
