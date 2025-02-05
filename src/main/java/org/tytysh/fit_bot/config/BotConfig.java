package org.tytysh.fit_bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class BotConfig {

    @Value("${bot.name}")
    @Getter
    private String botName;

    @Value("${bot.token}")
    @Getter
    private String botToken;

}
