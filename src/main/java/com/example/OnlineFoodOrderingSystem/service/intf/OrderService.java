package com.example.OnlineFoodOrderingSystem.service.intf;

import com.example.OnlineFoodOrderingSystem.entities.Order;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {
    Order crateOrder(CreateOrderRequest orderRequest, User user) throws Exception;

    Order updateOrder(long orderId,String orderStatus) throws Exception;

    void cancelOrder(long orderId)throws Exception;

    List<Order> getUsersOrder(String jwt) throws Exception;

    List<Order> getRestuarantOrder(long restaurantId,String orderStatus)throws Exception;
}
