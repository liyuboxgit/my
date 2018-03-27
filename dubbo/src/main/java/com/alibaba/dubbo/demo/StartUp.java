package com.alibaba.dubbo.demo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartUp {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"com/alibaba/dubbo/demo/dubbo-demo-provider.xml"});
        context.start();
        // press any key to exit
        System.in.read();
    }
}