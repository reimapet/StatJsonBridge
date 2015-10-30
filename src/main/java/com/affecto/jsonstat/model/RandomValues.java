package com.affecto.jsonstat.model;

import java.util.Iterator;
import java.util.Random;

public class RandomValues implements Values {

    private final int dimensions;
    private final int rows;

    public RandomValues(final int dimensions, final int rows) {
        this.dimensions = dimensions;
        this.rows = rows;
    }

    @Override
    public Iterator<String> iterator() {
        return new Random().doubles(dimensions * rows).mapToObj(Double::toString).iterator();
    }
}
