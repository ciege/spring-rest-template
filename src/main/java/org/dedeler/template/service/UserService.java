package org.dedeler.template.service;

import java.util.Calendar;
import java.util.Collection;

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
	
	public User create(User user){
		
		//Clean dirty fields
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setAuthorities(null);
		user.setCreationDate(Calendar.getInstance());
		user.setCredentialsNonExpired(true);
		user.setDeleted(false);
		user.setDeletionDate(null);
		user.setEnabled(true);
		user.setModificationDate(null);
		user.setOid(null);
		
		Long id = this.dao.save(user); //FIXME: validation exceptions?
		User createdUser = this.dao.findById(User.class, id);
		return createdUser;
	}
	
	public Collection<User> listAll(){
		Collection<User> userList = dao.findAll(User.class);
		return userList;
	}
	
	public boolean delete(Long oid){
		return delete(oid, User.class);
	}
	
	

}
