package com.affecto.jsonstat;

import com.affecto.jsonstat.model.*;
import com.google.common.collect.ImmutableList;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        final Values val = new RandomValues(9, 100);
        final Dataset ds = new Dataset("http://localhost:8080/test.json", "Test set", val);
        ds.addDimension(new BasicDimension("A", "A with 3 vals", ImmutableList.of("1", "2", "3")));
        ds.addDimension(new BasicDimension("B", "B with 2 vals", ImmutableList.of("1", "2")));
        ds.addDimension(new BasicDimension("C", "C with 4 vals", ImmutableList.of("1", "2", "3", "4")));
        final JsonStatSerializer ser = new JsonStatSerializer();
        System.out.println(ser.serialize(ds));
    }

}
