package com.pinyougou.cart.service;

import com.pinyougou.pojogroup.Cart;

import java.util.List;

public interface CartService {
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num );

    /**
     * 从redis中取出购物车列表
     * @param username
     * @return
     */
    public List<Cart> findCartListFromRedis(String username);
    /**
     * 将购物车保存到 redis
     * @param username
     * @param cartList
     */
    public void saveCartListToRedis(String username,List<Cart> cartList);

    /**
     * 合并购物车
     * @param cartList1
     * @param cartList2
     * @return
     */
    public List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);
}
