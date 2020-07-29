package com.jt.pojo;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@TableName("tb_order_item")
@Data
@Accessors(chain=true)
public class OrderItem extends BasePojo{
	
	//@TableId  高版问题  有得mybatis-plus只能有一个主键id<version>3.3.2</version>
    private String itemId;
	
	@TableId
    private String orderId;

    private Integer num;

    private String title;

    private Long price;

    private Long totalFee;

    private String picPath;
}