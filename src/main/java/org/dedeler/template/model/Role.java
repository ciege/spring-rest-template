package org.dedeler.template.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "TemplateRole")
public class Role extends AbstractModel implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Privilege> privileges;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", privileges=" + privileges + "]";
	}

}
