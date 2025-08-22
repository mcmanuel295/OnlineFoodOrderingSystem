package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Order;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.request.CreateOrderRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.OrderService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/")
    ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest,
                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(createOrderRequest,user);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }


    @GetMapping("/user")
    ResponseEntity<Order> getOrdersHisotry(@RequestBody CreateOrderRequest createOrderRequest,
                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(createOrderRequest,user);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

}
