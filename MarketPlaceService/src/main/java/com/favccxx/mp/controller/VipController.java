package com.favccxx.mp.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.constants.UserConstants;
import com.favccxx.mp.entity.LoginSession;
import com.favccxx.mp.entity.SmartVip;
import com.favccxx.mp.entity.req.VipReq;
import com.favccxx.mp.service.VipService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/vip")
@Api(tags="5. 会员管理接口" )
public class VipController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	VipService vipService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = LoginSession.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询会员信息")
	public RestResult<Page<SmartVip>> list(			
			@RequestParam(value = "vipName", required=false) String vipName,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		
		Page<SmartVip> pageData = null;
		SmartVip vip = new SmartVip();
		
		if(StringUtils.isNotBlank(vipName)) {
			vip.setVipName(vipName);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = vipService.pageQuery(vip, pageable);
		
		return RestResult.sucess(pageData);	
	}
	
	
	@PutMapping("/create")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = LoginSession.class) })
    @ApiOperation(httpMethod = "PUT", value = "创建会员信息")
	public RestResult<SmartVip> create(@RequestBody VipReq vipReq) {
		
		if(StringUtils.isBlank(vipReq.getVipName()) || StringUtils.isBlank(vipReq.getVipTel())) {
			return RestResult.lackParams("会员名和手机号不允许为空");
		}
		
		SmartVip vip = new SmartVip();
		BeanUtils.copyProperties(vipReq, vip);
		if(StringUtils.isBlank(vipReq.getStatus())) {
			vip.setStatus(UserConstants.VIP_STATUS_ENABLE);
		}
		if(StringUtils.isBlank(vipReq.getVipStar())) {
			vip.setVipStar("BRONZE");
		}
		if(vip.getVipFee() == null || BigDecimal.ZERO.equals(vipReq.getVipFee())) {
			vip.setVipFee(new BigDecimal(0.00));
		}
		vip.setCreateTime(new Date());
		vip.setUpdateTime(new Date());
		vip.setCreateUserName("abc");
		vip.setUpdateUserName("abc");
		vipService.save(vip);
		
		return RestResult.sucess(vip);
	}
	
	
	@PostMapping("/update")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartVip.class) })
    @ApiOperation(httpMethod = "POST", value = "更新会员信息")
	public RestResult<SmartVip> update(@RequestBody VipReq vipReq) {
		logger.info("vipInfo:" + vipReq.getId());
		
		if(vipReq.getId() != 0) {
			SmartVip vip = vipService.findById(vipReq.getId());
			if(vip != null) {
				if(StringUtils.isNotBlank(vipReq.getVipName())) {
					vip.setVipName(vipReq.getVipName());
				}
				
				if(StringUtils.isNotBlank(vipReq.getVipMail())) {
					vip.setVipMail(vipReq.getVipMail());
				}
				
				if(StringUtils.isNotBlank(vipReq.getVipTel())) {
					vip.setVipTel(vipReq.getVipTel());
				}
				
				if(StringUtils.isNotBlank(vipReq.getIdCard())) {
					vip.setIdCard(vipReq.getIdCard());
				}
				
				if(StringUtils.isNotBlank(vipReq.getStatus())) {
					vip.setStatus(vipReq.getStatus());
				}
				
				if(StringUtils.isNotBlank(vipReq.getIdCard())) {
					vip.setIdCard(vipReq.getIdCard());
				}
				
				if(!BigDecimal.ZERO.equals(vipReq.getVipFee())) {
					vip.setVipFee(vipReq.getVipFee());
				}
				
				
				vip.setUpdateTime(new Date());
				vipService.save(vip);
				
				return RestResult.sucess(vip);		
			}
		}
				
		return RestResult.sucess();						
	}

}
