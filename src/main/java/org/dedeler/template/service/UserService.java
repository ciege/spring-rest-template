package org.dedeler.template.service;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.dao.UserDao;
import org.dedeler.template.model.User;
import org.dedeler.template.service.LoggingService.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Logged(type = LogType.SERVICE)
@Service
@Transactional
public class UserService extends GenericService<User> {

	@Autowired
	public UserService(UserDao dao) {
		super(dao);
	}

	public UserService() {
		super(null);
		// needed for aop cglib
	}

	public User findByUsername(final String username) {
		return ((UserDao) this.dao).findByUsername(username);
	}

}
