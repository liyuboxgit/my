package liyu.test.reflect;

import org.apache.commons.csv.CSVPrinter;

public class TestClassLoader {
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.home"));
		
		System.out.println(TestClassLoader.class.getClassLoader());
		System.out.println(CSVPrinter.class.getClassLoader().getResource("").getPath());
	}
}
