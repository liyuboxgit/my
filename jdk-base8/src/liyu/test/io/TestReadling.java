package liyu.test.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TestReadling {
	public static void main(String[] args) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream("大\n中\n小".getBytes());
		BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
		String line = null;
		while((line=stream.readLine())!=null) {
			System.out.println(line);
		}
	}
}
