package org.dubbo.consumer.service;

import java.util.Date;

import org.dubbo.provider.service.DemoServices;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

@Component
public class ConsumerServiceImpl implements ConsumerService{

    @Reference(version = "1.0.0")
    private DemoServices demoServices;
    @Override
    public String ConService() throws Exception {
        return demoServices.toProvider(new Date().getTime()+"");
    }
}