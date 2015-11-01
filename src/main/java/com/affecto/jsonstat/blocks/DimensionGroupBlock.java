package com.affecto.jsonstat.blocks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class DimensionGroupBlock {

    public List<String> id;

    public List<Integer> size;

    public Map<String, List<String>> role;

    public Map<String, IndividualDimensionBlock> dimensions;

}
