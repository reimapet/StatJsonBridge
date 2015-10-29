package com.affecto.jsonstat.model;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
	
	private String href;
	private String label;
	
	private List<Dimension> dimensionList;
	private Values values;
	
	public Dataset()
	{
	}
	
	public Dataset( String href, String label, Values values )
	{
		this.href = href;
		this.label = label;
		this.values = values;
		dimensionList = new ArrayList<Dimension>();
	}
	/**
	 * Verifies that the dataset and the dimensions mathc
	 */
	public void verify()
	{
		
	}
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Dimension> getDimensionList() {
		return dimensionList;
	}
	
	public void addDimension( Dimension d )
	{
		this.dimensionList.add( d );
	}

	public void setDimensionList(List<Dimension> dimensionList) {
		this.dimensionList = dimensionList;
	}
	public Values getValues() {
		return values;
	}
	public void setValues(Values values) {
		this.values = values;
	}
	
	
	

}
