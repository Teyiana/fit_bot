package org.tytysh.fit_bot.commands;

import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.config.ChatSession;
import org.tytysh.fit_bot.dao.MealRepository;
import org.tytysh.fit_bot.dto.DishDTO;
import org.tytysh.fit_bot.dto.FoodTypeDTO;
import org.tytysh.fit_bot.dto.InlineButton;
import org.tytysh.fit_bot.dto.MealDTO;
import org.tytysh.fit_bot.entity.Meal;
import org.tytysh.fit_bot.i18n.Buttons;
import org.tytysh.fit_bot.service.DishService;
import org.tytysh.fit_bot.service.MealServiceIf;
import org.tytysh.fit_bot.service.ProductService;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.tytysh.fit_bot.i18n.ResponseMessages.*;

@Component
public class AddMealCommand implements SendCommand, Buttons {
    public static final String COMMAND_NAME = "/addMeal";
    private static final String PARAM_STEP = "step";
    private static final String PARAM_MEAL_ID = "mealId";
    private static final String PARAM_FOOD_TYPE_ID = "foodTypeId";
    private static final String STEP_NONE = "null";
    private static final String STEP_REPEAT_MEAL = "repeatMeal";
    private static final String STEP_ADD_NEW_MEAL = "addNewMeal";
    private static final String STEP_SELECT_DISH = "selectDish";
    private static final String STEP_REPEAT_MEAL_SELECTED = "repeatMealSelected";
    private static final String STEP_DISH_SELECTED = "DishSelected";
    private static final String PARAM_DISH_ID = "dishId";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final MealServiceIf mealService;
    private final MealRepository mealRepository;
    private final ProductService productService;
    private final DishService dishService;

    public AddMealCommand(MealServiceIf mealService,
                          MealRepository mealRepository, ProductService productService, DishService dishService) {
        this.mealService = mealService;
        this.mealRepository = mealRepository;
        this.productService = productService;
        this.dishService = dishService;
    }

    @Override
    public void execute(ChatSession chatSession, Map<String, String> messageData) {
        String step = messageData.get(PARAM_STEP);
        step = step == null ? STEP_NONE : step;
        switch (step) {
            case STEP_NONE -> selectMealAddType(chatSession);
            case STEP_REPEAT_MEAL -> repeatMeal(chatSession);
            case STEP_ADD_NEW_MEAL -> addNewMeal(chatSession);
            case STEP_SELECT_DISH -> selectDish(chatSession);
            case STEP_REPEAT_MEAL_SELECTED -> repeatMealSelected(chatSession, messageData);
        }
    }

    private void repeatMealSelected(ChatSession chatSession, Map<String, String> messageData) {
        String mealId = messageData.get(PARAM_MEAL_ID);
        if (mealId == null) {
            setResponseMessage(chatSession, MEAL_NOT_SELECTED.getLocalizedMessage(), getGeneralStepsKeyboard(chatSession));
        } else {
            mealService.getMeals(chatSession.getUserDTO().getUserId());
            setResponseMessage(chatSession, MEAL_REPEATED.getLocalizedMessage(), getGeneralStepsKeyboard(chatSession));
        }
    }

    private void selectDish(ChatSession chatSession) {
        List<DishDTO> userDishes =  dishService.getAllDishes();
        if (userDishes.isEmpty()) {
            setResponseMessage(chatSession, HAVE_NO_DISHES.getLocalizedMessage(), getGeneralStepsKeyboard(chatSession));
        } else {
            setResponseMessage(chatSession, SELECT_DISH_FOR_MEAL.getLocalizedMessage(), getDishesListKeyboard(chatSession, userDishes));
        }
    }

    private List<List<InlineButton>> getDishesListKeyboard(ChatSession chatSession, List<DishDTO> userDishes) {
        List<List<InlineButton>> keyboard = new ArrayList<>();
        List<InlineButton> row = new ArrayList<>();
        for (DishDTO dish : userDishes) {
            Map<String, Object> params = new HashMap<>();
            params.put(PARAM_STEP, STEP_DISH_SELECTED);
            params.put(PARAM_DISH_ID, dish.getId());
            row.add(createButton(dish.getName(), createCallback(params)));
            if (row.size() == 2) {
                keyboard.add(row);
                row = new ArrayList<>();
            }
        }
        if (!row.isEmpty()) {
            keyboard.add(row);
        }
        return keyboard;
    }



    private void addNewMeal(ChatSession chatSession) {
        Meal meal = new Meal();
        meal.setUserId(chatSession.getUserDTO().getUserId());
        meal.setTimeOfMeal(new Timestamp(System.currentTimeMillis()));
        meal.setMealFoods(new ArrayList<>());
        mealRepository.save(meal);
        if (meal.getMealFoods().isEmpty()) {
            setResponseMessage(chatSession, NEW_MEAL_NOT_ADDED.getLocalizedMessage(), getFoodTypeKeyboard(chatSession));
        } else {
            setResponseMessage(chatSession, NEW_MEAL_ADDED.getLocalizedMessage(), getGeneralStepsKeyboard(chatSession));
        }
    }

    private void repeatMeal(ChatSession chatSession) {
        List<MealDTO> userMeals = mealService.getMeals(chatSession.getUserDTO().getUserId());
        if (userMeals.isEmpty()) {
            setResponseMessage(chatSession, HAVE_NO_MEALS.getLocalizedMessage(), getGeneralStepsKeyboard(chatSession));
        } else {
            setResponseMessage(chatSession, SELECT_MEAL_TO_REPEAT.getLocalizedMessage(), getMealsListKeyboard(chatSession, userMeals));
        }
    }

    private List<List<InlineButton>>  getFoodTypeKeyboard(ChatSession chatSession) {
        List<FoodTypeDTO> foodTypes = productService.getAllFoodTypes();
        List<List<InlineButton>> keyboard = new ArrayList<>();
        List<InlineButton> row = new ArrayList<>();
        for (FoodTypeDTO foodType : foodTypes) {
            Map<String, Object> params = new HashMap<>();
            params.put(PARAM_STEP, STEP_SELECT_DISH);
            params.put(PARAM_FOOD_TYPE_ID, foodType.getId());
            row.add(createButton(foodType.getName(), createCallback(params)));
            if (row.size() == 2) {
                keyboard.add(row);
                row = new ArrayList<>();
            }
        }

        if (!row.isEmpty()) {
            keyboard.add(row);
        }
        return keyboard;
    }


    private List<List<InlineButton>> getMealsListKeyboard(ChatSession chatSession, List<MealDTO> userMeals) {
        List<List<InlineButton>> keyboard = new ArrayList<>();
        List<InlineButton> row = new ArrayList<>();
        for (MealDTO meal : userMeals) {
            Map<String, Object> params = new HashMap<>();
            params.put(PARAM_STEP, STEP_REPEAT_MEAL_SELECTED);
            params.put(PARAM_MEAL_ID, meal.getMealId());
            row.add(createButton(meal.getDate().toLocalDateTime().format(formatter), createCallback(params)));
            if (row.size() == 2) {
                keyboard.add(row);
                row = new ArrayList<>();
            }
        }
        if (!row.isEmpty()) {
            keyboard.add(row);
        }
        return keyboard;
    }

    private void selectMealAddType(ChatSession chatSession) {
        setResponseMessage(chatSession, "Please select", getGeneralStepsKeyboard(chatSession));
    }

    private List<List<InlineButton>> getGeneralStepsKeyboard(ChatSession chatSession) {
        List<List<InlineButton>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList(createButton(ADD_MEAL__REPEAT_MEAL.getLocalizedMessage(chatSession.getUserDTO().getLocale()), createCallback(Map.of(PARAM_STEP, STEP_REPEAT_MEAL)))));
        keyboard.add(Collections.singletonList(createButton(ADD_MEAL__ADD_NEW_MEAL.getLocalizedMessage(chatSession.getUserDTO().getLocale()), createCallback(Map.of(PARAM_STEP, STEP_ADD_NEW_MEAL)))));
        keyboard.add(Collections.singletonList(createButton(ADD_MEAL__SELECT_DISH.getLocalizedMessage(chatSession.getUserDTO().getLocale()), createCallback(Map.of(PARAM_STEP, STEP_SELECT_DISH)))));
        return keyboard;
    }

    @Override
    public InlineButton getButton(ChatSession chatSession) {
        return createButton(ADD_MEAL__MAIN_BUTTON.getLocalizedMessage(chatSession.getUserDTO().getLocale()), COMMAND_NAME);
    }


    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
