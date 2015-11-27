package com.affecto.jsonstat.util;

import org.apache.commons.csv.CSVParser;

import java.util.*;

/**
 * Created by reimapet on 24.11.2015.
 */
public class UniqueIndexGenerator implements IndexGenerator {

    final public static String INDEX_COLUMN_PARAMETER = "indexColumns";

    public Map<String, Integer> generateIndex( CSVParser parser, Map<String, String> parms )
    {
        ArrayList<Integer> colIndexes = extractParameters(parms);

        Map<String, Integer> indexMap = new LinkedHashMap<>();
        int indexCounter = 0;
        parser.forEach( (row) -> {
          StringBuffer key = new StringBuffer();
          colIndexes.forEach((index) -> key.append(row.get(index) ) );
          indexMap.put( key.toString(), new Integer((int)row.getRecordNumber()));
        });
        return indexMap;
    }

    private ArrayList extractParameters( Map<String, String> parms )
    {
        String i = parms.get(INDEX_COLUMN_PARAMETER);
        StringTokenizer st = new StringTokenizer(i, ",");
        ArrayList<Integer> list = new ArrayList<>();
        while( st.hasMoreElements() )
        {
            list.add( new Integer(st.nextToken()));
        }
        return list;
    }
}
