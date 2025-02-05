package org.tytysh.fit_bot.data_import;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CsvImportDTO<T> {
    private Timestamp timestamp;
    private Class<T> type;
    private List<T> data;
    private List<CsvAfterAction> afterImportActions;
}
