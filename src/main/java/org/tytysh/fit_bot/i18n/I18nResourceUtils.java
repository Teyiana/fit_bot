package org.tytysh.fit_bot.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class I18nResourceUtils {

    private static Locale defaultLocale = Locale.getDefault();

    private static String defaultBundleName = "i18n";

    private static final Map<String, I18nBundle> bundleMap = new HashMap<>();
    private static boolean cacheResources = false;

    public static Locale getDefaultLocale() {
        return defaultLocale;
    }

    public static void setDefaultLocale(Locale locale) {
        I18nResourceUtils.defaultLocale = Objects.requireNonNull(locale);
    }

    public static void setDefaultLocale(String languageTag) {
        I18nResourceUtils.defaultLocale = Locale.forLanguageTag(Objects.requireNonNull(languageTag));
    }

    public static String getDefaultBundleName() {
        return defaultBundleName;
    }

    public static void setDefaultBundleName(String defaultBundleName) {
        I18nResourceUtils.defaultBundleName = Objects.requireNonNull(defaultBundleName);
    }

    public static I18nBundle getDefaultBundle() {
        return getBundle(getDefaultBundleName());
    }

    public static I18nBundle getBundle(String bundleName) {
        return bundleMap.computeIfAbsent(Objects.requireNonNull(bundleName), I18nBundle::new);
    }

    public static String getMessage(String messageKey) {
        return getMessage(getDefaultBundle(), messageKey);
    }

    public static String getMessage(I18nBundle bundle, String messageKey) {
        return getMessage(bundle, getDefaultLocale(), messageKey);
    }
    public static String getMessage(I18nBundle bundle, Locale locale, String messageKey) {
        bundle.setCached(cacheResources);
        return bundle.getMessage(locale, messageKey);
    }

    public static void setCacheResources(boolean cacheResources) {
        I18nResourceUtils.cacheResources = cacheResources;
    }
}
