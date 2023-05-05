package com.sunman.soapwebservices.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapWebServiceConfiguration extends WsConfigurerAdapter {
	/**
	 * 	@EnableWs - Enables web services
		@Configuration - Tells spring container that this is a configuration class.
		MessageDispatcher Servlet - Takes all soap request and intendify which endpoint should get called.
		Servlet RegistrationBean - Map servlet to URI
		Any request comes with /ws/ will be executed by this messageDispatcherServlet.
		@Bean - we need a class who work as a messageDispatcherServlet
	 */

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> getMessageDispatcherServlet(ApplicationContext context){
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(messageDispatcherServlet, "/ws/*");
	}


	/**
	 * This will give course schema file.
	 */
	@Bean
	public XsdSchema getCourseSchema() {
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}

	/**
	 * Create WSDL file from xsd file.
	 * XsdSchema Auto wired with the getCourseSchema
	 * /ws/courses
	 */
	@Bean(name = "courses")
	public DefaultWsdl11Definition getDefaultWSDLFile(XsdSchema schema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("CoursePort");
		definition.setTargetNamespace("http://sunman.com/courses");
		definition.setLocationUri("/ws");
		definition.setSchema(schema);
		return definition;
	}


	@Bean
	public Wss4jSecurityInterceptor securityInterceptor() {
		Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
		securityInterceptor.setSecurementActions("UsernameToken");
		securityInterceptor.setSecurementUsername("user");
		securityInterceptor.setSecurementPassword("password");

		return securityInterceptor;
	}


	@Bean 
	public SimplePasswordValidationCallbackHandler callbackHandler() { 
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler(); 
		handler.setUsersMap(Collections.singletonMap("user", "password")); 
		return handler; 
	}


	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		//interceptors.add(securityInterceptor());
	}

}

