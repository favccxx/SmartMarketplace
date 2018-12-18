package com.favccxx.mp.controller.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
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

import com.favccxx.mp.constants.ProductStatus;
import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartProduct;
import com.favccxx.mp.service.ProductService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/sale")
@Api(tags="2.3 销售产品管理接口" )
public class SaleController {
	
Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProductService productService;
	
	@RequiresUser
	@GetMapping("/list")
	@ApiOperation(httpMethod = "GET", value = "分页查询产品信息")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "productName", value = "产品名称", required = false, dataType = "String"),
		@ApiImplicitParam(name = "categoryId", value = "产品类别Id", required = false, dataType = "String"),
		@ApiImplicitParam(name = "sort", value = "排序字段，默认Id升序", required = false, defaultValue="+id", dataType = "String"),
		@ApiImplicitParam(name = "page", value = "页索引", required = false, defaultValue="1", dataType = "Integer"),
		@ApiImplicitParam(name = "limit", value = "每页显示条目", required = false, defaultValue="10", dataType = "Integer")})
	public RestResult<Page<SmartProduct>> list(			
			@RequestParam(value = "productName", required=false) String productName,
			@RequestParam(value = "categoryId", required=false) String categoryId,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<SmartProduct> pageData = null;
		SmartProduct product = new SmartProduct();
		product.setStatus(ProductStatus.PUBLISHED.value());
		
		if(StringUtils.isNotBlank(productName)) {
			product.setProductName(productName);
		}
		
		if(StringUtils.isNoneBlank(categoryId)) {
			product.setCategoryId(Long.valueOf(categoryId));
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = productService.pageQuery(product, pageable);
		
		return RestResult.sucess(pageData);	
	}

}
