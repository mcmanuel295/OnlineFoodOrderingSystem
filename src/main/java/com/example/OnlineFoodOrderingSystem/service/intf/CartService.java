package com.example.OnlineFoodOrderingSystem.service.intf;

import com.example.OnlineFoodOrderingSystem.entities.Cart;
import com.example.OnlineFoodOrderingSystem.entities.CartItem;
import com.example.OnlineFoodOrderingSystem.request.AddCartItemRequest;

public interface CartService {

    CartItem addItemToCart(AddCartItemRequest cart, String  jwt)throws Exception;
    Cart findCartById(long cartId)throws Exception;
    Cart findCartByUserId(String jwt)throws Exception;
    CartItem updateCartItemQuantity(Long cardItemId, int quantity)throws Exception;
    Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;
    Long calculateCartTotal(Cart cart)throws Exception;
    Cart clearCart(String jwt)throws Exception;
}
