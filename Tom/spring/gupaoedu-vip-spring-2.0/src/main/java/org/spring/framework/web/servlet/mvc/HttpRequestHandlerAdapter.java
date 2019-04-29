/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spring.framework.web.servlet.mvc;

import org.spring.framework.utils.StringUtils;
import org.spring.framework.web.bind.annotation.RequestParam;
import org.spring.framework.web.servlet.HandlerAdapter;
import org.spring.framework.web.servlet.ModelAndView;
import org.spring.framework.web.servlet.handler.AbstractHandlerMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



/**
 * <p>This is an SPI class, not used directly by application code.
 *
 * @author Juergen Hoeller
 * @since 2.0

 */
public class HttpRequestHandlerAdapter implements HandlerAdapter {

	private String HTTP_SERVLET_REQUEST_NAME=HttpServletRequest.class.getName();
	private String HTTP_SERVLET_RESPONSE_NAME=HttpServletResponse.class.getName();

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof AbstractHandlerMapping);//(handler instanceof HttpRequestHandler);
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping)(handler);

		Map<String,Integer> paramIndexMapping = new HashMap<>();

		Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
		for (int i = 0 ; i<pa.length;i++){
			for (Annotation annotation : pa[i]) {
				if (annotation instanceof RequestParam){
					String paramName = ((RequestParam)annotation).value();
					if (StringUtils.hasText(paramName)){
						paramIndexMapping.put(paramName,i);
					}
				}
			}
		}

		//提取方法中的request和response参数
		Class<?> [] paramsTypes = handlerMapping.getMethod().getParameterTypes();
		for (int i =0;i<paramsTypes.length;i++){
			Class<?> type = paramsTypes[i];
			if (type==HttpServletRequest.class ||
					type == HttpServletResponse.class){
				paramIndexMapping.put(type.getName(),i);
			}
		}


		Map<String,String[]> params = request.getParameterMap();

		//实参列表
		Object [] paramValues = new Object[paramsTypes.length];

		for (Map.Entry<String, String[]> param : params.entrySet()) {
			String value = Arrays.toString(param.getValue()).
					replaceAll("\\[|\\]","").
					replaceAll("\\s",",");//空白符
			if(!paramIndexMapping.containsKey(param.getKey())){
				continue;
			}
			int index = paramIndexMapping.get(param.getKey());
			paramValues[index] = convert(paramsTypes[index],value);
		}
		if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
			int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
			paramValues[reqIndex] = request;
		}
		if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
			int reqIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
			paramValues[reqIndex] = response;
		}

		Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);
		if (result==null||result instanceof Void){
			return null;
		}

		boolean isModelAndView = handlerMapping.getMethod().getReturnType()==ModelAndView.class;
		if (isModelAndView){
			return (ModelAndView)result;
		}

		return null;
	}

	private void initParamValues(Object[] paramValues,Map<String,Integer> paramIndexMapping){

	}

	private Object convert(Class<?> paramsType, String value) {
		if (Integer.class == paramsType){
			return Integer.valueOf(value);
		}
		else if (Double.class==paramsType){
			return Double.valueOf(value);
		}else if (String.class==paramsType){
			return value;
		}
		return null;
	}

	@Override
	public long getLastModified(HttpServletRequest request, Object handler) {

		return -1L;
	}

}
