package com.affecto.jsonstat.model;

import lombok.Getter;
import org.apache.commons.csv.CSVFormat;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CsvFileValues implements Values {

    @Getter
    private List<String> values;

    public CsvFileValues(final String fileName) throws IOException {
        try (final Reader r = new FileReader(fileName)) {
            values = CSVFormat.DEFAULT.parse(r).getRecords().stream()
                    .flatMap(rec -> StreamSupport.stream(rec.spliterator(), false))
                    .collect(Collectors.toList());
        }
    }

    public Iterator<String> iterator() {
        return values.iterator();
    }

}
