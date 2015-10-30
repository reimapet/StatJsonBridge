package com.affecto.jsonstat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Dataset {

    @NonNull
    private String href;

    @NonNull
    private String label;

    private List<Dimension> dimensions = new LinkedList<>();

    @NonNull
    private Values values;

    /**
     * Verifies that the dataset and the dimensions mathc
     */
    public void verify() {

    }

    public void addDimension(final Dimension dimension) {
        dimensions.add(dimension);
    }
}
