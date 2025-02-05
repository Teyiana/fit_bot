package org.tytysh.fit_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tytysh.fit_bot.entity.Product;

public interface ProductRepository  extends JpaRepository<Product, Long> {
}
