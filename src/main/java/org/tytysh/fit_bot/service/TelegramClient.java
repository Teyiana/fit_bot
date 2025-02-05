package org.tytysh.fit_bot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.tytysh.fit_bot.commands.CommandService;
import org.tytysh.fit_bot.config.BotConfig;
import org.tytysh.fit_bot.config.ChatMessage;
import org.tytysh.fit_bot.config.ChatSession;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.tytysh.fit_bot.dto.InlineButton;

import java.util.List;


@Component
public class TelegramClient extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramClient.class);

    private final BotConfig config;
    private final TelegramService telegramService;

    public TelegramClient(BotConfig config, TelegramService telegramService, CommandService commandService) {
        super(config.getBotToken());
        this.telegramService = telegramService;
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        MaybeInaccessibleMessage message = null;
        String msgData = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            message = update.getMessage();
            msgData = update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
            msgData = update.getCallbackQuery().getData();
        }
        if (message != null) {
            String chatId = message.getChatId().toString();
            ChatSession chatSession = getTelegramService().processMsg(chatId, msgData, message.getMessageId());
            sendMessage(chatSession);
        }
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            LOGGER.error("Error while registering bot", e);
            throw e;
        }
    }

    private void sendMessage(ChatSession chatSession) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.chatId(chatSession.getChatId());
        ChatMessage chatMessage = chatSession.getLastChatMessage();
        if (chatSession.isMessageProcessed(chatMessage)) {
            if (chatMessage.getResponseMessage() != null) {
                builder.text(chatMessage.getResponseMessage());
            }
            if (chatMessage.getKeyboard() != null && !chatMessage.getKeyboard().isEmpty()) {
                builder.replyMarkup(prepareKeyboard(chatMessage.getKeyboard()));
            }
        }
        BotApiMethod<?> method = builder.build();
        try {
            execute(method);
            chatSession.finalizeMessage();
        } catch (TelegramApiException e) {
            chatSession.failMessage(e.getMessage());
        }
    }

    private ReplyKeyboard prepareKeyboard(List<List<InlineButton>> keyboard) {
        List<List<InlineKeyboardButton>> buttons = keyboard.stream()
                .map(list -> list.stream()
                        .map(this::toInlineKeyboardButton)
                        .toList())
                .toList();
        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }

    private InlineKeyboardButton toInlineKeyboardButton(InlineButton inlineButton) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(inlineButton.getText());
        button.setCallbackData(inlineButton.getCallbackData());
        return button;
    }

    @Override
    public String getBotUsername() {
        return getConfig().getBotName();
    }

    public TelegramService getTelegramService() {
        return telegramService;
    }

    public BotConfig getConfig() {
        return config;
    }
}
