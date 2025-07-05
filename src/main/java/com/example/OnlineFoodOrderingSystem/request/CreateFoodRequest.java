package com.example.OnlineFoodOrderingSystem.request;

import com.example.OnlineFoodOrderingSystem.entities.Category;
import com.example.OnlineFoodOrderingSystem.entities.IngredientsItem;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    private Category foodCategory;
    private List<String> images;
    private long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();

}
