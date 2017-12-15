package liyu.test.mybatis;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateGenerator {
	public static void main(String[] args) {
		syncdb();
	}
	/**
	 * 
	 * @Title: syncdb 
	 * @Description: 使用hibernate建表
	 * @return: void
	 */
	public static void syncdb(){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("conf/hibernate.cfg.xml").build();  
		MetadataImplementor metadata = (MetadataImplementor) new MetadataSources( serviceRegistry ).buildMetadata();  
		new SchemaExport(metadata).create(true, true);
	}
}
