package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	/**
	 * 1.url地址: page/item-add
	 * 			page/item-list
	 * 			page/item-param-list
	 * 2.需求: 使用一个方法实现页面跳转
	 * 		实现: RestFul风格
	 * 				语法:
	 * 					1.使用/方式分割参数,
	 * 					2.使用{}包裹参数
	 * 					3.在参数方法中,动态接收参数时使用特定注解@PathVariable
	 * 3注解:@PathVariable()
	 * 			name/value: 标识接收参数的名字
	 * 			required: 该参数是否必须 默认true

	 *
	 * restFul风格2:
	 * 		思路: 利用通用的url地址,实现不同的业务调用
	 * 		例子: http://www.jt.com/user?id=1		查询			GET
	 * 		例子: http://www.jt.com/user?id=1		删除			DELETE
	 * 		例子: http://www.jt.com/user?id=1&name=xxx		更新		PUT
	 * 		例子: http://www.jt.com/user		新增操作					POST
	 * 总结:
	 * 	作用1 : 动态获取 url 路径中的参数
	 * 	 作用2 : 以统一的url地址,不同的方法类型,实现不同的业务调用
	 *
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
}
