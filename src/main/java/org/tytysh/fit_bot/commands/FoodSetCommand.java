package org.tytysh.fit_bot.commands;

import org.apache.commons.collections.Buffer;
import org.tytysh.fit_bot.config.ChatMessage;
import org.tytysh.fit_bot.config.ChatSession;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.tytysh.fit_bot.dto.FoodTypeDTO;
import org.tytysh.fit_bot.dto.InlineButton;
import org.tytysh.fit_bot.i18n.Buttons;

import java.util.*;

import static org.tytysh.fit_bot.BotConstance.EMOJI_CHECKED;

public class FoodSetCommand implements SendCommand, Buttons {

    public static final String COMMAND_NAME = "/setMeal";
    private static final String MEAL_ARG = "?meal=";
    public static final String BUTTON_TEXT = "Meal";



    @Override
    public void execute(ChatSession chatSession, Map<String, String> messageData) {
        ChatMessage chatMessage = chatSession.getLastChatMessage();
        if (chatSession.isMessageProcessed(chatMessage)) {
            return;
        }
        chatMessage.setResponseMessage(BUTTON_TEXT);
        chatMessage.setKeyboard(getKeyboard());
    }

    @Override
    public InlineButton getButton(ChatSession chatSession) {
        return getButtons().get(0);
    }

    private EditMessageText.EditMessageTextBuilder createEditMethodBuilder(String chatId, Integer callbackMsgId) {
        EditMessageText.EditMessageTextBuilder builder = EditMessageText.builder();
        builder.chatId(chatId);
        builder.messageId(callbackMsgId);
        return builder;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public List<InlineButton> getButtons() {
        InlineButton button = new InlineButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }

    public static List<List<InlineButton>> getKeyboard() {
        List<List<InlineButton>> keyboard = new ArrayList<>();

        List<InlineButton> infoButtons = new GetInfoCommand().getButtons();
        if (!infoButtons.isEmpty()) {
            keyboard.add(Collections.singletonList(infoButtons.get(0)));
        }

        List<InlineButton> foodButtons = new FoodSetCommand().getButtons();
        if (!foodButtons.isEmpty()) {
            keyboard.add(Collections.singletonList(foodButtons.get(0)));
        }

        return keyboard;
    }


    public static List<InlineButton> createFoodTypeButton(FoodTypeDTO foodTypeDTO) {
           InlineButton button = new InlineButton();
            button.setText(foodTypeDTO.getName());
            button.setCallbackData(COMMAND_NAME + MEAL_ARG + foodTypeDTO.getId());
            return Collections.singletonList(button);

    }



}
