package org.apache.dubbo.demo.consumer.tag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class HeroNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("hero", new HeroBeanDefinitionParser(Hero.class));
    }
}