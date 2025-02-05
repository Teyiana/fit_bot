package org.tytysh.fit_bot.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "meal_food")
public class MealFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long Id;

    @Column(name = "meal_id")
    private long mealId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(name = "quantity")
    private int quantity;


}
