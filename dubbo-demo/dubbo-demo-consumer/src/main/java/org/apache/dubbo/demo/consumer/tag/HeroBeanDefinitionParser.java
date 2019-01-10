package org.apache.dubbo.demo.consumer.tag;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class HeroBeanDefinitionParser implements BeanDefinitionParser {
    private final Class<?> beanClass;

    public HeroBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().add("name", element.getAttribute("name"));
        beanDefinition.getPropertyValues().add("age", element.getAttribute("age"));
        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        beanDefinitionRegistry.registerBeanDefinition(beanClass.getName(),beanDefinition);//注册bean到BeanDefinitionRegistry中
        return beanDefinition;
    }
}