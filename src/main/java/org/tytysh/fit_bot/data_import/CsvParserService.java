package org.tytysh.fit_bot.data_import;


import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CsvParserService {
    @Autowired
    private CsvParsersRegistry parsersRegistry;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/fit-bot-db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";
    private static final String CSV_FILE_PATH_DISH = "/import_csv/dish.csv";
    private static final String CSV_FILE_PATH_FOOD_TYPE = "/import_csv/food_type.csv";
    private static final String CSV_FILE_PATH_PRODUCTS = "/import_csv/products.csv";

    private static final String[] CSV_FILES = {
            "/import_csv/dish.csv",
            "/import_csv/food_type.csv",
            "/import_csv/products.csv"
    };


    @PostConstruct
    public void importCsv() {
        Map<String, CsvImportDTO<?>> csvData = new HashMap<>();
        for (String csvPath : CSV_FILES) {
            CsvImportDTO<?> csvImportDTO = parseCsv(csvPath);
            csvData.put(csvPath, csvImportDTO);
        }
        Map<Class<?>, CsvImportDTO<?>> imports = new HashMap<>();
        for (CsvImportDTO<?> csvImportDTO : csvData.values()) {
            putOrMerge(csvImportDTO.getType(), imports, csvImportDTO);
        }
        imports.forEach((type, importDTO) -> {
            importDTO.getAfterImportActions().forEach(action -> action.afterImport(imports));
            persist(importDTO);
        });
    }

    private void persist(CsvImportDTO<?> importDTO) {


    }

    private <T> void putOrMerge(Class<T> type, Map<Class<?>, CsvImportDTO<?>> imports, CsvImportDTO<?> importDTO) {
        CsvImportDTO<T> existing = (CsvImportDTO<T>) imports.get(type);
        if (existing == null) {
            imports.put(type, importDTO);
        } else {
            importDTO.getData().forEach(d -> existing.getData().add((T) d));
            existing.getAfterImportActions().addAll(importDTO.getAfterImportActions());
        }
    }

    public CsvImportDTO<?> parseCsv(String resourcePath) {
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Файл не знайдено: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                Timestamp timestamp = parseTimestamp(reader.readLine());
                String type = parseType(reader.readLine());

                CsvParser<?> parser = parsersRegistry.getParser(type);
                if (parser == null) {
                    throw new IllegalArgumentException("Невідомий тип CSV: " + type);
                }
                return parser.parse(reader, timestamp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Помилка обробки CSV: " + resourcePath, e);
        }
    }

    private Timestamp parseTimestamp(String readLine) throws ParseException {
        if (readLine == null) {
            throw new IllegalArgumentException("Очікувався timestamp, але рядок відсутній");
        }
        String[] parts = readLine.split(",", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Неправильний формат timestamp: " + readLine);
        }

        String timestampString = parts[1].trim().replace("T", " ").replace("Z", "");


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new Timestamp(dateFormat.parse(timestampString).getTime());
    }

    private String parseType(String readLine) {
        if (readLine == null) {
            throw new IllegalArgumentException("Очікувався type, але рядок відсутній");
        }
        String[] split = readLine.split(",");
        if (split.length != 2) {
            throw new IllegalArgumentException("Unexpected type line: " + readLine);
        }
        return split[1].trim();
    }
}
