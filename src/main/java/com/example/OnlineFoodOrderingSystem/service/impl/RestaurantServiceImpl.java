package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.*;
import com.example.OnlineFoodOrderingSystem.repository.AddressRepository;
import com.example.OnlineFoodOrderingSystem.repository.RestaurantRepository;
import com.example.OnlineFoodOrderingSystem.request.RestaurantRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepo;
    private final AddressRepository addressRepo;

    @Override
    public Restaurant createRestaurant(RestaurantRequest restaurantRequest,User user) {

       Address address=  addressRepo.save(restaurantRequest.getAddress());
       return Restaurant.builder()
               .name(restaurantRequest.getName())
               .description(restaurantRequest.getDescription())
               .cuisineType(restaurantRequest.getCuisineType())
               .contactInformation(restaurantRequest.getContactInformation())
               .address(address)
               .openingHours(restaurantRequest.getOpeningHour())
               .images(restaurantRequest.getImages())
               .registrationDate(LocalDateTime.now())
               .owner(user)

               .build();
    }


    @Override
    public Restaurant updateRestaurant(long restaurantId, Restaurant updatedRestaurant) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);

        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Invalid restaurantId ");
        }

        if (restaurant.get().getCuisineType() != null) {
            restaurant.get().setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (restaurant.get().getDescription() != null) {
            restaurant.get().setDescription(updatedRestaurant.getDescription());
        }
        if (restaurant.get().getName() != null) {
            restaurant.get().setName(updatedRestaurant.getName());
        }

        return restaurantRepo.save(restaurant.get());
    }

    @Override
    public Restaurant deleteRestaurant(long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);

        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException()
        }
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return List.of();
    }

    @Override
    public List<Restaurant> searchRestaurant(long restaurantId, Restaurant updatedRestaurant) {
        return List.of();
    }

    @Override
    public Restaurant getRestaurantById(long restaurantId) {
        return null;
    }

    @Override
    public Restaurant getRestaurantByUserId(long userId) {
        return null;
    }

    @Override
    public Restaurant addToFavourite(long restaurantId, User user) {
        return null;
    }

    @Override
    public Restaurant updateRestaurantStatus(long restaurantId) {
        return null;
    }


}
