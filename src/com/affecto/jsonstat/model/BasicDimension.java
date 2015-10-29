package com.affecto.jsonstat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation for the Dimension object
 * @author reimapet
 *
 */
public class BasicDimension implements Dimension
{
	public String id;
	public String label;
	
	public List<String> dimensionValues;

	public BasicDimension( String id, String label, List<String> dimensionValues)
	{
		this.id = id;
		this.label = label;
		this.dimensionValues = dimensionValues;
	}
	
	
	public String getLabel()
	{
		return label;
	}
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return dimensionValues.size();
	}

	@Override
	public String getIndexValue(int index) {
		return dimensionValues.get(index);
	}

}
