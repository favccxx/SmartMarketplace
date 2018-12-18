package com.favccxx.mp.controller.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.entity.LoginSession;
import com.favccxx.mp.entity.SmartUser;
import com.favccxx.mp.entity.req.LoginUserReq;
import com.favccxx.mp.entity.resp.LoginUser;
import com.favccxx.mp.service.SessionUserService;
import com.favccxx.mp.service.UserService;
import com.favccxx.mp.utils.JWTUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/mgr/login")
@Api(value = "登录登出管理接口", tags = "1.0 登录接口")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserService userService;
	@Autowired
	SessionUserService sessionUserService;

	@PostMapping("/login")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "用户不存在"),
		@ApiResponse(code = 200, message = "操作成功", response = LoginSession.class) })
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "user", value = "登录用户实体", required = true, dataType = "LoginUserReq") })
	@ApiOperation(httpMethod = "POST", value = "用户登录")
	public RestResult<LoginUser> login(@RequestBody LoginUserReq user) {
		logger.info("Ready--> login");
		LoginUser loginUser = new LoginUser();
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return new RestResult<LoginUser>(101, "用户名密码不允许为空！");
		}

		SmartUser smartUser = userService.findByUserName(user.getUsername());
		if (smartUser == null) {
			return RestResult.error(400, "用户不存在");
		}

		if (user.getPassword().equals(smartUser.getPassword())) {			
			String token = JWTUtil.sign(user.getUsername(), user.getPassword());
			logger.info("------login token:-----" + token);
			try {
				BeanUtils.copyProperties(loginUser, smartUser);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			loginUser.setLoginDate(new Date());
			loginUser.setToken(token);
			return RestResult.sucess(loginUser);
		} else {
			return RestResult.error(400, "用户名或密码错误");
		}
	}

//	@POST
//	@Path("/login")
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "用户登录. ", response = SmartClub.class)
//	@ApiResponse(code = 200, message = "操作成功")	
//	public Response login(
//    		@FormParam("userName")  String userName,
//    		@FormParam("password")  String password) {
//		
//		if (StringUtils.isBlank(userName)) {
//			return Response.status(Response.Status.BAD_REQUEST).build();
//		}
//		
//		
//		SmartUser smartUser = userService.findByUserName(userName);
//		if(smartUser == null || !smartUser.getPassword().equals(password)) {
//			return Response.status(Status.NO_CONTENT).build();
//		}
//		
//		
//		LoginSession loginUser = sessionUserService.findOrCreateByUserName(userName);
//		
//	
//
//				
//		return Response.ok(loginUser).build();
//	}

}
