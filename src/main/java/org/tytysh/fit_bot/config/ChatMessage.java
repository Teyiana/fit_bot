package org.tytysh.fit_bot.config;

import lombok.Data;
import org.tytysh.fit_bot.dto.InlineButton;

import java.util.List;

@Data

public class ChatMessage {

    private String chatId;
    private Integer messageId;
    private String message;
    private String responseMessage;
    private List<List<InlineButton>> keyboard;
    private MessageState messageState;

}
