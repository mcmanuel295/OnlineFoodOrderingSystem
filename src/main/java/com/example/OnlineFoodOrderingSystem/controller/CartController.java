package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Cart;
import com.example.OnlineFoodOrderingSystem.entities.CartItem;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.request.AddCartItemRequest;
import com.example.OnlineFoodOrderingSystem.request.UpdateCartItemRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.CartService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/cart/add")
    public ResponseEntity<CartItem> addItemsToCart(@RequestBody AddCartItemRequest addCartItemRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem =cartService.addItemToCart(addCartItemRequest,jwt);
        if( cartItem ==null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> UpdateCartItemQuantity(@RequestBody UpdateCartItemRequest cartItemRequest) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(cartItemRequest.getCartItemId(),cartItemRequest.getQuantity());
        if( cartItem ==null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }


    @DeleteMapping("/cart-item/remove")
    public ResponseEntity<Cart> RemoveCartItemQuantity(@PathVariable long cartItemId,@RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(cartItemId,jwt);
        if( cart ==null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }



    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.clearCart(jwt);
        if( cart ==null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }



    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart( @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cartItem =cartService.findCartByUserId(user.getUserId());
        if( cartItem ==null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<Cart> findCartById(@PathVariable long cartId) throws Exception {
        Cart cart = cartService.findCartById(cartId);
        if( cart ==null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }



    @GetMapping("/cart/total")
    public ResponseEntity<Long> clearCart(@RequestBody Cart cart) throws Exception {
        long total = cartService.calculateCartTotal(cart);
        return new ResponseEntity<>(total,HttpStatus.OK);
    }

}
