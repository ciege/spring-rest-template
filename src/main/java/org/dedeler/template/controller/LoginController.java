package org.dedeler.template.controller;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.exception.ErrorCode;
import org.dedeler.template.model.User;
import org.dedeler.template.service.LoggingService.LogType;
import org.dedeler.template.view.Result;
import org.dedeler.template.view.Result.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Logged(type = LogType.CONTROLLER)
@Controller
@RequestMapping(value="/login")
public class LoginController extends AbstractController {

	
	@Autowired
	@Qualifier(value="authenticationManager")
	private AuthenticationManager authenticationManager;
	
	private SimpleGrantedAuthority anonymousRole = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
	
	@RequestMapping("/status")
	@ResponseBody
	public Result getStatus(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LoginStatus loginStatus = null;
		
		if(auth!=null && !isAnonymous(auth) && auth.isAuthenticated()){
			loginStatus = new LoginStatus(true, auth.getName());
		}else{
			loginStatus = new LoginStatus(false, null);
		}
		return new Builder(true).resultObject(loginStatus).build();
	}
	@RequestMapping(method=RequestMethod.POST,value="/")
	@ResponseBody
	public Result login(@RequestBody User user){
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			LoginStatus loginStatus = new LoginStatus(auth.isAuthenticated(), auth.getName());
			return new Builder(true).resultObject(loginStatus).build();
		} catch (BadCredentialsException e) {
			LoginStatus loginStatus = new LoginStatus(false, null);
			return new Builder(false).errorCode(ErrorCode.INVALID_CREDENTIALS).resultObject(loginStatus).build();
		}
	}
	
	private boolean isAnonymous(Authentication auth){
		return auth.getAuthorities().contains(anonymousRole);
	}
	
	public class LoginStatus {
		 
	    private final boolean loggedIn;
	    private final String username;
	 
	    public LoginStatus(boolean loggedIn, String username) {
	      this.loggedIn = loggedIn;
	      this.username = username;
	    }
	 
	    public boolean isLoggedIn() {
	      return loggedIn;
	    }
	 
	    public String getUsername() {
	      return username;
	    }
	  } 
}
