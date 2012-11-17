package org.dedeler.template.service.impl;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.dao.UserDao;
import org.dedeler.template.model.User;
import org.dedeler.template.service.LoggingService.LogType;
import org.dedeler.template.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Logged(type = LogType.SERVICE)
@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	@Autowired
	public UserServiceImpl(UserDao dao) {
		super(dao);
	}

	/**
	 * needed for aop cglib
	 */
	public UserServiceImpl() {
		super(null);
	}

	@Override
	public User findByUsername(final String username) {
		return ((UserDao) this.dao).findByUsername(username);
	}

}
