package org.tytysh.fit_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tytysh.fit_bot.entity.Meal;

import java.util.stream.Stream;

public interface MealRepository  extends JpaRepository<Meal, Long> {
    Stream<Meal> findByUserId(long userId);
}
