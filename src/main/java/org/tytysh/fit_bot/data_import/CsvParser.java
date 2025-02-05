package org.tytysh.fit_bot.data_import;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;

@Component
public abstract class CsvParser<T> {

    private final Class<T> type;

    protected CsvParser(Class<T> type) {
        this.type = type;
    }

    public abstract CsvImportDTO<T> parse(BufferedReader reader, Timestamp timestamp) throws IOException;

    public Class<T> getType() {
        return type;
    }
}
