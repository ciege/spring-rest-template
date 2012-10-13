package org.dedeler.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="GodfatherUser")
public class User extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	@NotNull
	@Length(min = 3)
	private String username;

	@Column(unique = true, nullable = true)
	private String facebookId;

	@NotNull
	@Length(min = 6)
	private String password;

	private String firstName;

	private String lastName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
