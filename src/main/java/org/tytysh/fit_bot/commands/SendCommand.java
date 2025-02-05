package org.tytysh.fit_bot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendCommand extends Command {

    default SendMessage.SendMessageBuilder createSendMethodBuilder(String chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.chatId(chatId);
        return builder;
    }

}
