package org.tytysh.fit_bot.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "food_type")
public class FoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

}
