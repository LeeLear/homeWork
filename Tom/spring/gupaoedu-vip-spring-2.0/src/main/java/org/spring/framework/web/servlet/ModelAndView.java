/*
 * Copyright 2002-2017 the original author or authors.
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

package org.spring.framework.web.servlet;


import java.util.Map;

public class ModelAndView {

	/** View instance or view name String */

	private Object view;

	private String viewName;

	private Map<String,?> model;

	/** Indicates whether or not this instance has been cleared with a call to {@link #clear()} */
	private boolean cleared = false;
	/**
	 * Default constructor for bean-style usage: populating bean
	 * properties instead of passing in constructor arguments.
	 * @see #setView(View)
	 * @see #setViewName(String)
	 */
	public ModelAndView() {
	}

	public ModelAndView(String viewName, Map<String, ?> model) {
		this.viewName = viewName;
		this.model = model;
	}

	public ModelAndView(String viewName) {
		this.viewName = viewName;
	}

	public ModelAndView(Map<String, ?> model) {
		this.model = model;
	}

	public String getViewName(){
		return viewName;
	}
	public Map<String,?>getModel(){
		return model;
	}
	public void setModel(Map<String,?> model){
		this.model= model;
	}

}
