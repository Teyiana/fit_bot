package org.tytysh.fit_bot.data_import;

import java.util.Map;

@FunctionalInterface
public interface CsvAfterAction {

    void afterImport(Map<Class<?>, CsvImportDTO<?>> importData);
}
