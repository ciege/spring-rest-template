package org.dedeler.template.service;

import org.dedeler.template.dao.RoleDao;
import org.dedeler.template.model.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService extends GenericService<Role> {

	@Autowired
	public RoleService(RoleDao dao) {
		super(dao);
	}

	
}
