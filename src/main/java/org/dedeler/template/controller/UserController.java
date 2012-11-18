package org.dedeler.template.controller;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.exception.ErrorCode;
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


/**
 * Handles user-related operations, such as user,role,privilege creation and assignment.
 * @author yasa
 *
 */
@Logged(type = LogType.CONTROLLER)
@Controller
@RequestMapping(value="/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public Result createUser(@RequestBody User user){
		User createdUser = userService.create(user);
		return new Builder(true).resultObject(createdUser).build();
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public Result updateUser(@RequestBody User user){
		Boolean success = userService.update(user);
		Builder b = new Builder(success);
		if(success){
			User updatedUser = userService.findById(User.class, user.getOid());
			updatedUser.setPassword(null); // FIXME: fugly
			b.resultObject(updatedUser);
		}else{
			b.errorCode(ErrorCode.UNKNOWN_ERROR); //FIXME: architectural decisions?
		}
		return b.build();
	}
	@RequestMapping(value="/deactivate", method=RequestMethod.POST)
	@ResponseBody
	public Result deleteUser(@RequestBody User user){
		userService.delete(user);
		return new Builder(true).build();
	}
	
	@RequestMapping(value="/get/{oid}", method=RequestMethod.GET)
	@ResponseBody
	public Result getUser(@PathVariable("oid") Long oid){
		User user = userService.findById(User.class, oid);
		return new Builder(true).resultObject(user).build();
	}
	
}
