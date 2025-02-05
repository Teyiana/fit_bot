package org.tytysh.fit_bot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sessions_messages")
public class SessionsMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "message_id")
    private long messageId;

    @Column(name = "message")
    private String message;

    @Column(name = "response")
    private String response;

    @Column(name = "state")
    private String state;

    @Column(name = "failure")
    private String failure;
}
