package org.tytysh.fit_bot.service;

import org.tytysh.fit_bot.commands.StartCommand;
import org.tytysh.fit_bot.config.ChatSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MessageProcessingService {

    private final StartCommand startCommand;

    public MessageProcessingService(StartCommand startCommand){
        this.startCommand = startCommand;
    }

    public void onMessage(ChatSession chatSession) {
        startCommand.execute(chatSession, new HashMap<>());
        chatSession.setMessageProcessed();
    }
}
