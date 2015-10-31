package com.affecto.jsonstat.blocks;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DimensionGroupBlock {

    public List<String> id;

    public List<Integer> size;

    public Map<String, List<String>> role;

    public Map<String, IndividualDimensionBlock> dimensions;

}
