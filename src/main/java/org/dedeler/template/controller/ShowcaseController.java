package org.dedeler.template.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	@Value("${test.message}")
	private String test;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Result home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is " + locale.toString());
		logger.info(test);

		List<String> list = new ArrayList<String>();
		list.add("sample");
		list.add("result");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		list.add("username is: " + authentication.getName());
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			list.add(auth.getAuthority());
		}

		return new Builder(true).resultObject(list).build();
	}

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

	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	@ResponseBody
	public Result exception(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is " + locale.toString());

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		ApiException e = new ApiException(ErrorCode.UNKNOWN_ERROR);

		Result result = new Builder(e, locale).build();

		return result;
	}
	

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize(value = "hasPermission(null,'PERMISSION_ADMIN_CONTROLLER')")
	public List<String> admin(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is " + locale.toString());
		logger.info(test);

		List<String> list = new ArrayList<String>();
		list.add("hi");
		list.add("slut");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		list.add(authentication.getName());
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			list.add(auth.getAuthority());
		}

		return list;

	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView page(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is " + locale.toString());
		logger.info(test);
		

		ModelAndView result = new ModelAndView("home");
		result.addObject("serverTime", new Date());
		return result;

	}

}
