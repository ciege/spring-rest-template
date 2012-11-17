package org.dedeler.template.service;

import org.dedeler.template.model.User;

public interface UserService extends GenericService<User> {

	User findByUsername(final String username);

}
