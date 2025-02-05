package org.tytysh.fit_bot.commands;

import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.config.ChatSession;
import org.tytysh.fit_bot.dao.MealRepository;
import org.tytysh.fit_bot.dto.InlineButton;
import org.tytysh.fit_bot.dto.MealDTO;
import org.tytysh.fit_bot.entity.Meal;
import org.tytysh.fit_bot.i18n.Buttons;
import org.tytysh.fit_bot.service.MealServiceIf;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class AddMealCommand implements SendCommand, Buttons {
    public static final String COMMAND_NAME = "/addMeal";
    private static final String PARAM_STEP = "step";
    private static final String PARAM_MEAL_ID = "mealId";

    private static final String STEP_NONE = "null";
    private static final String STEP_REPEAT_MEAL = "repeatMeal";
    private static final String STEP_ADD_NEW_MEAL = "addNewMeal";
    private static final String STEP_SELECT_DISH = "selectDish";
    private static final String STEP_REPEAT_MEAL_SELECTED = "repeatMealSelected";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final MealServiceIf mealService;
    private final MealRepository mealRepository;

    public AddMealCommand(MealServiceIf mealService,
                          MealRepository mealRepository) {
        this.mealService = mealService;
        this.mealRepository = mealRepository;
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
            setResponseMessage(chatSession, "Meal not selected", getGeneralStepsKeyboard(chatSession));
        } else {
            mealService.getMeals(chatSession.getUserDTO().getUserId());
            setResponseMessage(chatSession, "Meal repeated", getGeneralStepsKeyboard(chatSession));
        }
    }

    private void selectDish(ChatSession chatSession) {
        List<MealDTO> userMeals =  mealService.getMeals(chatSession.getUserDTO().getUserId());



    }

    private void addNewMeal(ChatSession chatSession) {
        Meal meal = new Meal();
        meal.setUserId(chatSession.getUserDTO().getUserId());
        meal.setTimeOfMeal(new Timestamp(System.currentTimeMillis()));
        mealRepository.save(meal);
        if (meal.getMealFoods().isEmpty()) {
            setResponseMessage(chatSession, "New meal not added", getGeneralStepsKeyboard(chatSession));
        } else {
            setResponseMessage(chatSession, "New meal added", getGeneralStepsKeyboard(chatSession));
        }
    }

    private void repeatMeal(ChatSession chatSession) {
        List<MealDTO> userMeals = mealService.getMeals(chatSession.getUserDTO().getUserId());
        if (userMeals.isEmpty()) {
            setResponseMessage(chatSession, "You have no meals", getGeneralStepsKeyboard(chatSession));
        } else {
            setResponseMessage(chatSession, "Select meal to repeat", getMealsListKeyboard(chatSession, userMeals));
        }
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
