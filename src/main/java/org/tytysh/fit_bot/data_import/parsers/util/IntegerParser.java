package org.tytysh.fit_bot.data_import.parsers.util;

import java.util.function.Consumer;

public class IntegerParser implements TypedParser<Integer>{

    @Override
    public int parse(String line, int fromIndex, Consumer<Integer> consumer) {
        int comaIndex = line.indexOf(',', fromIndex);
        String value = comaIndex == -1 ? line.substring(fromIndex) : line.substring(fromIndex, comaIndex);
        consumer.accept(Integer.parseInt(value));
        return comaIndex == -1 ? -1 : comaIndex + 1;
    }
}
