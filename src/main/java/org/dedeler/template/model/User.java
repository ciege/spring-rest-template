package org.dedeler.template.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "TemplateUser")
public class User extends AbstractModel implements UserDetails {
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

	@OneToMany
	@JsonIgnore
	private List<Role> authorities;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked = true;

	private boolean credentialsNonExpired = true;

	private boolean enabled = true;

	@Override
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

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	@JsonProperty
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	@JsonProperty
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	@JsonProperty
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	@JsonProperty
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

	@JsonIgnore
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@JsonIgnore
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@JsonIgnore
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@JsonIgnore
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", facebookId=" + facebookId + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", authorities=" + authorities + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + "]";
	}

}
