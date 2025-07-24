package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.dto.RestaurantDto;
import com.example.OnlineFoodOrderingSystem.entities.*;
import com.example.OnlineFoodOrderingSystem.repository.AddressRepository;
import com.example.OnlineFoodOrderingSystem.repository.RestaurantRepository;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import com.example.OnlineFoodOrderingSystem.request.RestaurantRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepo;
    private final AddressRepository addressRepo;
    private final UserRepository userRepo;
    private final UserService userService;

    @Override
    public Restaurant createRestaurant(RestaurantRequest restaurantRequest,String jwt) {

       User user = userService.findUserByJwtToken(jwt);
        if (user == null) {
            throw new UsernameNotFoundException("User not fund!!");
        }

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
    public void deleteRestaurant(long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);

        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Restaurant not found");
        }

        restaurantRepo.deleteById(restaurantId);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String searchKey) {
        return restaurantRepo.findBySearchQuery(searchKey);
    }

    @Override
    public Restaurant getRestaurantById(long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);

        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Restaurant with id "+restaurantId+" not found");
        }
        return restaurant.get();
    }

    @Override
    public Restaurant getRestaurantByOwnerId(long userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()){
            throw new EntityNotFoundException("User with user id "+userId+" not found");
        }
        Optional<Restaurant> restaurant = restaurantRepo.findByOwner(user.get());

        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Restaurant with ownerId "+userId+" not found");
        }
        return restaurant.get();
    }

    @Override
    public RestaurantDto addToFavourite(long restaurantId, User user) {
        if (userRepo.findById(user.getUserId()).isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new UsernameNotFoundException("restaurant not found");
        }

//        Converting restaurant to dto
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurant.get().getRestaurantId());
        restaurantDto.setTitle(restaurant.get().getName());
        restaurantDto.setDescription(restaurant.get().getDescription());
        restaurantDto.setImages(restaurant.get().getImages());

        user.getFavourite().remove(restaurantDto);
        user.getFavourite().add(restaurantDto);

        userRepo.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);

        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Restaurant not found");
        }

        restaurant.get().setOpen(!restaurant.get().isOpen());
        return restaurantRepo.save(restaurant.get()  );
    }


}
