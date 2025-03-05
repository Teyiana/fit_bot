package org.tytysh.fit_bot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dish_portion")
public class Portion {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "dish_id")
    private long dishId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(name = "quantity")
    private long quantity;


}
