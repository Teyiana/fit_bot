package org.tytysh.fit_bot.data_import.parsers.util;

import java.util.function.Consumer;

public class StringParser implements TypedParser<String>{

    @Override
    public int parse(String line, int fromIndex, Consumer<String> consumer) {
        int comaIndex = line.indexOf(',', fromIndex);
        int quotes = line.indexOf('"', fromIndex);

        if (quotes != -1) {
            int nextQuotes = line.indexOf('"', quotes + 1);
            consumer.accept(line.substring(quotes + 1, nextQuotes));
            return comaIndex == -1 ? -1 : line.indexOf(',', nextQuotes) + 1;
        } else {
            if (comaIndex == -1) {
                consumer.accept(line.substring(fromIndex));
                return -1;
            } else {
                consumer.accept(line.substring(fromIndex, comaIndex));
                return comaIndex + 1;
            }
        }
    }
}
