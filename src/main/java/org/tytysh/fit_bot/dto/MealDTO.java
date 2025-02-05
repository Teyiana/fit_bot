package org.tytysh.fit_bot.dto;


import lombok.Data;
import org.tytysh.fit_bot.entity.Meal;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MealDTO {
    private long mealId;

    private long userId;

    private Timestamp date;

    private List<MealFoodDTO> mealFoods;


}
