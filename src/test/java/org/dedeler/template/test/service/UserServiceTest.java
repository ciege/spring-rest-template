package org.dedeler.template.test.service;

import static org.junit.Assert.assertNotNull;

import org.dedeler.template.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService userService;

	@Test
	public void getUserByUsername() {
		assertNotNull(userService);
	}
}
