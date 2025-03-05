package org.tytysh.fit_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tytysh.fit_bot.entity.FoodType;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
}