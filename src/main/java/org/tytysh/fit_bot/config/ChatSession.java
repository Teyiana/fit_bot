package org.tytysh.fit_bot.config;

import org.tytysh.fit_bot.dto.UserDTO;
import org.tytysh.fit_bot.utils.EnumUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.tytysh.fit_bot.BotConstance.MAX_MESSAGE_HISTORY;

public class ChatSession {

    private static final MessageState[] PROCESSED_STATES = new MessageState[]{MessageState.PROCESSED, MessageState.FAILED, MessageState.FINALIZED};
    LinkedList<ChatMessage> messages = new LinkedList<>();
    private UserDTO userDTO;

    private final String chatId;

    private final Map<String, Object> context = new HashMap<>();

    public ChatSession(String chatId) {
        this.chatId = chatId;
    }

    public boolean isUserRegistered() {
        return getUserDTO() != null && getUserDTO().isFill();
    }

    public boolean isMessageProcessed(ChatMessage lastMessage) {
        if (lastMessage == null) return true;
        MessageState state = lastMessage.getMessageState();
        return EnumUtils.isOneOf(state, PROCESSED_STATES);
    }

    private void cleanupOldMessages() {
        while (messages.size() > MAX_MESSAGE_HISTORY) {
            messages.removeFirst();
        }
    }

    public void registerMessage(Integer messageId, String msg) {
        cleanupOldMessages();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatId(getChatId());
        chatMessage.setMessageId(messageId);
        chatMessage.setMessage(msg);
        chatMessage.setMessageState(MessageState.NEW);
        messages.add(chatMessage);
    }

    public void addResponseMessage(String message) {
        ChatMessage lastMessage = getLastChatMessage();
        addResponseMessage(message, lastMessage);
    }

    public void addResponseMessage(String message, ChatMessage lastMessage) {
        if (isMessageProcessed(lastMessage)) throw new IllegalStateException("No chat message for add response");
        String old = lastMessage.getResponseMessage();
        lastMessage.setResponseMessage(old == null ? message : old + "\n" + message);
    }

    public String getLastMessage() {
        if (messages.isEmpty()) return null;
        ChatMessage lastMessage = messages.getLast();
        return lastMessage.getMessage();
    }

    public ChatMessage getLastChatMessage() {
        if (messages.isEmpty()) return null;
        return messages.getLast();
    }

    public String getChatId() {
        return chatId;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setMessageProcessed() {
        ChatMessage lastMessage = getLastChatMessage();
        if (isMessageProcessed(lastMessage)) throw new IllegalStateException("Last method already processed");
        lastMessage.setMessageState(MessageState.PROCESSED);
    }

    public String  getResponseMessage() {
        ChatMessage lastMessage = getLastChatMessage();
        if (!isMessageProcessed(lastMessage)) throw new IllegalStateException("Can not retrieve response message: " +
                "the message has not yet been processed.");

        lastMessage.setMessageState(MessageState.FINALIZED);
       return lastMessage.getResponseMessage();

    }

    public void finalizeMessage() {
        ChatMessage lastMessage = getLastChatMessage();
        if (isMessageProcessed(lastMessage)) throw new IllegalStateException("The message was executed.");

        lastMessage.setMessageState(MessageState.FINALIZED);
    }

    public void failMessage(String message) {
        ChatMessage lastMessage = getLastChatMessage();
        if (isMessageProcessed(lastMessage)) throw new IllegalStateException("The message failed to execute.");
//        if (lastMessage == null || lastMessage.)
        lastMessage.setMessageState(MessageState.FAILED);
        String old = lastMessage.getResponseMessage();
        lastMessage.setResponseMessage(old == null ? message : "\n" + message);


    }

    public void setSessionContext(String key, Object value) {
        context.put(key, value);
    }

    public Object getSessionContext(String key) {
        return context.get(key);
    }

    public void removeSessionContext(String key) {
        context.remove(key);
    }
}
