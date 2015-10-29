package com.affecto.jsonstat.model;

import java.util.Iterator;

/**
 * Values represents the value field in stat json which holds the actual values for the data
 * @author reimapet
 *
 */
public interface Values {
	/**
	 * Get iterator to data values
	 * @return
	 */
	public Iterator<String> iterator();
	
}
