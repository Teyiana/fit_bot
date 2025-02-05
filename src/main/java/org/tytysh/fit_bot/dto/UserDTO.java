package org.tytysh.fit_bot.dto;


import lombok.Data;
import org.tytysh.fit_bot.entity.Sex;

import java.sql.Date;
import java.util.Locale;

@Data
public class UserDTO {
    private long userId;
    private String chatId;

    private String email;

    private String name;
    private Date dateOfBirth;
    private Sex sex;
    private int weight;

    private Locale locale = Locale.forLanguageTag("ua");

    public UserDTO(long userId) {
        this.userId = userId;

    }

    public boolean isFill() {
        return getEmail() !=null && getDateOfBirth() != null && getSex() != null && getWeight() > 0;
    }
}
