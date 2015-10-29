package com.affecto.jsonstat;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import com.affecto.jsonstat.model.*;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Values val = new CsvFileValues("c:/tmp/testjsondata.txt");
		Dataset ds = new Dataset("http://localhost:8080/test.json", "Test set", val);
		
		List<String> vals = new ArrayList();
		vals.add("1");
		vals.add("2");
		vals.add("3");
		Dimension d = new BasicDimension("A", "A with 3 vals", vals );
		ds.addDimension(d);
		
		List<String> vals2 = new ArrayList();
		vals2.add("1");
		vals2.add("2");
		Dimension d2 = new BasicDimension("B", "B with 2 vals", vals2 );
		ds.addDimension(d2);
		
		List<String> vals3 = new ArrayList();
		vals3.add("1");
		vals3.add("2");
		vals3.add("3");
		vals3.add("4");

		Dimension d3 = new BasicDimension("C", "C with 4 vals", vals3 );
		ds.addDimension(d3);

		JsonStatSerializer ser = new JsonStatSerializer();
		System.out.println(ser.serialize(ds));

	}

}
