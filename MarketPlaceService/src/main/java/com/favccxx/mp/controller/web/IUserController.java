package com.favccxx.mp.controller.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.entity.LoginSession;
import com.favccxx.mp.entity.SmartUser;
import com.favccxx.mp.entity.resp.UserResponse;
import com.favccxx.mp.service.UserService;
import com.favccxx.mp.utils.JWTUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/user")
@Api(tags="2.2. 用户管理接口" )
public class IUserController {
	
	@Autowired
	UserService userService;

	@RequiresUser
	@GetMapping("/info")
	@ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "操作成功", response = LoginSession.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户登录令牌查询用户信息")//swagger 当前接口注解
	public RestResult<UserResponse> info(@RequestParam(value = "token") String token)  {		
		String userName = JWTUtil.getUsername(token);
		if(StringUtils.isNoneBlank(userName)) {
			SmartUser user = userService.findByUserName(userName);
			if(user != null) {
				UserResponse userResponse = new UserResponse();
				try {
					PropertyUtils.copyProperties(userResponse, user);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				
				List<String> roles =  userService.findRoleCodesByUserId(user.getId());
				userResponse.setRoles(roles.toString());
				
				return RestResult.sucess(userResponse);
			}
		}

		return RestResult.invalidParams();
	}
	
}
