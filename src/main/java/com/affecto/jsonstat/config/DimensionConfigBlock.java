package com.affecto.jsonstat.config;

import com.affecto.jsonstat.blocks.IndividualDimensionBlock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by reimapet on 20.11.2015.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionConfigBlock {


    public String label;

    public BasicInfo basicInfo;

    public List<IndividualDimension> dimensions;

}
