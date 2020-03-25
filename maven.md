-- maven配置本地化依赖包
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

	<groupId>com.h3bpm</groupId>
	<artifactId>h3bpm-portal-web</artifactId>
	<version>10.7-TaiKang-RELEASE</version>
	<modelVersion>4.0.0</modelVersion>

	<packaging>war</packaging>

	<name>portal-web Maven Webapp</name>
	<url>http://www.example.com</url>

	<dependencies>
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>3.0.1</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>javax.servlet.jsp</groupId>
			<artifactId>jsp-api</artifactId>
			<version>2.1</version>
			<scope>provided</scope>
		</dependency>
	</dependencies>

	<build>
		<finalName>h3bpm-portal-web</finalName>
		<plugins>
			<plugin>
				<artifactId>maven-deploy-plugin</artifactId>
				<configuration>
					<skip>true</skip>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.3</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
					<compilerArguments>
						<extdirs>${basedir}/src/main/webapp/WEB-INF/lib</extdirs>
					</compilerArguments>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
二、maven在eclipse中出现Unknew Error，在properties中加入<maven-jar-plugin.version>3.1.1</maven-jar-plugin.version>
三、配置阿里云镜像
	<repositories>
        <repository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>public</id>
            <name>Public Repositories</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
       </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>public</id>
            <name>Public Repositories</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        </pluginRepository>
    </pluginRepositories>
	或者：
	<mirrors>
		<mirror>
			<id>nexus</id>
			<mirrorOf>central</mirrorOf>
			<name>local maven</name>
			<url>http://120.78.180.15:8081/nexus/content/groups/public/</url>
		</mirror>
	</mirrors>




























