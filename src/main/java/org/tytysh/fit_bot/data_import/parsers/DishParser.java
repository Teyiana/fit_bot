package org.tytysh.fit_bot.data_import.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.data_import.CsvImportDTO;
import org.tytysh.fit_bot.data_import.CsvParser;
import org.tytysh.fit_bot.data_import.parsers.util.IntegerParser;
import org.tytysh.fit_bot.data_import.parsers.util.StringParser;
import org.tytysh.fit_bot.entity.Dish;
import org.tytysh.fit_bot.entity.Portion;
import org.tytysh.fit_bot.entity.Product;
import org.tytysh.fit_bot.entity.User;
import org.tytysh.fit_bot.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DishParser extends CsvParser<Dish> {
    private static final Pattern PATTERN = Pattern.compile("(\\d+),\\s*(\\d+),\\s*([^,]+),\\s*\"([^\"]+)\",\\s*\"([^\"]+)\"");

    private static final Logger LOGGER = LoggerFactory.getLogger(DishParser.class);

    private static final String[] HEADERS = {"id", "userId", "name"};
    private IntegerParser integerParser = new IntegerParser();
    private StringParser stringParser = new StringParser();

    public DishParser() {
        super(Dish.class);
    }

    @Override
    public CsvImportDTO<Dish> parse(BufferedReader reader, Timestamp timestamp) throws IOException {
        CsvImportDTO<Dish> dto = new CsvImportDTO<>();
        dto.setData(new ArrayList<>());
        dto.setTimestamp(timestamp);
        dto.setType(Dish.class);
        dto.setAfterImportActions(new ArrayList<>());
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            String[] values = line.split(",");
            assert HEADERS.length == values.length;
            for (int i = 0; i < values.length; i++) {
                assert HEADERS[i].equalsIgnoreCase(values[i].trim());
            }
            break;
        }

        while ((line = reader.readLine()) != null) {
            parseRow(line, dto);
        }
        return dto;
    }

    private int[] parseIntArray(String strNumberArr) {
        String[] strNumbers = strNumberArr.split(",");
        int[] numbers = new int[strNumbers.length];
        for (int i = 0; i < strNumbers.length; i++) {
            numbers[i] = Integer.parseInt(strNumbers[i].trim());
        }
        return numbers;
    }

    private Long[] parseLongArray(String strNumberArr) {
        String[] strNumbers = strNumberArr.split(",");
        Long[] numbers = new Long[strNumbers.length];
        for (int i = 0; i < strNumbers.length; i++) {
            numbers[i] = Long.parseLong(strNumbers[i].trim());
        }
        return numbers;
    }

    private void parseRow(String row, CsvImportDTO<Dish> dto) {
        try {
            Dish dish = new Dish();
            int index = 0;
            CsvPortions csvPortions = new CsvPortions();
            index = integerParser.parse(row, index, dish::setId);
            index = stringParser.parse(row, index, dish::setName);
            index = stringParser.parse(row, index, p -> csvPortions.portions = p);
            stringParser.parse(row, index, q -> csvPortions.quantities = q);


            int[] products = parseIntArray(csvPortions.portions);
            int[] quantities = parseIntArray(csvPortions.quantities);
            if (products.length != quantities.length) {
                throw new IllegalArgumentException("Different number of products and quantities");
            }
            dish.setPortions(new ArrayList<>());
            for (int i = 0; i < products.length; i++) {
                final int productId = products[i];
                final int quantity = quantities[i];
                dto.getAfterImportActions().add(importData -> {
                    CsvImportDTO<Product> productsDto = (CsvImportDTO<Product>) importData.get(Product.class);
                    Optional<Product> product = productsDto.getData().stream().filter(p -> p.getId() == productId).findFirst();
                    if (product.isEmpty()) {
                        throw new IllegalArgumentException("Product not found: " + productId);
                    }
                    Portion portion = new Portion();
                    portion.setProduct(product.get());
                    portion.setQuantity(quantity);
                    portion.setDishId(dish.getId());
                    dish.getPortions().add(portion);
                });
            }
            dto.getData().add(dish);
        } catch (Exception e) {
            LOGGER.debug("Error parsing row: " + row);
        }
    }

    private static class CsvPortions {
        String portions;
        String quantities;
    }
}
