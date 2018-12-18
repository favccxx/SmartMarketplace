package com.favccxx.mp.controller.admin;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartShop;
import com.favccxx.mp.service.ShopService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/mgr/shop")
@Api(tags="1.1 店铺管理接口" )
public class ShopController {

	@Autowired
	ShopService shopService;
	
	@GetMapping("/detail/{shopCode}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartShop.class) })
	@ApiOperation(httpMethod = "GET", value = "根据店铺代码查询店铺信息")
	public RestResult<SmartShop> detail(@PathVariable(value = "shopCode") String shopCode) {
		SmartShop shop = shopService.findByShopCode(shopCode);
		return RestResult.sucess(shop);
	}


	@GetMapping("/list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartShop.class) })
	@ApiOperation(httpMethod = "GET", value = "分页查询店铺信息")
	public RestResult<Page<SmartShop>> list(
			@RequestParam(value = "shopName", required = false) String shopName,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "sort", defaultValue = "+id") String sort,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = SysConstants.PAGE_SIZE) int limit) {
		Page<SmartShop> pageData = null;
		SmartShop shop = new SmartShop();
		if (StringUtils.isNotBlank(shopName)) {
			shop.setShopName(shopName);
		}
		if (StringUtils.isNoneBlank(status)) {
			shop.setStatus(status);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = shopService.pageQuery(shop, pageable);

		return RestResult.sucess(pageData);
	}
	
	@PutMapping("/create")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartShop.class) })
	@ApiOperation(httpMethod = "PUT", value = "创建店铺信息")
	public RestResult<SmartShop> create(@RequestBody SmartShop shop) {
		if (StringUtils.isBlank(shop.getShopName()) || StringUtils.isBlank(shop.getShopType())) {
			return RestResult.lackParams("shopName/shopType不允许为空");
		}

		
		shop.setCreateTime(new Date());
		shop.setUpdateTime(new Date());

		shopService.save(shop);
		return RestResult.sucess(shop);
	}

	@PostMapping("/update")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartShop.class) })
	@ApiOperation(httpMethod = "POST", value = "更新产品类别信息")
	public RestResult<SmartShop> update(@RequestBody SmartShop shop) {
		SmartShop existShop = shopService.findById(shop.getId());
		if (existShop == null) {
			return RestResult.invalidParams();
		}

		if (StringUtils.isNoneBlank(shop.getShopName())) {
			existShop.setShopName(shop.getShopName());
		}

		if (StringUtils.isNoneBlank(shop.getDescription())) {
			existShop.setDescription(shop.getDescription());
		}

		
		existShop.setUpdateTime(new Date());

		shopService.save(existShop);
		return RestResult.sucess(existShop);
	}
	
	
}

