package demo.quartz;


import demo.util.DateUtil;
import org.quartz.*;

public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*System.out.println("HelloJob Time Is : " + DateUtil.now());
		//具体业务代码
		System.out.println("Hello World!");*/

		JobKey key = context.getJobDetail().getKey();
		System.out.println("jobDetil name: "+key.getName() +"group: "+key.getGroup() +"class: "+key.getClass());
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		JobDataMap tDataMap = context.getTrigger().getJobDataMap();
		System.out.println("jobMsg: "+jobDataMap.getString("message"));
		System.out.println("job isOk: "+jobDataMap.getBoolean("isOk"));
		System.out.println("trigger: "+tDataMap.getString("trigger"));
		System.out.println(1);
	}

}
