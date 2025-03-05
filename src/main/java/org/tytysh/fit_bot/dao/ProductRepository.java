package org.tytysh.fit_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tytysh.fit_bot.dto.FoodTypeDTO;
import org.tytysh.fit_bot.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    List<Product> findByType(FoodTypeDTO foodType);
}
