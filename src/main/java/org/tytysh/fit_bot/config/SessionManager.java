package org.tytysh.fit_bot.config;

import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.service.UserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final UserService userService;

    private Map<String, ChatSession> sessionCache;

    public SessionManager(UserService userService) {
        this.userService = userService;
        this.sessionCache = new ConcurrentHashMap<>();
    }

    public ChatSession getSession(String chatId) {
        return getOrCreateSession(chatId);
    }

    private ChatSession getOrCreateSession(String chatId) {
        sessionCache.computeIfAbsent(chatId, ChatSession::new);
        return sessionCache.get(chatId);
    }


//    public void removeSession(String chatId) {
//        sessionCache.remove(chatId);
//    }

}
