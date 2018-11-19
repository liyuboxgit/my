package com.rthd.tinychxu.util.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.rthd.framework.util.Conf;
import com.rthd.tinychxu.domain.entity.Demo;
import com.rthd.tinychxu.domain.entity.User;

public class HibernateCreateTable {
	
	public static void main(String[] args) throws IOException {
		Path xml = Paths.get(System.getProperty("user.home"), "hibernate.cfg.xml");
		if(xml.toFile().exists())
			xml.toFile().delete();
		
		xml.toFile().createNewFile();
		// User needs to changed; 
		Files.write(xml,Conf.get(User.class).toHibernateConfigString().getBytes());
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure(xml.toFile()).build();
		MetadataImplementor metadata = (MetadataImplementor) new MetadataSources(
		serviceRegistry ).buildMetadata();
		//create (true,true):print console and execute in database
		//create (true,false):print console only
		new SchemaExport(metadata).create(true, true);
		System.exit(-1);
	}
	
}
