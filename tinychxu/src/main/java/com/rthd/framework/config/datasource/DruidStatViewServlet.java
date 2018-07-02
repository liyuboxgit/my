package com.rthd.framework.config.datasource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.StatViewServlet;

@WebServlet(urlPatterns = "/druid/*", initParams={  
    @WebInitParam(name="loginUsername",value="admin"),
    @WebInitParam(name="loginPassword",value="admin"), 
    @WebInitParam(name="resetEnable",value="true")
}) 
public class DruidStatViewServlet extends StatViewServlet {
	private static final long serialVersionUID = 2359758657306626394L;

}