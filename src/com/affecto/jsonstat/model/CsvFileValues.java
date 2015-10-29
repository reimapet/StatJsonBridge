package com.affecto.jsonstat.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class CsvFileValues implements Values 
{

	List<String> values;
	
	/**
	 * 
	 * @param fileName file containins CSV data
	 * @throws IOException
	 */
	public CsvFileValues( String fileName ) throws IOException
	{
		File file = new File( fileName );
		StringBuffer sb = readData( file );
		values = breakupData( sb.toString());
	}
	
	public Iterator<String> iterator()
	{
		return values.iterator();
	}
	
	/**
	 * Convert data from csv string to list
	 * @param data
	 * @return
	 */
	public List<String>  breakupData( String data)
	{
		StringTokenizer st = null;
		st = new StringTokenizer( data, "," ) ;
		ArrayList<String> list = new ArrayList<String>();
		while( st.hasMoreTokens())
		{
			list.add(st.nextToken());
		}
		
		return list;
	}
	
	/**
	 * Read the datafile
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public StringBuffer readData( File file ) throws IOException
	{
		FileReader rd = new FileReader( file );
		BufferedReader brd = new BufferedReader( rd );
		StringBuffer buffer = new StringBuffer();
		String str = "";
		while( str != null )
		{
			str = brd.readLine();
			if( str != null)
				buffer.append(str);
		}
		return buffer;
	}

	
	
}
