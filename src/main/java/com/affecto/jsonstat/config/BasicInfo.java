package com.affecto.jsonstat.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by reimapet on 20.11.2015.
 */
@Data
public class BasicInfo {

    @Data
    public class BasicInfoRoles
    {
        public List<String> time;
        public List<String> geo;
        public List<String> metric;
    }

    public List<String> id;

    public List<Integer> size;

    @JsonProperty(required = false)
    @JsonInclude(NON_NULL)
    public BasicInfoRoles role;

}
