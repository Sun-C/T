package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart/")
public class CartController {
    @Reference(check = false)
    private DubboCartService dubboCartService;
    @RequestMapping("show")
    public String show(Model model,HttpServletRequest request){
        //1.根据userId查询他的购物车
        User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId();
        List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);
        return "cart";
    }
    /**
     * http://www.jt.com/cart/update/num/1474391983/4
     */
    @RequestMapping("update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartMany(Cart cart,HttpServletRequest request){
        User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId();;
        cart.setUserId(userId);
        dubboCartService.updateCartNum(cart);
        return SysResult.success(cart);
    }

    /**
     * 业务: 删除购物车商品信息
     * url地址: www.jt.com/cart/delete/1478923.html
     * 参数问题: 1478923
     * @param itemId
     * @return 重定向到购物车列表页面
     */
    @RequestMapping("delete/{itemId}")
    public String deleteCartById(@PathVariable Long itemId,HttpServletRequest request){
        User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId();
        dubboCartService.deleteCartByItemId(userId,itemId);
        return "redirect:/cart/show.html";
    }

    /**
     * url : http://www.jt.com/cart/add/1245235.html
     * 参数: 表单数据提交cart
     * 返回值: 重定向到购物车页面
     */
    @RequestMapping("add/{itemId}")
    public String saveCart(Cart cart,HttpServletRequest request){
        User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId();
        cart.setUserId(userId);
        dubboCartService.saveCart(cart);
        return "redirect:/cart/show.html";  //维护伪静态策略
    }

}
