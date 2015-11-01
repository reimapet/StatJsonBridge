package com.affecto.jsonstat.elements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/*
 * Contains an index, either a Map<String, Integer>(element, index)
 * or List<String> with the list position as the index.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class IndexElement {

    public Map<String, Integer> map;

    public List<String> list;

    public boolean isMap() {
        return map != null;
    }

    public boolean isList() {
        return list != null;
    }

    public IndexElement setMap(final Map<String, Integer> map) {
        this.map = map;
        this.list = null;
        return this;
    }

    public IndexElement setList(final List<String> list) {
        this.map = null;
        this.list = list;
        return this;
    }

}
