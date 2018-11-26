package com.favccxx.mp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.constants.SysConstants.OrderStatus;
import com.favccxx.mp.entity.SmartOrder;
import com.favccxx.mp.entity.SmartOrderProduct;
import com.favccxx.mp.entity.SmartProduct;
import com.favccxx.mp.entity.req.OrderPayReq;
import com.favccxx.mp.entity.req.PlaceOrderProductReq;
import com.favccxx.mp.entity.req.PlaceOrderReq;
import com.favccxx.mp.entity.resp.OrderResponse;
import com.favccxx.mp.service.OrderService;
import com.favccxx.mp.service.ProductService;
import com.favccxx.mp.utils.OrderUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/order")
@Api(tags="8. 订单管理接口" )
public class OrderController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询订单信息")
	public RestResult<Page<SmartOrder>> list(			
			@RequestParam(value = "orderNo", required=false) String orderNo,
			@RequestParam(value = "status", required=false) String status,
			@RequestParam(value="sortField", defaultValue="id") String sortField,
			@RequestParam(value = "direction", defaultValue="DESC") String direction,
			@RequestParam(value = "pageIndex", defaultValue="1")  int pageIndex,
			@RequestParam(value = "pageSize", defaultValue=SysConstants.PAGE_SIZE)  int pageSize) {
		Page<SmartOrder> pageData = null;
		SmartOrder order = new SmartOrder();
		if(StringUtils.isNotBlank(status)) {
			order.setStatus(OrderStatus.CREATED);
		}
		
		if(StringUtils.isNotBlank(orderNo)) {
			order.setOrderNo(orderNo);
		}
		
		Direction directionField = Direction.DESC;
		if (direction.toUpperCase().equals("ASC")) {
			directionField = Direction.ASC;
		}

		Sort sort = new Sort(directionField, sortField);
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
		pageData = orderService.pageQuery(order, pageable);
		
		return RestResult.sucess(pageData);	
	}

	
	@GetMapping("/detail/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "GET", value = "根据产品Id查询产品信息")
	public RestResult<OrderResponse> detail(@PathVariable (value = "id") long id) {
		OrderResponse order = orderService.findOrderDetail(id);
		return new RestResult<OrderResponse>().sucess(order, "操作成功");
	}
	
	
	@DeleteMapping("/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "DELETE", value = "删除订单")
	public RestResult<String> delete(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return RestResult.sucess();		
	}
	


}
