package com.dronesfree.broadcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/stream")
public class StreamController {

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView showIndexVideoPage(@PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView("videostream");
		modelAndView.addObject("orderId", id);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showVideoPage() {
		return new ModelAndView("videostream");
	}
}
