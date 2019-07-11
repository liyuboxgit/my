package liyu.test.javaweb;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
public class StartServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> classSet, ServletContext servletContext) throws ServletException {
 
        //添加Servlet
        ServletRegistration.Dynamic dynamicServlet = servletContext.addServlet("demoServlet", new DemoStartServlet());
        //请求路径
        dynamicServlet.addMapping("/demo");
        //Servlet InitParam
        dynamicServlet.setInitParameter("demo", "demo");
        dynamicServlet.setLoadOnStartup(1);
        //添加过滤器
        FilterRegistration.Dynamic dynamicFilter = servletContext.addFilter("filter", new DemoFilter());
        dynamicFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, "demoServlet");
    }
}
