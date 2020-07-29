package com.jt.pojo;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_order")
@Data
@Accessors(chain=true)
public class Order extends BasePojo{
	@TableField(exist=false)	//入库操作忽略该字段 相当于他俩不是数据库表字段
	private OrderShipping orderShipping;
								//封装订单商品信息  一对多
	@TableField(exist=false)	//入库操作忽略该字段
	private List<OrderItem> orderItems;
	
	@TableId   //标识主键并没有设定自增  订单号 登录用户 id+当前时间
    private String orderId;   //Long 设定自增 会给数据库带来压力  要计算后才能有id
    private String payment;
    private Integer paymentType;
    private String postFee;
    private Integer status;//1.未付款 2.已付款 3.未发货 4.已发货 5.交易成功 6.交易失败
    private Date paymentTime;
    private Date consignTime;
    private Date endTime;
    private Date closeTime;
    private String shippingName;
    private String shippingCode;
    private Long userId;
    private String buyerMessage;
    private String buyerNick;
    private Integer buyerRate;

}