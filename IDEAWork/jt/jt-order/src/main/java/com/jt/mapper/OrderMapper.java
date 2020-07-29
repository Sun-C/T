package com.jt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Order;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface OrderMapper extends BaseMapper<Order>{
    @Update("update tb_order set status = 6 where status = 1 and created < #{timeOut}")
    void updateStatus(Date timeOut);
}