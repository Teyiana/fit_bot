package org.tytysh.fit_bot.commands;


import org.tytysh.fit_bot.config.ChatMessage;
import org.tytysh.fit_bot.config.ChatSession;
import org.tytysh.fit_bot.dto.InlineButton;

import java.util.List;
import java.util.Map;


public interface Command {

    String CMD_ARG_SYMBOL = "?";
    String CMD_ARG_SEPARATOR = "&";
    String CMD_ARG_EQUALS = "=";

    /**
     * Execute command. Call when command button clicked or command message received
     *
     * @param chatSession  chat session
     * @param messageData  message data - contain pairs of key and value of parameters in command message query
     */
    void execute(ChatSession chatSession, Map<String, String> messageData);

    /**
     * Return a button that represents this command with message of callback query
     *
     * @param chatSession chat session
     * @return button
     */
    InlineButton getButton(ChatSession chatSession);

    /**
     * Return command name aka command base query
     *
     * @return command name
     */
    String getCommandName();



    default void setResponseMessage(ChatSession chatSession, String message, List<List<InlineButton>> keyboard) {
        ChatMessage chatMessage = chatSession.getLastChatMessage();
        if (chatSession.isMessageProcessed(chatMessage)) {
            return;
        }
        chatSession.addResponseMessage(message, chatMessage);
        chatMessage.setKeyboard(keyboard);
    }

    default InlineButton createButton(String text, String callbackData) {
        InlineButton button = new InlineButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    default String createCallback(Map<String, Object> params) {
        StringBuilder callback = new StringBuilder(getCommandName());
        if (!params.isEmpty()) {
            callback.append(CMD_ARG_SYMBOL);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                callback.append(entry.getKey()).append(CMD_ARG_EQUALS).append(entry.getValue()).append(CMD_ARG_SEPARATOR);
            }
            callback.deleteCharAt(callback.length() - CMD_ARG_SEPARATOR.length());
        }
        return callback.toString();
    }
}

