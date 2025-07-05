package com.example.OnlineFoodOrderingSystem.request;

import com.example.OnlineFoodOrderingSystem.entities.Address;
import com.example.OnlineFoodOrderingSystem.pojo.ContactInformation;
import lombok.Data;
import java.util.List;

@Data
public class RestaurantRequest {

    private long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHour;
    private List<String> images;
}
