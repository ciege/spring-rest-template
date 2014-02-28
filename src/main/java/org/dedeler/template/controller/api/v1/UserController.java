package org.dedeler.template.controller.api.v1;

import java.util.Collection;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.controller.AbstractController;
import org.dedeler.template.model.User;
import org.dedeler.template.service.LoggingService.LogType;
import org.dedeler.template.service.UserService;
import org.dedeler.template.view.Result;
import org.dedeler.template.view.Result.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Logged(type = LogType.CONTROLLER)
@Controller
@RequestMapping(value = "/api/v1/users")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result show(@PathVariable("id") String id) {
		User user = userService.findById(User.class, Long.parseLong(id));
		
		return new Builder(true).resultObject(user).build();
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Result list() {
		Collection<User> users = userService.findAll(User.class);
		
		return new Builder(true).resultObject(users).build();
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(@RequestBody User user) {
		userService.update(user);
		return new Builder(true).resultObject(user).build();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Result create(@RequestBody User user) {
		long id = userService.save(user);
		user.setOid(id);
		return new Builder(true).resultObject(user).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result delete(@PathVariable("id") String id) {
		userService.deleteById(User.class, Long.parseLong(id));
		return new Builder(true).build();
	}
}
