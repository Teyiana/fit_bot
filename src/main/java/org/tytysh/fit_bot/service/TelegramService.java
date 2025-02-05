package org.tytysh.fit_bot.service;

import org.tytysh.fit_bot.commands.CommandService;
import org.tytysh.fit_bot.config.ChatSession;
import org.tytysh.fit_bot.config.SessionManager;
import org.springframework.stereotype.Service;

@Service
public class TelegramService {

    private final CommandService commandService;
    private final SessionManager sessionManager;
    private final UserService userService;
    private final MessageProcessingService messageProcessingService;


    public TelegramService(SessionManager sessionManager, CommandService commandService,
                           UserService userService, MessageProcessingService messageProcessingService) {
        this.sessionManager = sessionManager;
        this.commandService = commandService;
        this.userService = userService;
        this.messageProcessingService = messageProcessingService;
    }

    public ChatSession processMsg(String chatId, String msg, Integer messageId) {
        ChatSession chatSession = getSessionManager().getSession(chatId);
        chatSession.registerMessage(messageId, msg);
        processMessage(chatSession);
        return chatSession;
    }

    private void processMessage(ChatSession chatSession) {
        if (!chatSession.isUserRegistered()) {
            getUserService().processRegistration(chatSession);
        }
        if (!chatSession.isMessageProcessed(chatSession.getLastChatMessage())) {
            getCommandService().onMessage(chatSession);
        }
        if (!chatSession.isMessageProcessed(chatSession.getLastChatMessage())) {
            getMessageProcessingService().onMessage(chatSession);
        }
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public UserService getUserService() {
        return userService;
    }

    public MessageProcessingService getMessageProcessingService() {
        return messageProcessingService;
    }
}
