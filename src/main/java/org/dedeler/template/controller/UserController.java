package org.dedeler.template.controller;

import java.util.Collection;
import java.util.Map;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.annotation.PartialUpdateTarget;
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
	
	@RequestMapping(value="/{oid}", method=RequestMethod.GET)
	@ResponseBody
	public Result getUser(@PathVariable("oid") Long oid){
		User user = userService.findById(User.class, oid);
		return new Builder(true).resultObject(user).build();
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseBody
	public Result createUser(@RequestBody User user){
		User createdUser = userService.create(user);
		return new Builder(true).resultObject(createdUser).build();
	}
	
	@RequestMapping(value="/{oid}", method=RequestMethod.PUT)
	@ResponseBody
	public Result updateUser(@PathVariable("oid") Long oid, @RequestBody Map<String, Object> userMap, @PartialUpdateTarget User user){
		//TODO: copy fields from userMap to user
//		Boolean success = userService.update(user);
		boolean success = false;
		Builder b = new Builder(success);
		if(success){
			User updatedUser = userService.findById(User.class, user.getOid());
			b.resultObject(updatedUser);
		}else{
			b.errorCode(ErrorCode.UNKNOWN_ERROR); //FIXME: architectural decisions?
		}
		return b.build();
	}
	
	
	@RequestMapping(value="/{oid}", method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteUser(@PathVariable("oid") Long oid){
		boolean success = userService.delete(oid);
		return new Builder(success).build(); //FIXME: current architecture does not allow failure reason to be bubbled up here.
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Result listUsers(){
		
		Collection<User> userList = userService.listAll();
		return new Builder(true).resultObject(userList).build();
		
	}
	
	
}
