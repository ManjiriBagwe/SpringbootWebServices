package com.sunman.org.restfulwebservices.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunman.org.restfulwebservices.bean.HelloWorldBean;

@RestController
public class HelloWorldController {

	/*@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	public String sayHelloWorld() {
		return "Hello World";
	}*/
	
	//Or Use GetMapping/PostMapping/...
	
	@GetMapping(path = "/hello-world")
	public String sayHelloWorld() {
		return "Hello World!";
	}
	
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean sayHelloWorldBean() {
		return new HelloWorldBean("Hello World Bean!");
	}
	
	
	/* This is used for reading message.property file for welcome message(Spanish, German) */
	MessageSource messageSource;
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping(path = "/hello-world-internationalization")
	public String sayHelloWorldInternationalization() {
		Locale local = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default message", local);
	}
	
}
