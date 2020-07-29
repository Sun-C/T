package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DubboCartServiceImpl implements DubboCartService{
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId);
        List<Cart> carts = cartMapper.selectList(cartQueryWrapper);
        return carts;
    }

    @Override
    public void updateCartNum(Cart cart) {
        System.out.println(cart);
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum()).setUpdated(new Date());
        UpdateWrapper<Cart> cartUpdateWrapper = new UpdateWrapper<>();
        cartUpdateWrapper.set("num",cart.getNum());
        cartUpdateWrapper.eq("item_id",cart.getItemId()).eq("user_id",cart.getUserId());
        cartMapper.update(cartTemp,cartUpdateWrapper);
    }

    /**
     * 1.第一次加购入库
     * 2.第N次加购更新数据库
     * @param cart
     */
    @Transactional
    @Override
    public void saveCart(Cart cart) {
        System.out.println(cart);
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",cart.getUserId()).eq("item_id",cart.getItemId());
        Cart cartDB = cartMapper.selectOne(cartQueryWrapper);
        if (cartDB==null){
            cart.setCreated(new Date()).setUpdated(cart.getCreated());
            cartMapper.insert(cart);
        }else {
            //cartDB.setUpdated(new Date());
            Cart cartTemp = new Cart();
            cartTemp.setNum(cart.getNum()+cartDB.getNum()).setId(cartDB.getId()).setUpdated(new Date());
            //BeanUtils.copyProperties(cart,cartDB,"itemPrice","itemImage","itemTitle","itemId","userId","created","updated");
            //cartDB.setNum(cart.getNum()+cartDB.getNum());
            //cartDB.setUpdated(new Date());
            cartMapper.updateById(cartTemp);
        }
    }

    /**
     * 根据已知条件删除购物车商品信息
     * @param userId
     * @param itemId
     */
    @Override
    public void deleteCartByItemId(Long userId, Long itemId) {
        //1.根据对选哪个信息直接进行删除
        Cart cart = new Cart();
        cart.setItemId(itemId).setUserId(userId);
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>(cart);
        cartMapper.delete(cartQueryWrapper);
        //2.删除方式二
        //Map<String,Object> deleteMap = new HashMap<>();
        //deleteMap.put("user_id",userId);
        //deleteMap.put("item_id",itemId);
        //cartMapper.deleteByMap(deleteMap);
        //3.删除方式三
        //QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        //cartQueryWrapper.eq("user_id",userId).eq("item_id",itemId);
        //cartMapper.delete(cartQueryWrapper);
    }


}
