package com.jt.conf;

import com.jt.quartz.OrderQuartz;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OrderQuartzConfig {
	
	//定义任务详情
	@Bean	//封装job任务的JobDetail 建造者模式
	public JobDetail orderjobDetail() {
		//指定job的名称和持久化保存任务
		return JobBuilder
				.newJob(OrderQuartz.class)  //1.任务类型
				.withIdentity("orderQuartz") //2.任务名称
				.storeDurably()  //
				.build();
	}
	//springboot会实现对象自动装配 开箱即用
	//定义触发器  告知将来执行的任务是谁?
	@Bean
	public Trigger orderTrigger() {
		/*SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(1)	//定义时间周期
				.repeatForever();*/
		CronScheduleBuilder scheduleBuilder      //一分钟执行一次  表达式
			= CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");  //调度器 设定我们的程序多久执行一次...
		return TriggerBuilder
				.newTrigger()
				.forJob(orderjobDetail())  //执行什么样的任务
				.withIdentity("orderQuartz")  //任务的名称
				.withSchedule(scheduleBuilder).build();  //什么时候执行  由调度器决定什么时候执行
	}
}
