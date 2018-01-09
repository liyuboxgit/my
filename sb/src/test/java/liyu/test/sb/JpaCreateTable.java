package liyu.test.sb;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class JpaCreateTable {
	public static void main(String[] args) {
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("liyu/test/sb/mybatis/hibernate.cfg.xml").build();		
        MetadataImplementor metadata = (MetadataImplementor) new MetadataSources( serviceRegistry ).buildMetadata();  
        new SchemaExport(metadata).create(true, true);
	}
}
