package org.dedeler.template.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TemplatePrivilege")
public class Privilege extends AbstractModel {

	private static final long serialVersionUID = 1L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	@Override
	public String toString() {
		return "Privilege [name=" + name + "]";
	}

}
