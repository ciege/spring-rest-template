package org.dedeler.template.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.dedeler.template.exception.GodfatherException;
import org.dedeler.template.model.User;
import org.dedeler.template.service.UserService;
import org.dedeler.template.view.Result;
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
@Controller
@RequestMapping(value = "/showcase")
public class ShowcaseController {

	private static final Logger logger = LoggerFactory.getLogger(ShowcaseController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	@ResponseBody
	public long createUser() {
		User user = new User();
		user.setUsername("dds");
		user.setPassword("dedeler");
		user.setFirstName("Destan");
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
	public Result<Object> home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is " + locale.toString());

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		GodfatherException e = new GodfatherException(1000);

		return e.toResult();
	}

}
