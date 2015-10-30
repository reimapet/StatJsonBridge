package com.affecto.jsonstat.model;

public interface Dimension {

	/**
	 * Get unique identifier for the dimension
	 */
	String getId();
	
	String getLabel();
	
	/**
	 * Get the number of items in the dimension
	 */
	int getSize();
	
	/**
	 * Get the value for the ith index
	 */
	String getIndexValue(int index);

}
