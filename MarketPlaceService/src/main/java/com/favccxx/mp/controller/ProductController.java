package com.favccxx.mp.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.favccxx.mp.constants.ProductStatus;
import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartProduct;
import com.favccxx.mp.entity.req.ProductReq;
import com.favccxx.mp.service.ProductService;
import com.favccxx.mp.utils.DateUtil;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/product")
@Api(tags="7. 产品管理接口" )
public class ProductController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询产品信息")
	public RestResult<Page<SmartProduct>> list(			
			@RequestParam(value = "productName", required=false) String productName,
			@RequestParam(value = "categoryId", required=false) String categoryId,
			@RequestParam(value = "status", required=false, defaultValue="200") int status,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<SmartProduct> pageData = null;
		SmartProduct product = new SmartProduct();
		product.setStatus(status);
		
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
	
	
	@GetMapping("/empty")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
    @ApiOperation(httpMethod = "GET", value = "获取一个空的产品信息，存储产品的草稿状态")
	public RestResult<SmartProduct> empty() {
		SmartProduct product = new SmartProduct();
		product.setProductNo(UUID.randomUUID().toString());
		product.setStatus(ProductStatus.CREATED.value());
		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());		
		productService.save(product);
		
		return RestResult.sucess(product);
	}

	
	@GetMapping("/detail/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
    @ApiOperation(httpMethod = "GET", value = "根据产品Id查询产品信息")
	public RestResult<SmartProduct> detail(@PathVariable (value = "id") long id) {
		SmartProduct product = productService.findById(id);
		return new RestResult<SmartProduct>().sucess(product, "操作成功");
	}
	
	
	@GetMapping("/findByProductNo/{productNo}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
    @ApiOperation(httpMethod = "GET", value = "根据产品编码查询产品信息")
	public RestResult<SmartProduct> findByProductNo(@PathVariable (value = "productNo") String productNo) {
		SmartProduct product = productService.findByProductNo(productNo);
		return new RestResult<SmartProduct>().sucess(product, "操作成功");
	}
	
	
	@PutMapping("/create")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
    @ApiOperation(httpMethod = "PUT", value = "创建产品信息")
	public RestResult<SmartProduct> create( 
			@RequestParam(value="productName", required=false) String productName,	//产品名称
			@RequestParam(value="status", required=false) int status,		//产品状态
			@RequestParam(value="originalPrice", required=false) BigDecimal originalPrice,	//原始价格
			@RequestParam(value="salePrice", required=false) BigDecimal salePrice,	//销售价格
			@RequestParam(value="onlineDate", required=false) String onlineDate,		//上线日期
			@RequestParam(value="offlineDate", required=false) String offlineDate,	//下架日期
			@RequestParam(value="detail", required=false) String detail
			) {				
		SmartProduct product = new SmartProduct();
		product.setProductNo(UUID.randomUUID().toString());
		product.setProductName(productName);
		if(status!=0) {
			product.setStatus(status);
		}else {
			product.setStatus(ProductStatus.CREATED.value());
		}
		
		if(!BigDecimal.ZERO.equals(originalPrice)) {
			product.setOriginalPrice(originalPrice);
		}
		
		if(!BigDecimal.ZERO.equals(salePrice)) {
			product.setSalePrice(salePrice);
		}
		
		if(StringUtils.isNoneBlank(onlineDate)) {
			Date online = DateUtil.parseStartDate(onlineDate);
			product.setOnlineDate(online);
		}
		
		if(StringUtils.isNoneBlank(offlineDate)) {
			Date offline = DateUtil.parseStartDate(offlineDate);
			product.setOfflineDate(offline);
		}
		
		if(StringUtils.isNoneBlank(detail)) {
			product.setDetail(detail);
		}
		
		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());
			
		productService.save(product);
		
				
		return RestResult.sucess(product);						
	}
	
	
	@PostMapping("/update")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
    @ApiOperation(httpMethod = "POST", value = "更新产品信息")
	public RestResult<SmartProduct> update( 
			@RequestBody ProductReq productReq
//			@RequestParam(value="id", required=false) String id,
//			@RequestParam(value="productName", required=false) String productName,	//产品名称
//			@RequestParam(value="status", required=false) int status,		//产品状态
////			@RequestParam(value="isPackage", required=true) int isPackage,	//是否为套餐
//			@RequestParam(value="originalPrice", required=false) BigDecimal originalPrice,	//原始价格
//			@RequestParam(value="salePrice", required=false) BigDecimal salePrice,	//销售价格
//			@RequestParam(value="onlineDate", required=false) String onlineDate,		//上线日期
//			@RequestParam(value="offlineDate", required=false) String offlineDate,	//下架日期
//			@RequestParam(value="detail", required=false) String detail
			) {
		logger.info("productInfo:" + productReq.getId() + "," + productReq.getOnlineDate() + "..." + productReq.getStar());
		
		if(productReq.getId() != 0) {
			SmartProduct product = productService.findById(productReq.getId());
			if(product != null) {
				
				if(productReq.getCategoryId() != 0) {
					product.setCategoryId(productReq.getCategoryId());
				}
				
				if(StringUtils.isBlank(product.getProductNo())) {
					product.setProductNo(UUID.randomUUID().toString());
				}
				
				if(StringUtils.isNotBlank(productReq.getProductName())) {
					product.setProductName(productReq.getProductName());
				}
				if(!BigDecimal.ZERO.equals(productReq.getOriginalPrice())) {
					product.setOriginalPrice(productReq.getOriginalPrice());
				}
				if(!BigDecimal.ZERO.equals(productReq.getSalePrice())) {
					product.setSalePrice(productReq.getSalePrice());
				}
				
				if(StringUtils.isNotBlank(productReq.getOnlineDate())) {
					Date online = DateUtil.parseStartDate(productReq.getOnlineDate());
					product.setOnlineDate(online);				
				}
				
				if(StringUtils.isNotBlank(productReq.getOfflineDate())) {
					Date offline = DateUtil.parseStartDate(productReq.getOfflineDate());
					product.setOfflineDate(offline);
				}
				
				if(StringUtils.isNotBlank(productReq.getDetail())) {
					product.setDetail(productReq.getDetail());
				}
				
				if(productReq.getStar() != 0) {
					product.setStar(productReq.getStar());
				}
				
				product.setStatus(ProductStatus.CREATED.value());
				if("published".equals(productReq.getStatus())) {
					product.setStatus(ProductStatus.PUBLISHED.value());
				}else if("draft".equals(productReq.getStatus())){
					product.setStatus(ProductStatus.DRAFT.value());
				}
				
				product.setCreateTime(new Date());
				product.setUpdateTime(new Date());
				productService.save(product);
				
				return RestResult.sucess(product);		
			}
		}
				
		return RestResult.sucess();						
	}
	
	
	@DeleteMapping("/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartProduct.class) })
    @ApiOperation(httpMethod = "DELETE", value = "根据产品Id删除产品信息")
	public RestResult<String> delete(@PathVariable (value = "id") long id){
		productService.delete(id);
		return RestResult.sucess("删除成功!");
	}
	
	
	
	
	
}
