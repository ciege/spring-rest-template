package org.dedeler.template.test.service;

import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * All test classes shall extend this class.
 * 
 * @author destan
 * 
 */
@ContextConfiguration(locations = { "/testContext.xml" })
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

	private MockHttpSession session;
	private MockHttpServletRequest request;

	/**
	 * Thank you SO: http://stackoverflow.com/a/5140022/878361
	 */
	private void startRequest() {
		request = new MockHttpServletRequest();
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		// creating session
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		attr.getRequest().getSession(true);
	}

	/**
	 * Initialize a mock request
	 */
	@Before
	public void initialize() {
		startRequest();
	}
}
