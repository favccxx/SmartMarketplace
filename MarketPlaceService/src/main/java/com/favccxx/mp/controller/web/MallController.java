package com.favccxx.mp.controller.web;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartMall;
import com.favccxx.mp.service.MallService;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/mall")
@Api(tags="1. 商城管理接口" )
public class MallController {

	@Autowired
	MallService mallService;		
	
	@RequiresUser
	@GetMapping("/detail")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartMall.class) })
    @ApiOperation(httpMethod = "GET", value = "查询商城信息")
	public RestResult<SmartMall> detail() {
		SmartMall mall = mallService.findByMallCode(SysConstants.DEFAULT_CLUB);		
		return new RestResult<SmartMall>().sucess(mall, "操作成功");
	}
	
}
