package com.expeditors.musictracking.controller.jconfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Component
public class AuthExceptionHandler implements AuthenticationEntryPoint {

	//This little puppy resolves the given exception to a Spring
	//@ExceptionHandler.  We have one in .../exceptions/LastStopHandler.java
	@Autowired(required = false)
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		resolver.resolveException(request, response, null, exception);
	}
}
