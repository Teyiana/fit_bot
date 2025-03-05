package org.tytysh.fit_bot.service;

import org.springframework.stereotype.Service;
import org.tytysh.fit_bot.dao.DishRepository;
import org.tytysh.fit_bot.dao.ProductRepository;
import org.tytysh.fit_bot.dto.DishDTO;
import org.tytysh.fit_bot.dto.DtoToEntityMapper;
import org.tytysh.fit_bot.dto.PortionDTO;
import org.tytysh.fit_bot.entity.Dish;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.tytysh.fit_bot.dto.DtoToEntityMapper.toDto;
@Service
public class DishService implements DtoToEntityMapper{
    private final DishRepository dishRepository;
    private final ProductRepository productRepository;

    public DishService(DishRepository dishRepository, ProductRepository productRepository) {
        this.dishRepository = dishRepository;
        this.productRepository = productRepository;
    }

    public List<DishDTO> getAllDishes() {
        return dishRepository.findAll().stream().map(dish ->  toDto(dish)).toList();

    }

    public DishDTO getDishById(long id) {
        return toDto(dishRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Dish not found")));
    }

    public DishDTO getDishByName(String name) {
       Optional<Dish> dish = dishRepository.findByName(name);
        if (dish.isEmpty()) {
            throw new IllegalArgumentException("Dish not found");
        }
        return toDto(dish.get());
    }

    public DishDTO createDish(Dish entity) {
        Dish dish = new Dish();
        dish.setName(entity.getName());
        dish.setPortions(entity.getPortions());
        return toDto(dishRepository.save(dish));
    }

    public void deleteDish(long id) {
        dishRepository.deleteById(id);
    }

    public  DishDTO updateDish(DishDTO dishDTO) {
        Dish dish = dishRepository.findById(dishDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Dish not found"));
        dish.setName(dishDTO.getName());
        dish.setPortions(dishDTO.getPortions().stream().map(DtoToEntityMapper::toEntity).collect(Collectors.toList()));
        return toDto(dishRepository.save(dish));
    }

}
