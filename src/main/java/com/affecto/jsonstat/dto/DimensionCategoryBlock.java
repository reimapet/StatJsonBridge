package com.affecto.jsonstat.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DimensionCategoryBlock {

    public Map<String, Integer> index;

    public Map<String, String> label;

    public Map<String, IndividualDimensionUnitBlock> unit;

}
