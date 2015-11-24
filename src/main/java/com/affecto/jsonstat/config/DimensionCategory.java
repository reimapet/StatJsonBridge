package com.affecto.jsonstat.config;

import com.affecto.jsonstat.blocks.IndividualDimensionUnitBlock;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by reimapet on 20.11.2015.
 */
@Data
public class DimensionCategory {

    @Data
    public class DynamicIndex {
        /** Name of the index generator **/
        public String className;
        public Map<String, String> parameters;

    }

    @JsonProperty(required = false)
    @JsonInclude(NON_NULL)
    public Map<String, String> label;

    @JsonProperty(required = false)
    @JsonInclude(NON_NULL)
    public Map<String, Integer> index;

    @JsonProperty(required = false)
    @JsonInclude(NON_NULL)
    public Map<String, List<String>> child;

    @JsonInclude(NON_NULL)
    public Map<String, IndividualDimensionUnitBlock> unit;


    @JsonProperty(required = false)
    @JsonInclude(NON_NULL)
    // This property may be set to override the regular preset index to generate the index from data
    public DynamicIndex dynamicIndex;

}
