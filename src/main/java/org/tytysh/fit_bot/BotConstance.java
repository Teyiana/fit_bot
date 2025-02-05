package org.tytysh.fit_bot;

import java.util.regex.Pattern;

public class BotConstance {
    public static final Pattern  PATTERN_EMAIL =  Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    public static final Pattern PATTERN_DATE_OF_BIRTH = Pattern.compile("^\\d{4}\\-(0[1-9]|1[0-2])\\-(0[1-9]|[12][0-9]|3[01])$");



 public static final  int MAX_MESSAGE_HISTORY = 10;



    public static final String COMMAND_NOT_FOUND = "Вибачте, команду не знайдено!\nСпробуйте знову";
    public static final String EMOJI_CHECKED = "\u2705";
    public static final String BUTTON_TITLE_BACK = "\uD83D\uDD19 Назад";
    public static final String BUTTON_TITLE_HOME = "\uD83C\uDFE1 Додому";


}
