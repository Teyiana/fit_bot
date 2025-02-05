package org.tytysh.fit_bot.dto;


import lombok.Data;

@Data
public class SessionsMessagesDOT {
    private long id;

    private String chatId;

    private long messageId;

    private String message;

    private String response;

    private String state;

    private String failure;
}
