package liyu.test.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GenerateJavaCode {
	public static void main(String[] args) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<String>();
	    boolean overwrite = true;
	   
	    InputStream inputStream = Resources.getResourceAsStream("conf/mybatic-generator.xml");
	    
	    ConfigurationParser cp = new ConfigurationParser(warnings);
	    Configuration configuration = cp.parseConfiguration(inputStream);
	    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration,
	            callback, warnings);
	    
	    myBatisGenerator.generate(null);
	}
}
