package liyu.test.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class TempMain {
	public static void closeQuietly(Closeable...c) {
		for(Closeable e:c) {
			try {
				e.close();
			} catch (IOException e1) {
				
			}
		}
	}
	public static void main(String[] args) throws IOException {
		File f = new File("tempMain.txt");
		if(!f.exists()) {
			f.createNewFile();
		}
		OutputStream os = new FileOutputStream(f, true);
		PrintStream printStream = new PrintStream(os, true, "UTF-8");
		System.setOut(printStream);
		try {			
			System.out.println("hello,world!你好！");
		}finally {
			closeQuietly(printStream,os);
		}

	}

}
