package com.affecto.jsonstat.elements;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class ValueElement {

    @Getter
    public Map<String, Number> map;

    @Getter
    public List<Number> list;

    public boolean isMap() {
        return map != null;
    }

    public boolean isList() {
        return list != null;
    }

    public ValueElement setMap(final Map<String, Number> map) {
        this.map = map;
        this.list = null;
        return this;
    }

    public ValueElement setList(final List<Number> list) {
        this.map = null;
        this.list = list;
        return this;
    }

}
