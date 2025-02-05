package org.tytysh.fit_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tytysh.fit_bot.dto.MealFoodDTO;
import org.tytysh.fit_bot.entity.Meal;

import java.util.Arrays;
import java.util.stream.Stream;

public interface MealFoodRepository extends JpaRepository<Meal, Long> {
    Stream<MealFoodDTO> findAllByMealId(long mealId);
}
