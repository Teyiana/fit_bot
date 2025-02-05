package org.tytysh.fit_bot.data_import.parsers;

import org.springframework.stereotype.Component;
import org.tytysh.fit_bot.data_import.CsvImportDTO;
import org.tytysh.fit_bot.data_import.CsvParser;
import org.tytysh.fit_bot.entity.Dish;
import org.tytysh.fit_bot.entity.User;

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

    private static final String[] HEADERS = {"id", "userId", "name"};

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
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                Dish entity = new Dish();
                entity.setId(Integer.parseInt(matcher.group(1)));
                final long userId = Long.parseLong(matcher.group(2));
                dto.getAfterImportActions().add(importData -> {
                    CsvImportDTO<User> users = (CsvImportDTO<User>) importData.get(User.class);
                    Optional<User> user = users.getData().stream().filter(u -> u.getUserId() == userId).findFirst();
                    if (user.isEmpty()) {
                        throw new IllegalArgumentException("User not found: " + userId);
                    }
                    entity.setUser(user.get());
                });
                entity.setName((matcher.group(3)));
                entity.setProducts(parseLongArray(matcher.group(4)));
                entity.setPortions(parseLongArray(matcher.group(5)));
                assert entity.getProducts().length == entity.getPortions().length;
                dto.getData().add(entity);
            }
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
}
