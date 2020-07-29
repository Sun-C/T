package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Reference(check = false)
    private DubboCartService dubboCartService;
    @Reference(check = false)
    private DubboOrderService dubboOrderService;

    //private DubboOrderService dubboOrderService;
    /**
     *  url : www.jt.com/order/create.html
     *  业务逻辑: 绑定userId,之后查询购物车信息 之后再页面添加${carts}
     * @return
     */
    @RequestMapping("create")
    public String create(Model model, HttpServletRequest request){
        User user = (User)request.getAttribute("JT_USER");
        long userId = user.getId();
        List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("carts",cartList);
        return "order-cart";
    }
    @ResponseBody
    @RequestMapping("submit")
    public SysResult submit(Order order,HttpServletRequest request){
        User user = (User) request.getAttribute("JT_USER");
        order.setUserId(user.getId());
        String orderId = dubboOrderService.saveOrder(order);
        if (StringUtils.isEmpty(orderId)){
            //说明: 后端服务器异常
            return SysResult.fail("订单提交失败");
        }
        return SysResult.success("ok",orderId);
    }

    /**
     * url : www.jt.com/order/success.html?id=79175971497
     * 获取order对象信息
     * @param model
     * @return
     */
    @RequestMapping("success")
    public String orderSuccess(String id,Model model){
        Order order = dubboOrderService.findOrderByOrderId(id);
        model.addAttribute("order",order);
        return "success";
    }
    @RequestMapping("myOrder")
    public String myOrder(HttpServletRequest request){
        User user = (User) request.getAttribute("JT_USER");

        return "my-orders";
    }
}
