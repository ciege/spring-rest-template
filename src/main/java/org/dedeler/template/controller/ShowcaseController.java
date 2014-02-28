package org.dedeler.template.controller;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.exception.ApiException;
import org.dedeler.template.exception.ErrorCode;
import org.dedeler.template.model.Role;
import org.dedeler.template.model.User;
import org.dedeler.template.service.LoggingService.LogType;
import org.dedeler.template.service.RoleService;
import org.dedeler.template.service.UserService;
import org.dedeler.template.view.Result;
import org.dedeler.template.view.Result.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Logged(type = LogType.CONTROLLER)
@Controller
@RequestMapping(value = "/showcase")
public class ShowcaseController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ShowcaseController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	@ResponseBody
	public long createUser() {
		Role role = new Role("ROLE_USER");
				
		User user = new User();
		user.setUsername("admin");
		user.setPassword("7lLEodyoRSvB9W6Rhjc+xfabU0ITmcdbjaW4MfARG5TOb/N7TeMxDB85j/HSm8t1h6pTrATIXySR+yQ5jMo39Q==");// admin
		user.setFirstName("Destan");
		user.setAuthorities(Arrays.asList(role));
		
		roleService.save(role);
		return userService.save(user);
	}

	@RequestMapping(value = "/getUser/username/{username}", method = RequestMethod.GET)
	@ResponseBody
	public User findUserByEmail(@PathVariable("username") String username) {
		return userService.findByUsername(username);
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	@ResponseBody
	public Result home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is " + locale.toString());

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		ApiException e = new ApiException(ErrorCode.UNKNOWN_ERROR);

		Result result = (new Builder(e, locale)).build();

		return result;
	}

}
