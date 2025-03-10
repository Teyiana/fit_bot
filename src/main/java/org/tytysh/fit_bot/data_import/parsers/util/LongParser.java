package org.tytysh.fit_bot.data_import.parsers.util;

import java.util.function.Consumer;

public class LongParser implements TypedParser<Long>{

    @Override
    public int parse(String line, int fromIndex, Consumer<Long> consumer) {
        int comaIndex = line.indexOf(',', fromIndex);
        String value = comaIndex == -1 ? line.substring(fromIndex) : line.substring(fromIndex, comaIndex);
        consumer.accept(Long.parseLong(value));
        return comaIndex == -1 ? -1 : comaIndex + 1;
    }

}
