package org.tytysh.fit_bot.data_import.parsers;

import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.data_import.CsvImportDTO;
import org.tytysh.fit_bot.data_import.CsvParser;
import org.tytysh.fit_bot.entity.FoodType;
import org.tytysh.fit_bot.entity.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class FoodTypeParser  extends CsvParser<FoodType> {

    private static final String[] HEADERS = {"id", "name"};

    protected FoodTypeParser() {
        super(FoodType.class);
    }

    @Override
    public CsvImportDTO<FoodType> parse(BufferedReader reader, Timestamp timestamp) throws IOException {
        List<FoodType> results = new ArrayList<>();

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
            FoodType entity = new FoodType();
            entity.setId(Integer.parseInt(values[0]));
            entity.setName(String.valueOf(values[1]));


            results.add(entity);
        }
        CsvImportDTO<FoodType> dto = new CsvImportDTO<>();
        dto.setTimestamp(timestamp);
        dto.setType(FoodType.class);
        dto.setData(results);
        dto.setAfterImportActions(new ArrayList<>());
        return dto;
    }

}
