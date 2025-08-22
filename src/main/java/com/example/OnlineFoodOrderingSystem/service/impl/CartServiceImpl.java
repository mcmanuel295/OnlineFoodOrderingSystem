package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.Cart;
import com.example.OnlineFoodOrderingSystem.entities.CartItem;
import com.example.OnlineFoodOrderingSystem.entities.Food;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.repository.CartItemRepository;
import com.example.OnlineFoodOrderingSystem.repository.CartRepository;
import com.example.OnlineFoodOrderingSystem.request.AddCartItemRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.CartService;
import com.example.OnlineFoodOrderingSystem.service.intf.FoodService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepo;
    private final UserService userService;
    private final CartItemRepository cartItemRepo;
    private final FoodService foodService;


    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user =userService.findUserByJwtToken(jwt);
        Food food = foodService.findById(req.getFoodId());

        Cart cart = cartRepo.findByUserId(user.getUserId());

        for (CartItem cartItem: cart.getCartItems()) {
            if (cartItem.getFood()==food){
                int quant = cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),quant);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        cart.getCartItems().add(newCartItem);

        return cartItemRepo.save(newCartItem);
    }

    @Override
    public Cart findCartById(long cartId) throws Exception {
        Cart cart = cartRepo.findByUserId(cartId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found");
        }
        return cart;
    }


    @Override
    public Cart findCartByUserId(long userId) throws Exception {
        Cart cart = cartRepo.findByUserId(userId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found");
        }
        return cart;
    }



    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepo.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new EntityNotFoundException("Cart Item not found");
        }

        cartItem.get().setQuantity(quantity);
        cartItem.get().setTotalPrice( cartItem.get().getTotalPrice()*quantity);

        return cartItemRepo.save(cartItem.get());
    }


    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart =cartRepo.findByUserId(user.getUserId());
        Optional<CartItem> cartItem = cartItemRepo.findById(cartItemId);

        if (cartItem.isEmpty()) {
            throw new EntityNotFoundException("Cart Item not found");
        }
        cart.getCartItems().remove(cartItem.get());
        return cartRepo.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        long total = 0L;
        for (CartItem cartItem: cart.getCartItems()){
            total +=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart clearCart(String jwt){
        User user = userService.findUserByJwtToken(jwt);
        Cart cart =cartRepo.findByUserId(user.getUserId());
        cart.getCartItems().clear();
        return cartRepo.save(cart);
    }
}
