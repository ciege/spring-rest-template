package org.dedeler.template.service;

import java.util.Collection;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.dao.UserDao;
import org.dedeler.template.model.User;
import org.dedeler.template.service.LoggingService.LogType;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncryptionException;
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
		User newUser = new User();
		copyFields(user, newUser, new String[]{"username","facebookId","firstName", "lastName"}, User.class);
		try {
			String hashedPassword = ESAPI.encryptor().hash((String) user.getPassword(), user.getUsername());
			user.setPassword(hashedPassword);
		} catch (EncryptionException e) {
			logger.error(e.getLogMessage(),e);
			throw new RuntimeException(); //TODO: really?
		}
		
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
