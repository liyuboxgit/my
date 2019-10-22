package liyu.test.io;

import java.util.Scanner;
import java.util.regex.Pattern;

public class RegexTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\d{3}");
		Scanner scan = null;
		while(true) {
			scan = new Scanner(System.in);
			String line = scan.nextLine();
			
			if(line.equals("q!")) {
				System.out.println("thank you bye!");
				break;
			}else {
				System.out.println("your input:"+line);
				boolean ret = pattern.matcher(line).matches();
				if(ret) {
					System.out.println("your input is right!");
				}else {
					System.out.println("your input is wrong!");
				}
			}
			
		}
	}
}
