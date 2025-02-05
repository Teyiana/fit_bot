package org.tytysh.fit_bot.commands;

import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.config.ChatMessage;
import org.tytysh.fit_bot.config.ChatSession;
import org.tytysh.fit_bot.dto.InlineButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class StartCommand implements SendCommand {

    public static final String COMMAND_NAME = "/start";
    public static final String GREETINGS_TEXT = "Ласкаво просимо. Цей бот допоможе відслідковувати кількість вжитих калорій";
    private static final String BUTTON_TEXT = "Почати";

    private final GetInfoCommand getInfoCommand;
    private final AddMealCommand addMealCommand;

    public StartCommand(GetInfoCommand getInfoCommand, AddMealCommand addMealCommand) {
        this.getInfoCommand = getInfoCommand;
        this.addMealCommand = addMealCommand;
    }

    @Override
    public void execute(ChatSession chatSession, Map<String, String> messageData) {
        setResponseMessage(chatSession, GREETINGS_TEXT, getKeyboard(chatSession));
    }

    @Override
    public InlineButton getButton(ChatSession chatSession) {
        InlineButton button = new InlineButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return button;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public List<List<InlineButton>> getKeyboard(ChatSession chatSession) {
        List<List<InlineButton>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList(getInfoCommand.getButton(chatSession)));
        keyboard.add(Collections.singletonList(addMealCommand.getButton(chatSession)));
        return keyboard;
    }
}
