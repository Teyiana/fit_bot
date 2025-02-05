package org.tytysh.fit_bot.i18n;

public interface ResponseMessages {

    String bundleName = ResponseMessages.class.getName();
    I18nResource PLEASE_REGISTER = new I18nResource(bundleName, ResponseMessages.class, "PLEASE_REGISTER", "You are not registered. Please register user");
    I18nResource ENTER_NAME = new I18nResource(bundleName, ResponseMessages.class, "ENTER_NAME", "Please enter name:");
    I18nResource ENTER_EMAIL = new I18nResource(bundleName, ResponseMessages.class, "ENTER_EMAIL", "Please enter email:");
    I18nResource ENTER_DATE_OF_BERTH = new I18nResource(bundleName, ResponseMessages.class, "ENTER_DATE_OF_BERTH", "Please enter date of birth (yyyy-MM-dd format):");
    I18nResource ENTER_WEIGHT = new I18nResource(bundleName, ResponseMessages.class, "ENTER_WEIGHT", "Please enter weight:");
    I18nResource ENTER_SEX = new I18nResource(bundleName, ResponseMessages.class, "ENTER_SEX", "Please enter sex:");
    I18nResource USER_REGISTRATION_COMPLETE = new I18nResource(bundleName, ResponseMessages.class, "USER_REGISTRATION_COMPLETE", "Thank you for registration");
}
