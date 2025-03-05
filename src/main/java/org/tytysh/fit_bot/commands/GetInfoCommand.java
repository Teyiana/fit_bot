package org.tytysh.fit_bot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.tytysh.fit_bot.config.ChatMessage;
import org.tytysh.fit_bot.config.ChatSession;
import org.tytysh.fit_bot.dto.InlineButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class GetInfoCommand implements SendCommand {

    public static final String COMMAND_NAME = "/getInfo";
    public static final String BUTTON_TEXT = "Get info";


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
        List<InlineButton> buttons = getButtons();
        return buttons.isEmpty() ? null : buttons.get(0);
    }

    @Override
    public void setResponseMessage(ChatSession chatSession, String message, List<List<InlineButton>> keyboard) {
        SendCommand.super.setResponseMessage(chatSession, message, keyboard);
    }


    @Override
    public String createCallback(Map<String, Object> params) {
        return SendCommand.super.createCallback(params);
    }


    public static List<InlineButton> getButtons() {
        InlineButton button = new InlineButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public static List<List<InlineButton>> getKeyboard() {
        List<List<InlineButton>> keyboard = new ArrayList<>();

        List<InlineButton> infoButtons = getButtons();
        if (!infoButtons.isEmpty()) {
            keyboard.add(Collections.singletonList(infoButtons.get(0)));
        }


        return keyboard;
    }

    @Override
    public SendMessage.SendMessageBuilder createSendMethodBuilder(String chatId) {
        return SendCommand.super.createSendMethodBuilder(chatId);
    }
}
