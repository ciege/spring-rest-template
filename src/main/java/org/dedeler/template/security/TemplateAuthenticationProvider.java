package org.dedeler.template.security;

import java.util.Collection;

import org.dedeler.template.model.User;
import org.dedeler.template.service.UserService;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncryptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class TemplateAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		Object password = authentication.getCredentials();
		try {
			String hashedPassword = ESAPI.encryptor().hash((String) password, username);
			User user = userService.findByUsername(username);

			if (user == null) {
				throw new BadCredentialsException("Username not found."); // TODO message
			}

			if (!hashedPassword.equals(user.getPassword())) {
				throw new BadCredentialsException("Wrong password."); // TODO message
			}

			Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
			return new UsernamePasswordAuthenticationToken(username, password, authorities);

		}
		catch (EncryptionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
