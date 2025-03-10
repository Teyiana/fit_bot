package org.tytysh.fit_bot.data_import.parsers.util;

import java.util.function.Consumer;

public interface TypedParser<T> {
    int parse(String line, int fromIndex, Consumer<T> consumer);
}
