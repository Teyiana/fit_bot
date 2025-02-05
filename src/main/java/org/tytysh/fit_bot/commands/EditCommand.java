package org.tytysh.fit_bot.commands;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface EditCommand extends Command{

        default EditMessageText.EditMessageTextBuilder createEditMethodBuilder(String chatId, Integer messageId){
            EditMessageText.EditMessageTextBuilder builder = EditMessageText.builder();
            builder.chatId(chatId);
            builder.messageId(messageId);
            return builder;
        }
}
