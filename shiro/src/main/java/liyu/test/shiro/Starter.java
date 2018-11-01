package liyu.test.shiro;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

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
		/*ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/hibernate.cfg.xml").build();  
        MetadataImplementor metadata = (MetadataImplementor) new MetadataSources( serviceRegistry ).buildMetadata();  
        new SchemaExport(metadata).create(true, true);*/
	}
}
