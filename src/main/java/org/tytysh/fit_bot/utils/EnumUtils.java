package org.tytysh.fit_bot.utils;

public class EnumUtils {

    public static <T extends Enum<T>> boolean isOneOf(T value, T...values) {
        if (values != null && value != null) {
            for (T v : values) {
                if (v == value) return  true;
            }
        }
        return false;
    }
}
