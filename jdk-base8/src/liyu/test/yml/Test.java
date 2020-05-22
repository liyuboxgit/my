package liyu.test.yml;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		/*Yml yml = new Yml();
		yml.putAndReturn("server", new Yml())
				.putSingalString("port","80")
				.putMaps("mp", 
					Arrays.asList(
						new Yml.M[] {
							new Yml.M(new String[] {"name:mg","age:3"}),
							new Yml.M(new String[] {"name:zg","age:5"})
						}
					)
				).end()
				.putSingalString("password", "123456");
		
		yml.putAndReturn("spring", new Yml())
				.putAndReturn("mvc", new Yml())
					.putSingalString("throw-exception-if-no-handler-found", "true")
					.putStrings("pets", Arrays.asList(new String[] {"dog","cat"}));*/
		
		
		Yml yml = new Yml();
		yml.putSingalString("apiVersion", "v1")
	       .putSingalString("kind", "ReplicationController")
	       .putAndReturn("metadata", new Yml())
	       		.putSingalString("name", "nginx-controller").end()
	       .putAndReturn("spec", new Yml())
	       		.putSingalString("replicas", "2")
	       		.putAndReturn("selector", new Yml())
	       			.putSingalString("name", "nginx").end()
	       		.putAndReturn("template", new Yml())
	       			.putAndReturn("metadata", new Yml())
	       				.putAndReturn("labels", new Yml())
	       					.putSingalString("name", "nginx").end()
	       				.end()
	       			.end()
	       		.putAndReturn("spec", new Yml())
	       			.putMaps("containers", Arrays.asList(
							new Yml.M[] {
									new Yml.M(new String[] {"name:nginx","image:docker.io/nginx","imagePullPolicy:Never",})
								}
							));
	       				
		
		
		
		
		Yml.outputYML(yml);
	}

}
