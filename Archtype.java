

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Date;
import java.util.Scanner;

public class Archtype {
	private static String sp = File.separator;
	/*public enum ARCH{
		GROUPID("liyu.test"),ARTIFACTID("spring"),VERSION("0.0.1-SNAPSHOT"),
		//spring boot,spring cloud
		TYPE("spring boot");
		
		private String value;

		ARCH(String value) {
			this.value = value;
		}
	}*/
	/*public static class ARCHC{
		public static String GROUPID = "liyu.test";
		public static String ARTIFACTID = "spring";
		public static String VERSION = "0.0.1-SNAPSHOT";
		public static String TYPE = "spring boot";
	}*/
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in); String tmp = null;
		
		System.out.print("ÇëÊäÈëTYPE,spring boot or spring cloud£º[spring boot]"); tmp = sc.nextLine();
		String TYPE = tmp.length()==0?"spring boot":tmp; 

		System.out.print("ÇëÊäÈëGROUPID£º[liyu.test]");  tmp = sc.nextLine();
		String GROUPID = tmp.length()==0?"liyu.test":tmp; 
		
		System.out.print("ÇëÊäÈëARTIFACTID£º[spring]"); tmp = sc.nextLine();
		String ARTIFACTID = tmp.length()==0?"spring":tmp; 
		
		System.out.print("ÇëÊäÈëVERSION£º[0.0.1-SNAPSHOT]"); tmp = sc.nextLine();
		String VERSION = tmp.length()==0?"0.0.1-SNAPSHOT":tmp; 
		
		System.out.println("create "+TYPE+" project:"+"\n"+"===========>"+"\n"+"GROUPID£º"+GROUPID+"\n"+"ARTIFACTID£º"+ARTIFACTID+"\n"+"VERSION£º"+VERSION+"\n"+"<<<<<<<<<<<"); 
		
  
		
		
		//ç”¨æˆ·ç©ºé—´
		File path = new File(System.getProperty("user.home") + sp + "maven_" + new Date().getTime());
		//å·¥ä½œç©ºé—´
		//String classPath = java.lang.Object.class.getClass().getResource("/").getFile();
		//String src = classPath+"../../../";
		//File path = new File(src);
		
		String javapath = ARTIFACTID + ".src.main.java" +"."+GROUPID + "."+ARTIFACTID;
		createDir(path,javapath);
		createDir(path,ARTIFACTID+".src.main.resources");
		createDir(path,ARTIFACTID+".src.test.java");
		
		StringBuffer pom = new StringBuffer();
		if("spring boot".equals(TYPE)) {
			pom.append(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
							"<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" + 
					"    <modelVersion>4.0.0</modelVersion>\n");
			pom.append(
					"    <groupId>"+GROUPID+"</groupId>\n" + 
					"    <artifactId>"+ARTIFACTID+"</artifactId>\n" + 
					"    <version>"+VERSION+"</version>\n" +
					"    <packaging>jar</packaging>\n" +
							
					"    <dependencyManagement>\n" + 
					"        <dependencies>\n" + 
					"            <dependency>\n" + 
					"                <groupId>org.springframework.boot</groupId>\n" + 
					"                <artifactId>spring-boot-dependencies</artifactId>\n" + 
					"                <version>1.5.7.RELEASE</version>\n" + 
					"                <type>pom</type>\n" + 
					"                <scope>import</scope>\n" + 
					"            </dependency>\n" + 
					"        </dependencies>\n" + 
					"    </dependencyManagement>\n" +
					
					"    <dependencies>\n" + 
					"        <dependency>\n" + 
					"            <groupId>org.springframework.boot</groupId>\n" + 
					"            <artifactId>spring-boot-starter-web</artifactId>\n" + 
					"        </dependency>\n" +
					"    </dependencies>\n" +
					
					"    <build>\n" + 
					"        <pluginManagement>\n" + 
					"            <plugins>\n" + 
					"                <plugin>\n" + 
					"                    <groupId>org.apache.maven.plugins</groupId>\n" + 
					"                    <artifactId>maven-compiler-plugin</artifactId>\n" + 
					"                    <version>3.6.1</version>\n" + 
					"                    <configuration>\n" + 
					"                        <source>1.8</source>\n" + 
					"                        <target>1.8</target>\n" + 
					"                        <encoding>UTF-8</encoding>\n" + 
					"                    </configuration>\n" + 
					"                </plugin>\n" + 
					"            </plugins>\n" + 
					"        </pluginManagement>\n" + 
					"    </build>\n");
			pom.append("</project>");
		}else{
			pom.append(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
               "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n"+
               "    <modelVersion>4.0.0</modelVersion>\n"+
               "    <parent>\n"+
               "        <groupId>org.springframework.boot</groupId>\n"+
					"        <artifactId>spring-boot-starter-parent</artifactId>\n"+
					"        <version>1.5.7.RELEASE</version>\n"+
					"    </parent>\n"+
					"    <dependencyManagement>\n"+
					"        <dependencies>\n"+
					"            <dependency>\n"+
					"                <groupId>org.springframework.cloud</groupId>\n"+
					"                <artifactId>spring-cloud-dependencies</artifactId>\n"+
					"                <version>Dalston.SR3</version>\n"+
					"                <type>pom</type>\n"+
					"                <scope>import</scope>\n"+
					"            </dependency>\n"+
					"        </dependencies>\n"+
					"    </dependencyManagement>\n"+


					"    <groupId>"+GROUPID+"</groupId>\n" + 
					"    <artifactId>"+ARTIFACTID+"</artifactId>\n" + 
					"    <version>"+VERSION+"</version>\n" +
					"    <packaging>jar</packaging>\n" +

					"    <dependencies>\n" + 
					"        <dependency>\n" + 
					"            <groupId>org.springframework.boot</groupId>\n" + 
					"            <artifactId>spring-boot-starter-web</artifactId>\n" + 
					"        </dependency>\n" +
					"        <dependency>\n" + 
					"            <groupId>org.springframework.boot</groupId>\n" + 
					"            <artifactId>spring-boot-starter-actuator</artifactId>\n" + 
					"        </dependency>\n" +
					"    </dependencies>\n" +
					
					"    <repositories>\n"+
					"        <repository>\n"+
					"            <snapshots>\n"+
					"                <enabled>true</enabled>\n"+
					"            </snapshots>\n"+
					"            <id>public</id>\n"+
					"            <name>Public Repositories</name>\n"+
					"            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>\n"+
					"       </repository>\n"+
					"    </repositories>\n"+
					"    <pluginRepositories>\n"+
					"        <pluginRepository>\n"+
					"            <id>public</id>\n"+
					"            <name>Public Repositories</name>\n"+
					"            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>\n"+
					"        </pluginRepository>\n"+
					"    </pluginRepositories>\n"+
					"    <build>\n"+
					"        <plugins>\n"+
					"            <plugin>\n"+
					"                <groupId>org.springframework.boot</groupId>\n"+
					"                <artifactId>spring-boot-maven-plugin</artifactId>\n"+
					"            </plugin>\n"+
					"            <plugin>\n"+
					"               <groupId>org.apache.maven.plugins</groupId>\n"+
					"               <artifactId>maven-compiler-plugin</artifactId>\n"+
					"               <configuration>\n"+
					"                   <source>1.8</source>\n"+
					"                   <target>1.8</target>\n"+
					"                   <encoding>UTF-8</encoding>\n"+
					"               </configuration>\n"+
					"            </plugin>\n"+
					"        </plugins>\n"+
					"    </build>\n"+
					"</project>");
		}
		
		byte[] xmlbytes = pom.toString().getBytes(Charset.defaultCharset());
		File xmlfile = new File(path,
				ARTIFACTID + sp + "pom.xml");
		System.out.println("in path ----------->"+path.getPath());
		xmlfile.createNewFile();
		Files.write(xmlfile.toPath(), xmlbytes);
		
		StringBuffer mjava = new StringBuffer();
		
		mjava.append(
				"package "+GROUPID+"."+ARTIFACTID+";\n" + 
				"\n" + 
				"import org.springframework.boot.SpringApplication;\n" + 
				"import org.springframework.boot.autoconfigure.SpringBootApplication;\n" + 
				"import org.springframework.web.bind.annotation.RequestMapping;\n" + 
				"import org.springframework.web.bind.annotation.RestController;\n" + 
				"\n" + 
				"\n" + 
				"@SpringBootApplication\n" + 
				"@RestController\n" + 
				"public class MainConfigure {\n" + 
				"	@RequestMapping(\"/\")\n" + 
				"	public String sccess() {\n" + 
				"		return \"success\";\n" + 
				"	}\n" + 
				"	public static void main(String[] args) {\n" + 
				"		SpringApplication.run(MainConfigure.class, args);\n" + 
				"	}\n" + 
				"}\n");
		
		byte[] javabytes = mjava.toString().getBytes(Charset.defaultCharset());
		File javafile = new File(path,
				javapath.replace(".", sp) + sp + "MainConfigure.java");
		javafile.createNewFile();
		Files.write(javafile.toPath(), javabytes);
	
		java.awt.Desktop.getDesktop().open(path);
	}
	
	private static void createDir(File path, String value) {
		mkDir(new File(path, value.replace(".", sp)));
	}

	private static void mkDir(File file) {
		if (file.getParentFile().exists())
			file.mkdir();
		else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}
}
