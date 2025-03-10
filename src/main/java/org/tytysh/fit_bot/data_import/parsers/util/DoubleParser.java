package org.tytysh.fit_bot.data_import.parsers.util;

import java.util.function.Consumer;

public class DoubleParser implements TypedParser<Double>{

    @Override
    public int parse(String line, int fromIndex, Consumer<Double> consumer) {
        int comaIndex = line.indexOf(',', fromIndex);
        String value = comaIndex == -1 ? line.substring(fromIndex) : line.substring(fromIndex, comaIndex);
        consumer.accept(Double.parseDouble(value));
        return comaIndex == -1 ? -1 : comaIndex + 1;
    }
}
