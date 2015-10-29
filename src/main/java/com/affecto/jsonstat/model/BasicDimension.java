package com.affecto.jsonstat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Basic implementation for the Dimension object
 *
 * @author reimapet
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicDimension implements Dimension {

    public String id;

    public String label;

    public List<String> values;

    @Override
    public String getID() {
        return id;
    }

    @Override
    public int getSize() {
        return values.size();
    }

    @Override
    public String getIndexValue(int index) {
        return values.get(index);
    }

}
