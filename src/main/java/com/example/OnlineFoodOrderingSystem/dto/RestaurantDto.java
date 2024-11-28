package com.example.OnlineFoodOrderingSystem.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto{
    private Long id;

    private String title;

    @Column(length = 100)
    private List<String> images;

    private String description;


}
