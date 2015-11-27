package com.affecto.jsonstat.util;

import org.apache.commons.csv.CSVParser;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by reimapet on 27.11.2015.
 */
public interface IndexGenerator {
    /**
     * Generates a sorted map of column indexed by appending column in colIndexes parameter.
     * @param parser
     * @param parms
     * @return
     */
    public Map<String, Integer> generateIndex( CSVParser parser, Map<String, String> parms);
}
