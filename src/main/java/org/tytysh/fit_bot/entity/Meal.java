package org.tytysh.fit_bot.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "time_of_meal")
    private Timestamp timeOfMeal;

    @OneToMany
    @JoinColumn(name = "meal_id")
    private List<MealFood> mealFoods;

}
