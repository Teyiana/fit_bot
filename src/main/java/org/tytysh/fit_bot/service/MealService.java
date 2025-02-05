package org.tytysh.fit_bot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tytysh.fit_bot.dao.MealRepository;
import org.tytysh.fit_bot.dto.DtoToEntityMapper;
import org.tytysh.fit_bot.dto.MealDTO;
import org.tytysh.fit_bot.entity.Meal;


import java.util.List;
import java.util.stream.Stream;

@Service
public class MealService implements MealServiceIf {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    @Transactional
    public List<MealDTO> getMeals(long userId) {
        Stream<Meal> mealsStream = mealRepository.findByUserId(userId);
        return mealsStream.map(DtoToEntityMapper::toDto).toList();
    }



}
