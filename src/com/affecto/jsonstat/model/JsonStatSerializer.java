package com.affecto.jsonstat.model;

import java.util.Iterator;

public class JsonStatSerializer {
	public String serialize( Dataset dataset )
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		appendBasicData( sb, dataset );
		appendDimensions( sb, dataset );
		appendValues(sb, dataset );
		sb.append("}");

		return sb.toString();
	}

	private void appendValues(StringBuffer sb, Dataset dataset) {
		// TODO Auto-generated method stub
		
	}

	private void appendDimensions(StringBuffer sb, Dataset dataset) {
		// TODO Auto-generated method stub
		sb.append("dimension: {");
		Iterator<Dimension> it = dataset.getDimensionList().iterator();
		sb.append("id : [");
		while( it.hasNext() )
		{
			Dimension d = it.next();
			sb.append( 	"\"" + d.getID() + "\"");
			if( it.hasNext())
				sb.append(",");
			
		}
		sb.append("], ");

		it = dataset.getDimensionList().iterator();
		sb.append("size : [");
		while( it.hasNext() )
		{
			Dimension d = it.next();
			sb.append( 	"\"" + d.getSize() + "\"");
			if( it.hasNext())
				sb.append(",");
			
		}
		sb.append("],");

		it = dataset.getDimensionList().iterator();
		while( it.hasNext() )
		{
			Dimension d = it.next();
			sb.append( d.getID() + ": {");
			sb.append("label: \"" + d.getLabel() + "\", ");
			
			sb.append("category : {");
			sb.append("index: [");
			for( int i = 0 ; i < d.getSize() ; i++ )
			{
				if( i > 0 )
					sb.append(",");
				sb.append("\"" + d.getIndexValue(i) + "\"");
			}

			sb.append("]");
			sb.append("}");

			sb.append("}");
			if( it.hasNext())
				sb.append(",");
		}
		
		sb.append(", value: [");
		Iterator<String> dit = dataset.getValues().iterator();
		while( dit.hasNext() )
		{
			sb.append("\""+dit.next()+"\"");
			if( dit.hasNext() )
				sb.append(",");
		}
		
		sb.append("]");
		
	}

	private void appendBasicData(StringBuffer sb, Dataset dataset) {
		// TODO Auto-generated method stub
		sb.append("class: \"dataset:\",");
		sb.append("href: ");
		sb.append("\"" + dataset.getHref() + "\",");
		sb.append("label: ");
		sb.append("\"" + dataset.getLabel() + "\", ");
	}
}
