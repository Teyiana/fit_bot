package org.tytysh.fit_bot.dto;

import org.tytysh.fit_bot.entity.*;


import java.util.Locale;

public interface DtoToEntityMapper {
     static UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO(entity.getUserId());
        dto.setChatId(entity.getChatId());
        dto.setName(entity.getUser_name());
        dto.setEmail(entity.getEmail());
        dto.setSex(entity.getSex());
        dto.setWeight(entity.getWeight());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setLocale(Locale.forLanguageTag(entity.getLocale()));
        return dto;
    }

    static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setUserId(dto.getUserId());
        entity.setChatId(dto.getChatId());
        entity.setUser_name(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setSex(dto.getSex());
        entity.setWeight(dto.getWeight());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setLocale(dto.getLocale().toString());
        return entity;

    }


    static MealDTO toDto(Meal entity) {
        if (entity == null) {
            return null;
        }
        MealDTO mealDTO = new MealDTO();
        mealDTO.setMealId(entity.getId());
        mealDTO.setUserId(entity.getUserId());
        mealDTO.setDate(entity.getTimeOfMeal());
        mealDTO.setMealFoods(entity.getMealFoods().stream().map(DtoToEntityMapper::toDto).toList());
        return mealDTO;
    }

    static Meal toEntity(MealDTO mealDTO) {
        Meal meal = new Meal();
        meal.setId(mealDTO.getMealId());
        meal.setUserId(mealDTO.getUserId());
        meal.setTimeOfMeal(mealDTO.getDate());
        meal.setMealFoods(mealDTO.getMealFoods().stream().map(DtoToEntityMapper::toEntity).toList());
        return meal;
    }

    static MealFoodDTO toDto(MealFood mealFood) {
        MealFoodDTO mealFoodDTO = new MealFoodDTO();
        mealFoodDTO.setMealId(mealFood.getMealId());
        mealFoodDTO.setId(mealFood.getId());
        mealFoodDTO.setProduct(DtoToEntityMapper.toDto(mealFood.getProduct()));
        mealFoodDTO.setQuantity(mealFood.getQuantity());
        return mealFoodDTO;
    }

    static DishDTO toDto(Dish entity) {
        if (entity == null) {
            return null;
        }
        DishDTO dto = new DishDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPortions(entity.getPortions().stream().map(DtoToEntityMapper::toDto).toList());
        return dto;
    }

    static Dish toEntity(DishDTO dishDTO) {
        Dish dish = new Dish();
        dish.setId(dishDTO.getId());
        dish.setName(dishDTO.getName());
        dish.setPortions(dishDTO.getPortions().stream().map(DtoToEntityMapper::toEntity).toList());
        return dish;
    }



    static ProductDTO toDto(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCalories(entity.getCalories());
        dto.setProtein(entity.getProtein());
        dto.setFat(entity.getFat());
        dto.setCarbohydrates(entity.getCarbohydrates());
        return dto;
    }

    static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCalories(productDTO.getCalories());
        product.setProtein(productDTO.getProtein());
        product.setFat(productDTO.getFat());
        product.setCarbohydrates(productDTO.getCarbohydrates());
        return product;
    }

    static MealFood toEntity(MealFoodDTO mealFoodDTO) {
        MealFood mealFood = new MealFood();
        mealFood.setId(mealFoodDTO.getId());
        mealFood.setMealId(mealFoodDTO.getMealId());
        mealFood.setProduct(mealFoodDTO.getProduct() != null ? DtoToEntityMapper.toEntity(mealFoodDTO.getProduct()) : null);
        mealFood.setQuantity(mealFoodDTO.getQuantity());
        return mealFood;

    }

    static FoodTypeDTO toDto(FoodType foodType) {
        FoodTypeDTO foodTypeDTO = new FoodTypeDTO();
        foodTypeDTO.setId(foodType.getId());
        foodTypeDTO.setName(foodType.getName());
        return foodTypeDTO;
    }

    static Portion toEntity(PortionDTO portionDTO) {
        Portion portion = new Portion();
        portion.setId(portionDTO.getId());
        portion.setDishId(portionDTO.getDishId());
        portion.setProduct(portionDTO.getProduct() != null ? DtoToEntityMapper.toEntity(portionDTO.getProduct()) : null);
        portion.setQuantity(portionDTO.getQuantity());
        return portion;
    }

    static PortionDTO toDto(Portion portion) {
        PortionDTO portionDTO = new PortionDTO();
        portionDTO.setId(portion.getId());
        portionDTO.setDishId(portion.getDishId());
        portionDTO.setProduct(DtoToEntityMapper.toDto(portion.getProduct()));
        portionDTO.setQuantity(portion.getQuantity());
        return portionDTO;
    }

}
