package com.gupaoedu.vip.spring.v1.demo.mvc.action;


import com.gupaoedu.vip.spring.v1.demo.service.IDemoService;
import com.gupaoedu.vip.spring.v1.framework.annotation.Autowired;
import com.gupaoedu.vip.spring.v1.framework.annotation.Controller;
import com.gupaoedu.vip.spring.v1.framework.annotation.RequestMapping;
import com.gupaoedu.vip.spring.v1.framework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/demo")
public class DemoAction {

    @Autowired
    private IDemoService demoService;

    @RequestMapping("/query.json")
    public String query(HttpServletRequest req, HttpServletResponse resp,
                      @RequestParam("name") String name,@RequestParam("age")String age){
        String result = demoService.get(name)+"年龄为："+age;
        System.out.println(result);
//		try {
//			resp.getWriter().write(result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        return result;
    }

    @RequestMapping("/edit.json")
    public void edit(HttpServletRequest req, HttpServletResponse resp, Integer id){

    }

}
