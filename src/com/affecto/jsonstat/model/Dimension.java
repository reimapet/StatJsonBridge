package com.affecto.jsonstat.model;

public interface Dimension {

	/**
	 * Get unique identifier for the dimension
	 * @return
	 */
	public String getID();
	
	/**
	 * 
	 * @return
	 */
	public String getLabel();
	
	/**
	 * Get the number of items in the dimension
	 * @return
	 */
	public int getSize();
	
	/**
	 * Get the value for the ith index
	 * @param index
	 * @return
	 */
	public String getIndexValue( int index );
	
	
	
}
