package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestMatchInfo;
import org.springframework.web.servlet.support.RequestMatchResolver;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	RequestMatchResolver mvcMatcher;

	@Autowired
	public void setHandlerMappings(List<HandlerMapping> handlerMappings) {
		mvcMatcher = new RequestMatchResolver();
		mvcMatcher.setHandlerMappings(handlerMappings);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
				.requestMatchers(request -> mvcMatcher.resolve(request).match(request, new RequestMatchInfo("/denyAll"))).denyAll()
				.and()
			.httpBasic();
	}


}
