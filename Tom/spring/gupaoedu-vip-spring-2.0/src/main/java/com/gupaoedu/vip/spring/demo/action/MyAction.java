package com.gupaoedu.vip.spring.demo.action;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupaoedu.vip.spring.demo.service.IModifyService;
import com.gupaoedu.vip.spring.demo.service.IQueryService;
import com.gupaoedu.vip.spring.framework.annotation.GPAutowired;
import com.gupaoedu.vip.spring.framework.annotation.GPController;
import com.gupaoedu.vip.spring.framework.annotation.GPRequestMapping;
import org.spring.framework.web.bind.annotation.RequestParam;
import com.gupaoedu.vip.spring.framework.webmvc.servlet.GPModelAndView;
import org.spring.framework.beans.factory.annotation.Autowired;
import org.spring.framework.stereotype.Controller;
import org.spring.framework.web.bind.annotation.RequestMapping;
import org.spring.framework.web.bind.annotation.RequestParam;
import org.spring.framework.web.servlet.ModelAndView;

/**
 * 公布接口url
 * @author Tom
 *
 */
@Controller
@RequestMapping("/web")
public class MyAction {

	@Autowired
	IQueryService queryService;
	@Autowired IModifyService modifyService;

	@RequestMapping("/query.json")
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
							  @RequestParam("name") String name){
		String result = queryService.query(name);
		return out(response,result);
	}
	
	@RequestMapping("/add*.json")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,
			   @RequestParam("name") String name,@RequestParam("addr") String addr) throws Exception {
		String result = null;
//		try {
			result = modifyService.add(name,addr);
			return out(response,result);
//		} catch (Exception e) {
////			e.printStackTrace();
//			Map<String,Object> model = new HashMap<String,Object>();
//			model.put("detail",e.getMessage());
////			System.out.println(Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
//			model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
//			return new ModelAndView("500",model);
//		}

	}
	
	@RequestMapping("/remove.json")
	public ModelAndView remove(HttpServletRequest request,HttpServletResponse response,
		   @RequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@RequestMapping("/edit.json")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id") Integer id,
			@RequestParam("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}
	
	
	
	private ModelAndView out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
