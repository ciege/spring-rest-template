package org.dedeler.template.dao;

import org.dedeler.template.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends GenericDao<User> {

	public User findByUsername(final String username) {
		final Criteria c = createCriteria(User.class).add(Restrictions.eq("username", username));
		return (User) c.uniqueResult();// TODO what happens if result is null?
	}
}
