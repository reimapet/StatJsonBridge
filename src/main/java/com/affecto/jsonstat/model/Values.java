package com.affecto.jsonstat.model;

import java.util.Iterator;

/**
 * Values represents the value field in stat json which holds the actual values for the data
 */
public interface Values {

	Iterator<String> iterator();
	
}
