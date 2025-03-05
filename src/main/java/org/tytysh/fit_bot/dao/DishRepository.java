package org.tytysh.fit_bot.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.tytysh.fit_bot.entity.Dish;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {

    Optional<Dish> findByName(String name);
}
