package com.favccxx.mp.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.constants.UserConstants;
import com.favccxx.mp.entity.LoginSession;
import com.favccxx.mp.entity.SmartUser;
import com.favccxx.mp.service.SessionUserService;
import com.favccxx.mp.service.UserService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/mgr/user")
@Api(value = "用户管理接口", tags="1.8 用户管理接口" )
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	SessionUserService sessionUserService;
	
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = LoginSession.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询用户信息")
	public RestResult<Page<SmartUser>> list(			
			@RequestParam(value = "username", required=false) String username,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<SmartUser> pageData = null;
		SmartUser user = new SmartUser();
		
		if(StringUtils.isNotBlank(username)) {
			user.setUsername(username);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = userService.pageQuery(user, pageable);
		
		return RestResult.sucess(pageData);	
	}
	
	
	
		
	@GetMapping("/detail/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 100, message = "用户不存在"),
            @ApiResponse(code = 200, message = "操作成功", response = SmartUser.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户Id查询用户信息")
	public RestResult<SmartUser> detail(@PathVariable (value = "id") long id) {
		SmartUser user = userService.findById(id);

		return new RestResult<SmartUser>().sucess(user, "操作成功");
	}
	
	
	@GetMapping("/findByUsername/{username}")
	@ApiResponses(value = {
            @ApiResponse(code = 100, message = "用户不存在"),
            @ApiResponse(code = 200, message = "操作成功", response = SmartUser.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户名查询用户信息")
	public RestResult<SmartUser> findByUsername(@PathVariable (value = "username") String username) {
		SmartUser user = userService.findByUserName(username);
//		if(user == null) {
//			return new RestResult<SmartUser>(100, "用户不存在", null);
//		}
		return new RestResult<SmartUser>().sucess(user, "操作成功");
	}
	
	
	@PostMapping("/create")
	public RestResult<SmartUser> create(@RequestBody SmartUser user) {
		
		
		return null;
		
	}
	
	@DeleteMapping("/{id}")
	@ApiResponses(value = {            
            @ApiResponse(code = 200, message = "操作成功", response = SmartUser.class) })
    @ApiOperation(httpMethod = "DELETE", value = "根据用户Id删除用户")
	public RestResult<String> delete(@PathVariable (value = "id") long id) {
		SmartUser user = userService.findById(id);
		if(user != null) {
			user.setStatus(UserConstants.STATUS_DELETED);
			userService.save(user);
		}
		return RestResult.sucess();
	}
	
	
}
