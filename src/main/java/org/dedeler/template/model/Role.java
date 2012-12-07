package org.dedeler.template.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

/**
 * Represents a role. 
 * @author yasa
 *
 */
@Entity
@Table(name = "TemplateRole")
public class Role extends AbstractModel implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * We'll almost always need the privilege list
	 * initialized, so we set the fetch type to eager.
	 * TemplatePermissionEvaluator#hasPermission relies
	 * on this. 
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private List<Privilege> privileges;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	@JsonIgnore
	public String getAuthority() {
		return this.name;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", privileges=" + privileges + "]";
	}

	@JsonProperty
	public List<Privilege> getPrivileges() {
		return privileges;
	}

	@JsonIgnore
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

}
