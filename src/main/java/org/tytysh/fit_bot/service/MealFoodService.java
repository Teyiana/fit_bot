package org.tytysh.fit_bot.service;

import org.springframework.stereotype.Service;
import org.tytysh.fit_bot.dao.MealFoodRepository;
import org.tytysh.fit_bot.dao.MealRepository;
import org.tytysh.fit_bot.dao.ProductRepository;
import org.tytysh.fit_bot.dto.DtoToEntityMapper;
import org.tytysh.fit_bot.dto.MealFoodDTO;
import org.tytysh.fit_bot.entity.MealFood;

import java.util.List;

@Service
public class MealFoodService {
    private final MealFoodRepository mealFoodRepository;
    private final ProductRepository productRepository;
    private final MealRepository mealRepository;

    public MealFoodService(MealFoodRepository mealFoodRepository, ProductRepository productRepository, MealRepository mealRepository) {
        this.mealFoodRepository = mealFoodRepository;
        this.productRepository = productRepository;
        this.mealRepository = mealRepository;
    }

    public MealFoodDTO addMealFood(MealFoodDTO mealFoodDTO) {
        MealFood mealFood = new MealFood();
        mealFood.getId();
        mealFood.setMealId(mealFoodDTO.getMealId());
        mealFood.setProduct(productRepository.findById(mealFoodDTO.getProduct().getId()).orElseThrow(() -> new IllegalArgumentException("Product not found")));
        mealFood.setQuantity(mealFoodDTO.getQuantity());
        return DtoToEntityMapper.toDto(mealFoodRepository.save(mealFood));
    }

    public List<MealFoodDTO> getMealFood(long mealId) {
        return mealFoodRepository.findById(mealId).stream().map(DtoToEntityMapper::toDto).toList();
    }

    public void deleteMealFood(long mealFoodId) {
        mealFoodRepository.deleteById(mealFoodId);
    }

    public MealFoodDTO updateMealFood(MealFoodDTO mealFoodDTO) {
        MealFood mealFood = mealFoodRepository.findById(mealFoodDTO.getId()).orElseThrow(() -> new IllegalArgumentException("MealFood not found"));
        mealFood.setProduct(productRepository.findById(mealFoodDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Product not found")));
        mealFood.setQuantity(mealFoodDTO.getQuantity());
        return DtoToEntityMapper.toDto(mealFoodRepository.save(mealFood));
    }





}
