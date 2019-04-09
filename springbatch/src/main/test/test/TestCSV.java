package test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class TestCSV {
	public static void main(String[] args) throws IOException {
		URL resource = TestCSV.class.getResource("/person.csv");
		File input = new File(resource.getFile());
		if(input.exists()) {
			Reader in = new FileReader(input);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			records.iterator().next();//ignore header
			for (CSVRecord record : records) {
				System.out.println(record.get(0));
			}
		}
	}
}
