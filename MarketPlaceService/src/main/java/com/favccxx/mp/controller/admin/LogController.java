package com.favccxx.mp.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.favccxx.mp.entity.SmartLog;
import com.favccxx.mp.service.LogService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/log")
@Api(tags="10. 日志管理接口" )
public class LogController {

Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	LogService logService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartLog.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询产品信息")
	public RestResult<Page<SmartLog>> list(			
			@RequestParam(value = "uri", required=false) String uri,
			@RequestParam(value = "method", required=false) String method,
			@RequestParam(value = "statusCode", required=false, defaultValue="0") int statusCode,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<SmartLog> pageData = null;
		
		SmartLog log = new SmartLog();
		
		log.setStatusCode(statusCode);
		if(StringUtils.isNotBlank(uri)) {
			log.setUri(uri);
		}
		if(StringUtils.isNoneBlank(method)) {
			log.setMethod(method);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = logService.pageQuery(log, pageable);
		
		return RestResult.sucess(pageData);	
	}
	
}
