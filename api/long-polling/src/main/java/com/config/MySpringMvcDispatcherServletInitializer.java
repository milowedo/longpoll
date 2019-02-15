package com.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppConfiguration.class };
	}

	@NotNull
    @Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}






