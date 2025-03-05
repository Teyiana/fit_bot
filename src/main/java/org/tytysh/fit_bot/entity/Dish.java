package org.tytysh.fit_bot.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.tytysh.fit_bot.dto.PortionDTO;

import java.util.List;


@Data
@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "dish_id")
    private List<Portion> portions;

}
