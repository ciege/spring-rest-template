package org.dedeler.template.controller;

import org.dedeler.template.annotation.Logged;
import org.dedeler.template.exception.ErrorCode;
import org.dedeler.template.model.Role;
import org.dedeler.template.service.RoleService;
import org.dedeler.template.service.LoggingService.LogType;
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
@RequestMapping(value="/role")
public class RoleController extends AbstractController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/{oid}", method=RequestMethod.GET)
	@ResponseBody
	private Result getRole(@PathVariable("oid") Long oid){
		Role role = roleService.getById(oid);
		return new Builder(true).resultObject(role).build();
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseBody
	private Result createRole(@RequestBody Role role){

		Role newRole = roleService.create(role);
		return new Builder(true).resultObject(newRole).build();

	}
	
	@RequestMapping(value="/{oid}", method=RequestMethod.PUT)
	@ResponseBody
	public Result updateRole(@PathVariable Long oid, @RequestBody Role role){
		role.setOid(oid);
		boolean success = roleService.updateFields(role, new String[]{"name"}, Role.class);
		Builder b = new Builder(success);
		if(success){
			Role newRole = roleService.getById(oid);
			b.resultObject(newRole);
		}else{
			b.errorCode(ErrorCode.UNKNOWN_ERROR); //FIXME: architectural decisions?
		}
		return b.build();
	}
	
	@RequestMapping(value="/{oid}", method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteRole(@PathVariable Long oid){
		boolean success = roleService.delete(oid, Role.class);
		Builder b = new Builder(success);
		if(!success){
			b.errorCode(ErrorCode.UNKNOWN_ERROR); //FIXME: architectural decisions?
		}
		return b.build();
	}
}
