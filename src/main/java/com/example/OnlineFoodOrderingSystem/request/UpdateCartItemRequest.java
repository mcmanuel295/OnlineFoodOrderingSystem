package com.example.OnlineFoodOrderingSystem.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

    private long cartItemId;
    private int quantity;

}
