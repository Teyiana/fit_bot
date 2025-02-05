package org.tytysh.fit_bot.service;

import org.tytysh.fit_bot.dao.MealFoodRepository;
import org.tytysh.fit_bot.dao.MealRepository;
import org.tytysh.fit_bot.dao.ProductRepository;
import org.tytysh.fit_bot.dto.DtoToEntityMapper;
import org.tytysh.fit_bot.dto.MealFoodDTO;
import org.tytysh.fit_bot.entity.MealFood;

import java.util.List;

public class MealFoodService {
    private final MealFoodRepository mealFoodRepository;
    private final ProductRepository productRepository;
    private final MealRepository mealRepository;

    public MealFoodService(MealFoodRepository mealFoodRepository, ProductRepository productRepository, MealRepository mealRepository) {
        this.mealFoodRepository = mealFoodRepository;
        this.productRepository = productRepository;
        this.mealRepository = mealRepository;
    }



//    public MealFoodDTO addMealFood(MealFoodDTO mealFoodDTO) {
//        MealFood mealFood = new MealFood();
//        mealFood.getId();
//        mealFood.setMealId(mealFoodDTO.getMealId());
//        mealFood.setProduct(productRepository.findById(mealFoodDTO.getProduct().getId()).orElseThrow(() -> new IllegalArgumentException("Product not found")));
//        mealFood.setQuantity(mealFoodDTO.getQuantity());
//        return DtoToEntityMapper.toDto(mealFoodRepository.save(mealFood));
//    }
//
//    public List<MealFoodDTO> getMealFoodByMealId(long mealId) {
//        List<MealFood> mealFoods = mealFoodRepository.findAllByMealId(mealId);
//
//        return mealFoods.stream().map(DtoToEntityMapper::toDto).toList();
//
//
//    }
//
//    public void deleteMealFood(long id) {
//        mealFoodRepository.deleteById(id);
//    }
//
//    public List<MealFoodDTO> getMealFoodByMealIdAndProductType(long mealId, long typeId) {
//        return mealFoodRepository.findAllByMealIdAndProductTypeId(mealId, typeId).stream().map(MealFoodDTO::from).collect(Collectors.toList());
//    }
//
//    public List<MealFoodDTO> getMealFoodByMealIdAndProductName(long mealId, String name) {
//        return mealFoodRepository.findAllByMealIdAndProductName(mealId, name).stream().map(MealFoodDTO::from).collect(Collectors.toList());
//    }
//
//    public List<MealFoodDTO> getMealFoodByMealIdAndProductNameAndProductType(long mealId, String name, long typeId) {
//        return mealFoodRepository.findAllByMealIdAndProductNameAndProductTypeId(mealId, name, typeId).stream().map(MealFoodDTO::from).collect(Collectors.toList());
//    }
//
//    public List<MealFoodDTO> getMealFoodByMealIdAndProductTypeAndProductName(long mealId, long typeId, String name) {
//        return mealFoodRepository.findAllByMealIdAndProductTypeIdAndProductName(mealId, typeId, name).stream().map(Meal FoodDTO::from).collect(Collectors.toList());
//    }

}
