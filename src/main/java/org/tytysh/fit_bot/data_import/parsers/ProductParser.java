package org.tytysh.fit_bot.data_import.parsers;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.data_import.CsvImportDTO;
import org.tytysh.fit_bot.data_import.CsvParser;
import org.tytysh.fit_bot.entity.FoodType;
import org.tytysh.fit_bot.entity.Product;
import org.tytysh.fit_bot.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductParser extends CsvParser<Product> {

    private static final String[] HEADERS = {"id", "name", "type_id", "gram", "protein", "fat", "carbohydrates", "calories"};

    protected ProductParser() {
        super(Product.class);
    }

    @Override
    public CsvImportDTO<Product> parse(BufferedReader reader, Timestamp timestamp) throws IOException {
        List<Product> results = new ArrayList<>();
        CsvImportDTO<Product> dto = new CsvImportDTO<>();
        dto.setTimestamp(timestamp);
        dto.setType(Product.class);
        dto.setData(results);
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
            String[] values = line.split(",");
            Product entity = new Product();
            entity.setId(Integer.parseInt(values[0]));
            entity.setName(String.valueOf(values[1]));
            final int typeId = Integer.parseInt(values[2]);
            dto.getAfterImportActions().add(importData -> {
                CsvImportDTO<FoodType> types = (CsvImportDTO<FoodType>) importData.get(FoodType.class);
                Optional<FoodType> foodType = types.getData().stream().filter(t -> t.getId() == typeId).findFirst();
                if (foodType.isEmpty()) {
                    throw new IllegalArgumentException("Type not found: " + typeId);
                }
                entity.setType(foodType.get());
            });

            entity.setGram(Integer.parseInt(values[3]));
            entity.setProtein(Double.parseDouble(values[4]));
            entity.setFat(Double.parseDouble(values[5]));
            entity.setCarbohydrates(Double.parseDouble(values[6]));
            entity.setCalories(Double.parseDouble(values[7]));
            results.add(entity);
        }

        return dto;
    }
}
