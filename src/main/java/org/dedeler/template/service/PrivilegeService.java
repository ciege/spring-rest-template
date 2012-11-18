package org.dedeler.template.service;

import org.dedeler.template.dao.PrivilegeDao;
import org.dedeler.template.model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeService extends GenericService<Privilege> {

	@Autowired
	public PrivilegeService(PrivilegeDao dao) {
		super(dao);
	}

}
