package com.example;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {
	private static final String DENY_ALL = "/denyAll";

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void denyAll() throws Exception {
		mockMvc.perform(get(DENY_ALL))
			.andExpect(isDenied());
	}

	@Test
	public void denyAllSlash() throws Exception {
		mockMvc.perform(get(DENY_ALL+"/"))
			.andExpect(isDenied());
	}

	@Test
	public void denyAllFileExtension() throws Exception {
		mockMvc.perform(get(DENY_ALL+".html"))
			.andExpect(isDenied());
	}

	@Test
	public void denyAllQuery() throws Exception {
		mockMvc.perform(get(DENY_ALL).param("foo", "bar"))
			.andExpect(isDenied());
	}

	@Test
	public void denyAllPost() throws Exception {
		mockMvc.perform(post(DENY_ALL).with(csrf()))
			.andExpect(isDenied());
	}

	private ResultMatcher isDenied() {
		return status().isUnauthorized();
	}
}
