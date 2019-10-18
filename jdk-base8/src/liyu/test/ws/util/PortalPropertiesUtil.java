package liyu.test.ws.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 所有系统的配置都从这获取
 * @author luwei
 *
 */
public class PortalPropertiesUtil {
	
    private static final String PROPERTIES_FILE = "liyu/test/ws/ws.properties";

    private static Properties _properties = null;

    public static String getProperty(String key){
    	if (_properties == null) {
    		Properties prop = new Properties();
        	try {
        		InputStream in = PortalPropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        		prop.load(in);
        		_properties = prop;
    		} catch (Exception e) {
    			
    			throw new RuntimeException(e);
    		}
		}
    	
    	return _properties.getProperty(key);
    }

    public static String getPropertyNoCache(String key) {
        Properties prop = new Properties();
        try {
            InputStream in = PortalPropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            prop.load(in);
            return prop.getProperty(key);
        } catch (Exception e) {
           
            throw new RuntimeException(e);
        }
    }

    public static String getConfigFileName() {
        return PROPERTIES_FILE;
    }

}
