package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.Order;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.repository.OrderRepository;
import com.example.OnlineFoodOrderingSystem.request.CreateOrderRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.OrderService;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final UserService userService;
    private final RestaurantService restaurantService;


    @Override
    public Order crateOrder(CreateOrderRequest orderRequest, User user) throws Exception {
        Order order = new Order();

        order.setCustomer(user);
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        Restaurant restaurant = restaurantService.getRestaurantById(orderRequest.getRestaurantId());
        order.setRestaurant(restaurant);
        order.setCreatedAt(new Date(System.currentTimeMillis()));

        order.setOrderStatus("Pending");

        return orderRepo.save(order);
    }

    @Override
    public Order updateOrder(long orderId, String orderStatus) throws Exception {
        Optional<Order> order =orderRepo.findById(orderId);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }

        order.get().setOrderStatus(orderStatus);
        return orderRepo.save(order.get());
    }

    @Override
    public void cancelOrder(long orderId) throws Exception {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }
        order.get().setOrderStatus("canceled");

    }

    @Override
    public List<Order> getUsersOrder(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.getOrder();
    }

    @Override
    public List<Order> getRestuarantOrder(long restaurantId, String orderStatus) throws Exception {
        Restaurant restaurant= restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant not found");
        }
        return orderRepo.getRestaurantOrderByStatus(restaurant,orderStatus);
    }
}
