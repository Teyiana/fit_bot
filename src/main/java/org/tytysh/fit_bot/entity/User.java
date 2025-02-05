package org.tytysh.fit_bot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "email")
    private String email;

    @Column(name = "sex")
//    @Type(PostgreSQLEnumType.class )
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "weight")
    private  int weight;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "locale")
    private String locale = "ua";


}
