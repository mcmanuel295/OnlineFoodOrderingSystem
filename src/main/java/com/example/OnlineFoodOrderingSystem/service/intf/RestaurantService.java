package com.example.OnlineFoodOrderingSystem.service.intf;


import com.example.OnlineFoodOrderingSystem.dto.RestaurantDto;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.request.RestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(RestaurantRequest restaurantRequest,String jwt);

    Restaurant updateRestaurant(long restaurantId, Restaurant updatedRestaurant);

    void deleteRestaurant(long restaurantId);

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String searchKey);

    Restaurant getRestaurantById(long restaurantId)throws Exception;

    Restaurant getRestaurantByOwnerId(long userId);

    RestaurantDto addToFavourite(long restaurantId, User user);

    Restaurant updateRestaurantStatus(long restaurantId);

}