package org.tytysh.fit_bot.data_import.parsers;

import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.dao.FoodTypeRepository;
import org.tytysh.fit_bot.data_import.CsvImportDTO;
import org.tytysh.fit_bot.data_import.CsvParser;
import org.tytysh.fit_bot.data_import.parsers.util.DoubleParser;
import org.tytysh.fit_bot.data_import.parsers.util.IntegerParser;
import org.tytysh.fit_bot.data_import.parsers.util.LongParser;
import org.tytysh.fit_bot.data_import.parsers.util.StringParser;
import org.tytysh.fit_bot.entity.FoodType;
import org.tytysh.fit_bot.entity.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductParser extends CsvParser<Product> {

    private static final String[] HEADERS = {"id", "name", "type_id", "gram", "protein", "fat", "carbohydrates", "calories"};
    private IntegerParser integerParser = new IntegerParser();
    private StringParser stringParser = new StringParser();
    private LongParser longParser = new LongParser();
    private DoubleParser doubleParser = new DoubleParser();

    private final FoodTypeRepository foodTypeRepository;

    protected ProductParser(FoodTypeRepository foodTypeRepository) {
        super(Product.class);
        this.foodTypeRepository = foodTypeRepository;
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
            parseRow(line, dto);
        }

        return dto;
    }

    private void parseRow (String row, CsvImportDTO<Product> importDTO) {
        try {
            Product product = new Product();
            int index = 0;
            index = integerParser.parse(row, index, product::setId);
            index = stringParser.parse(row, index, product::setName);
            index = longParser.parse(row, index, foodTypeId -> importDTO.getAfterImportActions().add(importData -> {
                product.setType(findFoodType(foodTypeId, importData));
            }));
            index = integerParser.parse(row, index, product::setGram);
            index = doubleParser.parse(row, index, product::setProtein);
            index = doubleParser.parse(row, index, product::setFat);
            index = doubleParser.parse(row, index, product::setCarbohydrates);
            index = doubleParser.parse(row, index, product::setCalories);
            importDTO.getData().add(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse row: " + row, e);
        }
    }

    private FoodType findFoodType(long foodTypeId, Map<Class<?>, CsvImportDTO<?>> importData) {
        CsvImportDTO<FoodType> foodTypeCsvImportDTO = (CsvImportDTO<FoodType>) importData.get(FoodType.class);
        for (FoodType foodType : foodTypeCsvImportDTO.getData()) {
            if (foodType.getId() == foodTypeId) {
                return foodType;
            }
        }
        return foodTypeRepository.findById(foodTypeId).orElseThrow(() -> new RuntimeException("Food type not found: " + foodTypeId));
    }

}
