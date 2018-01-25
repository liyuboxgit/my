package liyu.test.sb.archtypeCodec;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Date;

public class Archtype {
	private static String sp = File.separator;
	public enum ARCH{
		GROUPID("liyu.test"),ARTIFACTID("sb_1"),VERSION("0.0.1-SNAPSHOT"),
		//spring boot,spring cloud
		TYPE("spring cloud");
		
		private String value;

		ARCH(String value) {
			this.value = value;
		}
	}
	
	public static void main(String[] args) throws IOException {
		File path = new File(System.getProperty("user.home") + sp + "maven_" + new Date().getTime());
		String javapath = ARCH.ARTIFACTID.value + ".src.main.java" +"."+ARCH.GROUPID.value + "."+ARCH.ARTIFACTID.value;
		createDir(path,javapath);
		createDir(path,ARCH.ARTIFACTID.value+".src.main.resources");
		createDir(path,ARCH.ARTIFACTID.value+".src.test.java");
		
		StringBuffer pom = new StringBuffer();
		if("spring boot".equals(ARCH.TYPE.value)) {
			pom.append(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
							"<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" + 
					"    <modelVersion>4.0.0</modelVersion>\n");
			pom.append(
					"    <groupId>"+ARCH.GROUPID.value+"</groupId>\n" + 
					"    <artifactId>"+ARCH.ARTIFACTID.value+"</artifactId>\n" + 
					"    <version>"+ARCH.VERSION.value+"</version>\n" +
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
					"                <version>Camden.SR4</version>\n"+
					"                <type>pom</type>\n"+
					"                <scope>import</scope>\n"+
					"            </dependency>\n"+
					"        </dependencies>\n"+
					"    </dependencyManagement>\n"+


					"    <groupId>"+ARCH.GROUPID.value+"</groupId>\n" + 
					"    <artifactId>"+ARCH.ARTIFACTID.value+"</artifactId>\n" + 
					"    <version>"+ARCH.VERSION.value+"</version>\n" +
					"    <packaging>jar</packaging>\n" +

					"    <dependencies>\n" + 
					"        <dependency>\n" + 
					"            <groupId>org.springframework.boot</groupId>\n" + 
					"            <artifactId>spring-boot-starter-web</artifactId>\n" + 
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
				ARCH.ARTIFACTID.value + sp + "pom.xml");
		xmlfile.createNewFile();
		Files.write(xmlfile.toPath(), xmlbytes);
		
		StringBuffer mjava = new StringBuffer();
		
		mjava.append(
				"package "+ARCH.GROUPID.value+"."+ARCH.ARTIFACTID.value+";\n" + 
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
