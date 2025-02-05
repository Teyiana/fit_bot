package org.tytysh.fit_bot.data_import;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvParsersRegistry {

    private final Map<String, CsvParser<?>> parsers = new HashMap<>();

    public CsvParsersRegistry(List<CsvParser<?>> parsers) {
        for (CsvParser<?> parser : parsers) {
            this.parsers.put(parser.getType().getName(), parser);
        }
    }

    public CsvParser<?> getParser(String type) {
        return parsers.get(type);
    }
}
