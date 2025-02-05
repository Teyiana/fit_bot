package org.tytysh.fit_bot.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;


@Data
@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "name")
    private String name;

    @Type(CustomLongArrayType.class)
    @Column(name = "products", columnDefinition = "bigint[]")
    private Long [] products;

    @Type(CustomLongArrayType.class)
    @Column(name = "portions", columnDefinition = "bigint[]")
    private Long [] portions;

}
