package org.tytysh.fit_bot.i18n;

import java.util.*;

public class I18nBundle {

    private boolean cached = false;
    private final String bundleName;
    private Map<Locale, ResourceBundle> bundleMap;

    protected I18nBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    protected String getMessage(Locale locale, String messageKey) {
        return getMessage(locale, messageKey, true);
    }

    private String getMessage(Locale locale, String messageKey, boolean lookupDefault) {
        try {
            ResourceBundle resourceBundle = getResourceBundle(locale);
            return resourceBundle.getString(messageKey);
        } catch (MissingResourceException mre){
            if (lookupDefault){
                return getMessage(null, messageKey, false);
            }
            return messageKey;
        }
    }

    protected void setCached(boolean cached){
        if (this.cached != cached) {
            if (cached) bundleMap = new HashMap<>();
            else bundleMap = null;
        }
        this.cached = cached;
    }

    private ResourceBundle getResourceBundle(Locale locale) {
        if (cached){
            return bundleMap.computeIfAbsent(locale, this::loadBundle);
        } else {
            return loadBundle(locale);
        }
    }

    private ResourceBundle loadBundle(Locale locale){
        return locale == null ? ResourceBundle.getBundle(bundleName) : ResourceBundle.getBundle(bundleName, locale);
    }

}
