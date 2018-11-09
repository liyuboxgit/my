package liyu.test.springbootMybatis.druidFilter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.StatViewServlet;

@WebServlet(urlPatterns = "/static/druid/*", initParams={  
    @WebInitParam(name="loginUsername",value="admin"),
    @WebInitParam(name="loginPassword",value="admin"), 
    @WebInitParam(name="resetEnable",value="true")
})  /*http://ip:port/domain/static/druid */
public class DruidStatViewServlet extends StatViewServlet {
	private static final long serialVersionUID = 2359758657306626394L;

}