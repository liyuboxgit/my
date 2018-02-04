package liyu.test.ireport;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartUp {
	//ireport version is 5.5.2,download url:https://sourceforge.net/projects/ireport/files/iReport/
	//note use jdk 1.6
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
	}

}
