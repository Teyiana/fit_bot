package org.tytysh.fit_bot.data_import;


import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CsvParserService {
    @Autowired
    private CsvParsersRegistry parsersRegistry;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/fit-bot-db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";
    private static final String CSV_FILE_PATH = "/import_csv";

    public void importCsv() {
        Map<String, CsvImportDTO<?>> csvData = new HashMap<>();
        try (InputStream inputStream = getClass().getResourceAsStream(CSV_FILE_PATH);
             CSVReader reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))) {
            String[] values;
            while ((values = reader.readNext()) != null) {
                String type = values[0];
                String path = values[1];
                CsvImportDTO<?> csvImportDTO = parseCsv(path);
                csvData.put(type, csvImportDTO);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to import CSV file", e);
        }
    }


    public CsvImportDTO<?> parseCsv(String resourcePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(resourcePath)), StandardCharsets.UTF_8))) {

            // Read metadata lines and skip
            Timestamp timestamp = parseTimestamp(reader.readLine()); // Skip: timestamp, 2024-11-30T14:00:00
            String type = parseType(reader.readLine()); // Skip: type, example
            CsvParser<?> parser = parsersRegistry.getParser(type);
            if (parser != null) {
                return parser.parse(reader, timestamp);
            } else {
                throw new IllegalArgumentException("Unknown type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file" + resourcePath, e);
        }
    }

    private Timestamp parseTimestamp(String readLine) {
        String[] split = readLine.split(",");
        if (split.length != 2) {
            throw new IllegalArgumentException("Unexpected timestamp line: " + readLine);
        }
        return Timestamp.valueOf(split[1].trim());
    }

    private String parseType(String readLine) {
        String[] split = readLine.split(",");
        if (split.length != 2) {
            throw new IllegalArgumentException("Unexpected type line: " + readLine);
        }
        return split[1].trim();
    }
}
