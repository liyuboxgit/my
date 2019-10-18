package liyu.test.springbootCfx;

import java.util.ArrayList;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import com.alibaba.fastjson.JSON;

public class WebServiceCli {
	public static void main(String[] args){
        //在一个方法中连续调用多次WebService接口,每次调用前需要重置上下文
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        System.out.println("学生的信息如下：================");
        printStudentList(dcf);
        //在调用第二个webservice前，需要重置上下文
        Thread.currentThread().setContextClassLoader(cl);

        System.out.println("用户的信息如下：================");
        printUserList(dcf);
    }

    private static void printUserList(JaxWsDynamicClientFactory dcf){
        Client client = dcf.createClient("http://localhost:8080/services/UserService?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("getUserList", "张三");
            if(objects.length>0){
                @SuppressWarnings("unchecked")
				ArrayList<Object> objectList = (ArrayList<Object>)objects[0];
                for (Object o:objectList){
                	String jsonString = JSON.toJSONString(o);
                	System.out.println("USER:"+jsonString);
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static void printStudentList(JaxWsDynamicClientFactory dcf){
        Client client = dcf.createClient("http://localhost:8080/services/StudentService?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("getStudentList","");
            if(objects.length>0){
            	@SuppressWarnings("unchecked")
				ArrayList<Object> objectList = (ArrayList<Object>)objects[0];
                for (Object o:objectList){
                	String jsonString = JSON.toJSONString(o);
                	System.out.println("STUDENT:"+jsonString);
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
