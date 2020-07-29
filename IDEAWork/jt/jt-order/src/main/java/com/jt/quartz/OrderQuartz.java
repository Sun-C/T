package com.jt.quartz;

import java.util.Calendar;
import java.util.Date;

import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;


//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;

	/**当用户订单提交30分钟后,如果还没有支付.则交易关闭
	 * 现在时间 - 订单创建时间 > 30分钟  则超时
	 * new date - 30 分钟 > 订单创建时间
	 *
	 * 删除30分钟之后没有支付的订单, 将订单状态 由1 改为 6
	 * 定时器时间一到 则执行任务.....
	 *
	 * 业务实现:
	 * 		如何判断超时: create < now - 30
	 * 		1.sql update tb_order set status = 6 where status = 1 and created < timeOut
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//1.计算超时时间
		Calendar calendar = Calendar.getInstance();//过去当前时间 只能做加法 可以加 负数
		calendar.add(Calendar.MINUTE,-30);
		Date timeOut = calendar.getTime();
		orderMapper.updateStatus(timeOut);

		//设定30分钟超时 mybatis-puls 写法
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.MINUTE, -30);
//		Date date = calendar.getTime();
//		Order order = new Order();
//		order.setStatus(6);
//		order.setUpdated(new Date());
//		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
//		updateWrapper.eq("status", "1").lt("created",date);
//		orderMapper.update(order, updateWrapper);
		System.out.println("定时任务执行");
	}
}
