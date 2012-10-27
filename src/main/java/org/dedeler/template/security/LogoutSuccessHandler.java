package org.dedeler.template.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dedeler.template.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	private MappingJacksonHttpMessageConverter converter;

	@Autowired
	public LogoutSuccessHandler(MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter) {
		converter = mappingJacksonHttpMessageConverter;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		response.setContentType("application/json");

		Result logoutResult = new Result.Builder(true).message("Logged out successfully").build();

		String json = converter.getObjectMapper().writeValueAsString(logoutResult);
		response.getWriter().write(json);
		return;
	}
}
