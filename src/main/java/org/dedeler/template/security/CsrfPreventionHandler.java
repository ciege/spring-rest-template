package org.dedeler.template.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CsrfPreventionHandler extends HandlerInterceptorAdapter {

	// private static final String csrfTokenHeaderKey = "API_CSRF_TOKEN";// TODO externalize

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		return true;

		// TODO: design a csrf prevention scheme
		// if (request.getMethod().equalsIgnoreCase("GET") || request.getMethod().equalsIgnoreCase("HEAD")) {
		// return true;
		// }
		//
		// if (request.getSession() == null || request.getSession().getAttribute(csrfTokenHeaderKey) == null) {
		// return false;
		// }
		//
		// String token = request.getHeader(csrfTokenHeaderKey);
		// if (token == null || !token.equals(request.getSession().getAttribute(csrfTokenHeaderKey))) {
		// return true;
		// }
		//
		// return false;
	}
}
