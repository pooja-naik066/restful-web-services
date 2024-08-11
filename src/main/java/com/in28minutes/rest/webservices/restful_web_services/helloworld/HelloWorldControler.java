package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

//REST API
@RestController
public class HelloWorldControler {

	private MessageSource messageSource;

	public HelloWorldControler(MessageSource messageSource){
		this.messageSource=messageSource;
	}
	
	@GetMapping(path="/helloworld")
	public String helloWorld() {
		return "Hello World";
	}
 
	@GetMapping("/helloworldbean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping("/helloworld/pathvariable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s",name));
	}
	@GetMapping(path="/helloworld-internationalized")
	public String helloWorldInternationalized() {
		Locale locale= LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message",null,"Default message",locale);


	}
}
