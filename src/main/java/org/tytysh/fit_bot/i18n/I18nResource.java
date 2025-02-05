package org.tytysh.fit_bot.i18n;

import java.util.Locale;
import java.util.Objects;

public class I18nResource {

    private final I18nBundle bundle;
    private final String messageKey;
    private final String defaultMessage;

    public I18nResource(I18nBundle bundle, String messageKey, String defaultMessage){
        this.bundle = Objects.requireNonNull(bundle, "I18nBundle cant be null!!!");
        this.messageKey = messageKey;
        this.defaultMessage = defaultMessage;
    }

    public I18nResource(String messageKey, String defaultMessage){
        this(I18nResourceUtils.getDefaultBundle(), messageKey, defaultMessage);
    }

    public I18nResource(String messageKeyPrefix, String messageKeySuffix, String defaultMessage){
        this(String.format("%s.%s", messageKeyPrefix, messageKeySuffix), defaultMessage);
    }

    public I18nResource(String bundleName, String messageKeyPrefix, String messageKeySuffix, String defaultMessage){
        this(I18nResourceUtils.getBundle(bundleName), String.format("%s.%s", messageKeyPrefix, messageKeySuffix), defaultMessage);
    }
    public I18nResource(Class<?> messageKeyClassPrefix, String messageKeySuffix, String defaultMessage){
       this(messageKeyClassPrefix.getName(), messageKeySuffix, defaultMessage);
    }

    public I18nResource(String bundleName, Class<?> messageKeyClassPrefix, String messageKeySuffix, String defaultMessage){
        this(bundleName, messageKeyClassPrefix.getName(), messageKeySuffix, defaultMessage);
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getMessage() {
        return getLocalizedMessage(null);
    }

    public String getLocalizedMessage() {
        return getLocalizedMessage(I18nResourceUtils.getDefaultLocale());
    }
    public String getLocalizedMessage(Locale locale) {
        String msgKey = getMessageKey();
        if (msgKey != null && !msgKey.isBlank()){
            String localizedMessage = I18nResourceUtils.getMessage(bundle, locale, msgKey);
            if (msgKey.equals(localizedMessage)){
                String defMdg = getDefaultMessage();
                return defMdg == null ? msgKey : getDefaultMessage();
            }
            return localizedMessage;
        }
        return getDefaultMessage();
    }
}
