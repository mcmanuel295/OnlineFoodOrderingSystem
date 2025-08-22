package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Order;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.service.intf.OrderService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/order/restaurant/{id}")
    ResponseEntity<List<Order>> createOrder(@PathVariable long restaurantId,@RequestParam(required = false) String orderStatus, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getRestuarantOrder(restaurantId,orderStatus);

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }


    @PutMapping("/order/{orderId}/{orderStatus}")
    ResponseEntity<Order> updateOrderStatus(@PathVariable long orderId, @RequestParam(required = false) String orderStatus) throws Exception {
        Order order = orderService.updateOrder(orderId,orderStatus);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

}

