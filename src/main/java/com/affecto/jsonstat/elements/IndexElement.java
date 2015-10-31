package com.affecto.jsonstat.elements;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/*
 * Contains an index, either a Map<String, Integer>(element, index)
 * or List<String> with the list position as the index.
 */
public class IndexElement {

    @Getter
    public Map<String, Integer> mapIndex;

    @Getter
    public List<String> listIndex;

    public boolean isMap() {
        return mapIndex != null;
    }

    public boolean isList() {
        return listIndex != null;
    }

    public IndexElement setMap(final Map<String, Integer> mapIndex) {
        this.mapIndex = mapIndex;
        this.listIndex = null;
        return this;
    }

    public IndexElement setList(final List<String> listIndex) {
        this.mapIndex = null;
        this.listIndex = listIndex;
        return this;
    }

}
