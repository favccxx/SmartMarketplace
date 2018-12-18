package com.favccxx.mp.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartRole;
import com.favccxx.mp.service.RoleService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/mgr/role")
@Api(tags="1.9 角色管理接口" )
public class RoleController {
	
	@Autowired
	RoleService roleService;

	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartRole.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询用户信息")
	public RestResult<Page<SmartRole>> list(			
			@RequestParam(value = "roleCode", required=false) String roleCode,
			@RequestParam(value = "roleName", required=false) String roleName,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		
		Page<SmartRole> pageData = null;
		SmartRole role = new SmartRole();
		
		if(StringUtils.isNotBlank(roleCode)) {
			role.setRoleCode(roleCode);
		}
		
		if(StringUtils.isNoneBlank(roleName)) {
			role.setRoleName(roleName);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = roleService.pageQuery(role, pageable);
		
		return RestResult.sucess(pageData);	
	}
}
