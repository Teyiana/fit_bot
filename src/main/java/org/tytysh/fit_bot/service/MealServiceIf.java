package org.tytysh.fit_bot.service;


import org.tytysh.fit_bot.dto.MealDTO;

import java.util.List;


public interface MealServiceIf {
    List<MealDTO> getMeals(long userId);
}
