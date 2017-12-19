package liyu.test.springmvc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class Starter {
	public static void main(String[] args) {
		int port = 8080;
        Server server = new Server(port);
        WebAppContext webAppContext = new WebAppContext("webapp","/");  

        webAppContext.setDescriptor("webapp/WEB-INF/web.xml");  
        webAppContext.setResourceBase("src/main/webapp");  
        webAppContext.setDisplayName("/");  
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());  
        webAppContext.setConfigurationDiscovered(true);  
        webAppContext.setParentLoaderPriority(true);  

        try {
            server.setHandler(webAppContext);  
            server.start();
            System.out.println("server is  start, port is "+port+"............");  
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
		//syncdb();
	}
	/**
	 * 
	 * @Title: syncdb 
	 * @Description: 使用hibernate建表
	 * @return: void
	 */
	public static void syncdb(){
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/hibernate.cfg.xml").build();  
        MetadataImplementor metadata = (MetadataImplementor) new MetadataSources( serviceRegistry ).buildMetadata();  
        new SchemaExport(metadata).create(true, true);
	}
}
