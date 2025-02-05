package org.tytysh.fit_bot.i18n;

public interface Buttons {

    String bundleName = Buttons.class.getName();
    I18nResource ADD_MEAL__MAIN_BUTTON = new I18nResource(bundleName, Buttons.class, "ADD_MEAL__MAIN_BUTTON", "Add meal");
    I18nResource ADD_MEAL__REPEAT_MEAL = new I18nResource(bundleName, Buttons.class, "ADD_MEAL__REPEAT_MEAL", "repeat meal");
    I18nResource ADD_MEAL__ADD_NEW_MEAL = new I18nResource(bundleName, Buttons.class, "ADD_MEAL__ADD_NEW_MEAL", "add new meal");
    I18nResource ADD_MEAL__SELECT_DISH = new I18nResource(bundleName, Buttons.class, "ADD_MEAL__SELECT_DISH", "select meal");
}
