package org.tytysh.fit_bot.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private FoodType type;


    @Column(name = "gram")
    private int gram;

    @Column(name = "protein")
    private double protein;

    @Column(name = "fat")
    private double fat;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    @Column(name = "calories")
    private double calories;

}
