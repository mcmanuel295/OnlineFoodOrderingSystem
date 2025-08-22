package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.*;
import com.example.OnlineFoodOrderingSystem.repository.AddressRepository;
import com.example.OnlineFoodOrderingSystem.repository.OrderItemsRepository;
import com.example.OnlineFoodOrderingSystem.repository.OrderRepository;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import com.example.OnlineFoodOrderingSystem.request.CreateOrderRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.CartService;
import com.example.OnlineFoodOrderingSystem.service.intf.OrderService;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemsRepository orderItemsRepo;
    private final UserRepository userRepository;
    private final RestaurantService restaurantService;
    private final AddressRepository addressRepo;
    private final CartService cartService;


    @Override
    public Order createOrder(CreateOrderRequest orderRequest, User user) throws Exception {
        Order order = new Order();

        Address savedAddress= addressRepo.save(orderRequest.getDeliveryAddress());

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.getRestaurantById(orderRequest.getRestaurantId());

        order.setCustomer(user);
        order.setCreatedAt(new Date());
        order.setOrderStatus("PENDING");
        order.setDeliveryAddress(savedAddress);
        order.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getUserId());

        List<OrderItems> orderItems = new ArrayList<>();

        for (CartItem cartItem: cart.getCartItems()){
            OrderItems orderItem = new OrderItems();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            orderItems.add(orderItemsRepo.save(orderItem));
        }

        order.setItems(orderItems);
        long total = cartService.calculateCartTotal(cart);
        order.setTotalPrice(total);
        Order savedOrder = orderRepo.save(order);
        restaurant.getOrders().add(savedOrder);
        return savedOrder;
    }

    @Override
    public Order updateOrder(long orderId, String orderStatus) throws Exception {
        Optional<Order> order =orderRepo.findById(orderId);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }

        if (orderStatus.equals("OUT_OF_BILL")|| orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED")|| orderStatus.equals("PENDING")) {
            order.get().setOrderStatus(orderStatus);
            order.get().setOrderStatus(orderStatus);
            return orderRepo.save(order.get());
        }
        throw new Exception("Please select a valid order status");

    }

    @Override
    public void cancelOrder(long orderId) throws Exception {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }
        order.get().setOrderStatus("canceled");
        orderRepo.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(long userId) throws Exception {
        orderRepo.findByCustomerId(userId);
     return  null;
    }

    @Override
    public List<Order> getRestaurantOrder(long restaurantId, String orderStatus) throws Exception {
        Restaurant restaurant= restaurantService.getRestaurantById(restaurantId);

        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant not found");
        }

        List<Order> orders = orderRepo.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders =orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).toList();
        }
        return orders;
    }

    @Override
    public Order findOrderById(long orderId) throws Exception {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }

        return order.get();
    }
}
