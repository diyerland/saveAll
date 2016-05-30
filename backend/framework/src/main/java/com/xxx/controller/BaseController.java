package com.xxx.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gray <long1795@gmail.com>
 */
public abstract class BaseController {
	final static Map<String, String> viewNameMap = new HashMap<String, String>();
	final static String dftViewName = "jsonView";

	static {
		viewNameMap.put("json", "jsonView");
		viewNameMap.put("xml", "xStreamMarshallingView");
	}

	protected ModelAndView mav() {
		return mav("json");
	}

	protected ModelAndView mav(String type) {
		String viewName = viewNameMap.get(type);
		if (viewName == null) {
			viewName = dftViewName;
		}
		return new ModelAndView(viewName);
	}

	protected ModelAndView error(Exception ex) {
		return mav("json").addObject("code", -1).addObject("msg", ex.getMessage());
	}

	protected ModelAndView error(String message) {
		return mav("json").addObject("code", -1).addObject("msg", message);
	}

	protected ModelAndView error(int code,String message) {
		return mav("json").addObject("code", code).addObject("msg", message);
	}

	protected ModelAndView succeed(Object data) {
		return mav("json").addObject("code", 0).addObject("data", data);
	}

	@RequestMapping
	public String index(ModelMap model) {
		return "/index";
	}

}
