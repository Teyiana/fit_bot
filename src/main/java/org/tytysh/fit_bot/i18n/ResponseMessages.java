package org.tytysh.fit_bot.i18n;

public interface ResponseMessages {

    String bundleName = ResponseMessages.class.getName();
    I18nResource PLEASE_REGISTER = new I18nResource(bundleName, ResponseMessages.class, "PLEASE_REGISTER", "You are not registered. Please register user");
    I18nResource ENTER_NAME = new I18nResource(bundleName, ResponseMessages.class, "ENTER_NAME", "Please enter name:");
    I18nResource ENTER_EMAIL = new I18nResource(bundleName, ResponseMessages.class, "ENTER_EMAIL", "Please enter email:");
    I18nResource INVALID_EMAIL = new I18nResource(bundleName, ResponseMessages.class, "INVALID_EMAIL", "Invalid email");
    I18nResource ENTER_DATE_OF_BERTH = new I18nResource(bundleName, ResponseMessages.class, "ENTER_DATE_OF_BERTH", "Please enter date of birth (yyyy-MM-dd format):");
    I18nResource INVALID_DATE_OF_BERTH = new I18nResource(bundleName, ResponseMessages.class, "INVALID_DATE_OF_BERTH", "Invalid date of birth");
    I18nResource ENTER_WEIGHT = new I18nResource(bundleName, ResponseMessages.class, "ENTER_WEIGHT", "Please enter weight:");
    I18nResource INVALID_WEIGHT = new I18nResource(bundleName, ResponseMessages.class, "INVALID_WEIGHT", "Invalid weight");
    I18nResource ENTER_SEX = new I18nResource(bundleName, ResponseMessages.class, "ENTER_SEX", "Please enter sex:");
    I18nResource INVALID_SEX = new I18nResource(bundleName, ResponseMessages.class, "INVALID_SEX", "Invalid sex");
    I18nResource USER_REGISTRATION_COMPLETE = new I18nResource(bundleName, ResponseMessages.class, "USER_REGISTRATION_COMPLETE", "Thank you for registration");

    I18nResource MEAL_NOT_SELECTED = new I18nResource(bundleName, ResponseMessages.class, "MEAL_NOT_SELECTED", "Meal not selected");
    I18nResource MEAL_REPEATED = new I18nResource(bundleName, ResponseMessages.class, "MEAL_REPEATED", "Meal repeated");
    I18nResource HAVE_NO_MEALS = new I18nResource(bundleName, ResponseMessages.class, "HAVE_NO_MEALS", "You have no meals");
    I18nResource HAVE_NO_DISHES = new I18nResource(bundleName, ResponseMessages.class, "HAVE_NO_DISHES", "You have no dishes");
    I18nResource SELECT_DISH_FOR_MEAL = new I18nResource(bundleName, ResponseMessages.class, "SELECT_DISH_FOR_MEAL", "Select dish for meal");
    I18nResource NEW_MEAL_ADDED = new I18nResource(bundleName, ResponseMessages.class, "MEAL_ADDED", " New meal added");
    I18nResource NEW_MEAL_NOT_ADDED = new I18nResource(bundleName, ResponseMessages.class, "MEAL_NOT_ADDED", "New meal not added");
    I18nResource SELECT_MEAL_TO_REPEAT = new I18nResource(bundleName, ResponseMessages.class, "SELECT_MEAL_TO_REPEAT", "Select meal to repeat");
}
