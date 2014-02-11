package org.dedeler.template.service;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.dao.RoleDao;
import org.dedeler.template.model.Role;
import org.dedeler.template.service.LoggingService.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Logged(type = LogType.SERVICE)
@Service
@Transactional
public class RoleService extends GenericService<Role> {

	@Autowired
	public RoleService(RoleDao dao) {
		super(dao);
	}

	public RoleService() {
		super(null);
		// needed for aop cglib
	}


}
