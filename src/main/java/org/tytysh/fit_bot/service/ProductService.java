package org.tytysh.fit_bot.service;

import org.springframework.stereotype.Service;
import org.tytysh.fit_bot.dao.ProductRepository;
import org.tytysh.fit_bot.dto.FoodTypeDTO;
import org.tytysh.fit_bot.dto.ProductDTO;
import org.tytysh.fit_bot.dao.FoodTypeRepository;
import org.tytysh.fit_bot.entity.Product;

import java.util.List;
import java.util.Optional;

import static org.tytysh.fit_bot.dto.DtoToEntityMapper.toDto;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FoodTypeRepository foodTypeRepository;

    public ProductService(ProductRepository productRepository,
                          FoodTypeRepository foodTypeRepository) {
        this.productRepository = productRepository;
        this.foodTypeRepository = foodTypeRepository;
    }


    public List<FoodTypeDTO> getAllFoodTypes() {
        return  foodTypeRepository.findAll().stream().map(foodType -> toDto(foodType)).toList();
    }



    public List<ProductDTO> getProductByType(FoodTypeDTO foodType) {
        List<Product> products = productRepository.findByType(foodType);
        return products.stream().map(product -> toDto(product)).toList();
    }


    public ProductDTO getProductById(long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        return toDto(product.get());
    }

    public ProductDTO getProductByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        return toDto(product.get());
    }

    public ProductDTO createProduct(Product entity) {
        Product product = new Product();
        product.setName(entity.getName());
        product.setCalories(entity.getCalories());
        product.setProtein(entity.getProtein());
        product.setFat(entity.getFat());
        product.setCarbohydrates(entity.getCarbohydrates());
        return toDto(productRepository.save(product));
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setName(productDTO.getName());
        product.setCalories(productDTO.getCalories());
        product.setProtein(productDTO.getProtein());
        product.setFat(productDTO.getFat());
        product.setCarbohydrates(productDTO.getCarbohydrates());
        return toDto(productRepository.save(product));
    }

    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productsList = productRepository.findAll();
       return productsList.stream().map(product -> toDto(product)).toList();
    }

}
