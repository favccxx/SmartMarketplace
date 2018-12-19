package com.favccxx.mp.controller.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.CategoryStatus;
import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartProductCategory;
import com.favccxx.mp.entity.req.CategoryPutReq;
import com.favccxx.mp.entity.req.CategoryUpdateReq;
import com.favccxx.mp.service.ProductCategoryService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/mgr/category")
@Api(tags = "1.2 产品类别管理接口")
public class ProductCategoryController {

	@Autowired
	ProductCategoryService categoryService;

	@GetMapping("/detail/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartProductCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "根据产品类别Id查询类别信息")
	public RestResult<SmartProductCategory> detail(@PathVariable(value = "id") long id) {
		SmartProductCategory category = categoryService.findById(id);
		return RestResult.sucess(category);
	}

	@RequiresRoles("admin")
	@GetMapping("/list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartProductCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "分页查询产品类别信息")
	public RestResult<Page<SmartProductCategory>> list(
			@RequestParam(value = "categoryCode", required = false) String categoryCode,
			@RequestParam(value = "categoryName", required = false) String categoryName,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "sort", defaultValue = "+id") String sort,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = SysConstants.PAGE_SIZE) int limit) {
		Page<SmartProductCategory> pageData = null;
		SmartProductCategory category = new SmartProductCategory();
		if (StringUtils.isNoneBlank(categoryCode)) {
			category.setCategoryCode(categoryCode);
		}
		if (StringUtils.isNotBlank(categoryName)) {
			category.setCategoryName(categoryName);
		}
		if (StringUtils.isNoneBlank(status)) {
			category.setStatus(Integer.parseInt(status));
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = categoryService.pageQuery(category, pageable);

		return RestResult.sucess(pageData);
	}
	
	@GetMapping("/listParent")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartProductCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "查询所有正常的父类别信息")
	public RestResult<List<SmartProductCategory>> listParent() {
		List<SmartProductCategory> list = categoryService.listParent();
		return RestResult.sucess(list);
	}

	@GetMapping("/listNomal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartProductCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "查询所有正常的类别信息")
	public RestResult<List<SmartProductCategory>> listNomal() {
		List<SmartProductCategory> list = categoryService.listNormal();
		return RestResult.sucess(list);
	}

	@PutMapping("/create")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartProductCategory.class) })
	@ApiOperation(httpMethod = "PUT", value = "创建产品类别信息")
	public RestResult<SmartProductCategory> create(@RequestBody CategoryPutReq categoryReq) {
		SmartProductCategory category = new SmartProductCategory();

		if (StringUtils.isBlank(categoryReq.getCategoryCode()) || StringUtils.isBlank(categoryReq.getCategoryName())) {
			return RestResult.lackParams("categoryCode/categoryName/detail不允许为空");
		}

		BeanUtils.copyProperties(categoryReq, category);
		category.setCreateTime(new Date());
		category.setUpdateTime(new Date());

		boolean checkStatus = false;
		for (CategoryStatus catStatus : CategoryStatus.values()) {
			if (catStatus.value() == category.getStatus()) {
				checkStatus = true;
				continue;
			}
		}
		if (!checkStatus) {
			category.setStatus(CategoryStatus.ENABLE.value());
		}

		categoryService.save(category);
		return RestResult.sucess(category);
	}

	@PostMapping("/update")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartProductCategory.class) })
	@ApiOperation(httpMethod = "POST", value = "更新产品类别信息")
	public RestResult<SmartProductCategory> update(@RequestBody CategoryUpdateReq categoryReq) {
		SmartProductCategory existCategory = categoryService.findById(categoryReq.getId());
		if (existCategory == null) {
			return RestResult.invalidParams();
		}

		if (StringUtils.isNoneBlank(categoryReq.getCategoryName())) {
			existCategory.setCategoryName(categoryReq.getCategoryName());
		}

		if (StringUtils.isNoneBlank(categoryReq.getDetail())) {
			existCategory.setDetail(categoryReq.getDetail());
		}

		for (CategoryStatus catStatus : CategoryStatus.values()) {
			if (catStatus.value() == categoryReq.getStatus()) {
				existCategory.setStatus(categoryReq.getStatus());
			}
		}
		existCategory.setUpdateTime(new Date());

		categoryService.save(existCategory);
		return RestResult.sucess(existCategory);
	}

	@PostMapping("/modifyStatus")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartProductCategory.class) })
	@ApiOperation(httpMethod = "POST", value = "更新产品类别状态")
	public RestResult<SmartProductCategory> modifyStatus(@RequestBody CategoryUpdateReq categoryReq) {
		SmartProductCategory existCategory = categoryService.findById(categoryReq.getId());
		if (existCategory == null) {
			return RestResult.invalidParams();
		}

		for (CategoryStatus catStatus : CategoryStatus.values()) {
			if (catStatus.value() == categoryReq.getStatus()) {
				existCategory.setStatus(categoryReq.getStatus());
			}
		}
		existCategory.setUpdateTime(new Date());

		categoryService.save(existCategory);
		return RestResult.sucess(existCategory);
	}

	@DeleteMapping("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = String.class) })
	@ApiOperation(httpMethod = "DELETE", value = "根据产品类别Id删除产品类别信息")
	public RestResult<String> delete(@PathVariable Long id) {
		categoryService.delete(id);
		return RestResult.sucess();
	}

}
