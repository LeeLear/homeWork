package com.gupaoedu.vip.spring.v1.demo.service.impl;


import com.gupaoedu.vip.spring.v1.demo.service.IDemoService;
import com.gupaoedu.vip.spring.v1.framework.annotation.Service;

@Service
public class DemoService implements IDemoService {

    public String get(String name) {
        return "My name is " + name;
    }

}
