package com.gupaoedu.vip.spring.v1.demo.mvc.action;


import com.gupaoedu.vip.spring.v1.demo.service.IDemoService;
import com.gupaoedu.vip.spring.v1.framework.annotation.Autowired;
import com.gupaoedu.vip.spring.v1.framework.annotation.Controller;
import com.gupaoedu.vip.spring.v1.framework.annotation.RequestMapping;

@Controller
public class MyAction {

    @Autowired
    IDemoService demoService;

    @RequestMapping("/index.html")
    public void query(){

    }

}
