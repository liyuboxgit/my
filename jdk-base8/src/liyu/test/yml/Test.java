package liyu.test.yml;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		YML yml = new YML(0);
		yml.put("server", new YML(1))
				.putString("port","80")
				.put("mp", 
					Arrays.asList(
						new YML.M[] {
							new YML.M(new String[] {"name:mg","age:3"}),
							new YML.M(new String[] {"name:zg","age:5"})
						}
					)
				);
		
		yml.put("spring", new YML(1))
				.put("mvc", new YML(2))
					.putString("throw-exception-if-no-handler-found", "true")
					.putString("pets", Arrays.asList(new String[] {"dog","cat"}));
		
		YML.outputYML(yml);
	}

}
