package org.tytysh.fit_bot.dto;


import lombok.Data;

@Data
public class MealFoodDTO {
    private long Id;

    private long mealId;

    private ProductDTO product;

    private int quantity;

}
