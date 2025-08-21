package com.example.OnlineFoodOrderingSystem.request;

import com.example.OnlineFoodOrderingSystem.entities.Address;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private long restaurantId;
    private Address deliveryAddress;
}
