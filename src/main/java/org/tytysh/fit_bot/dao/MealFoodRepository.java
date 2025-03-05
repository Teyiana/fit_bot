package org.tytysh.fit_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tytysh.fit_bot.dto.MealFoodDTO;
import org.tytysh.fit_bot.entity.MealFood;

import java.util.stream.Stream;

public interface MealFoodRepository extends JpaRepository<MealFood, Long> {
    Stream<MealFoodDTO> findAllByMealId(long mealId);

}
