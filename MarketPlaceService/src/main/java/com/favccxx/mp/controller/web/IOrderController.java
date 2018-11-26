package com.favccxx.mp.controller.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.favccxx.mp.entity.SmartVip;
import com.favccxx.mp.entity.req.OrderPayReq;
import com.favccxx.mp.entity.req.PlaceOrderProductReq;
import com.favccxx.mp.entity.req.PlaceOrderReq;
import com.favccxx.mp.entity.resp.OrderResponse;
import com.favccxx.mp.service.OrderService;
import com.favccxx.mp.service.ProductService;
import com.favccxx.mp.service.VipService;
import com.favccxx.mp.utils.OrderUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/iorder")
@Api(tags = "3. 前端订单管理接口")
public class IOrderController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;
	@Autowired
	VipService vipService;

	@RequiresUser
	@GetMapping("/list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = OrderResponse.class) })
	@ApiOperation(httpMethod = "GET", value = "分页查询订单信息")
	public RestResult<Page<OrderResponse>> list(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "sortField", defaultValue = "id") String sortField,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction,
			@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
			@RequestParam(value = "pageSize", defaultValue = SysConstants.PAGE_SIZE) int pageSize) {
		
		Page<OrderResponse> pageData;
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		Page<SmartOrder> page = orderService.pageIOrder(username, status, pageable);
		
		List<SmartOrder> orderList = page.getContent();
		List<OrderResponse> list = new ArrayList<OrderResponse>();
		
		if(orderList != null && orderList.size()>0) {
			for(SmartOrder orderItem : orderList) {
				OrderResponse response = orderService.findOrderDetail(orderItem.getId());
				SmartVip vip = vipService.findById(orderItem.getVipId());
				if(vip != null) {
					response.setVipName(vip.getVipName());
				}
				list.add(response);
			}
		}
		
		pageData = new PageImpl<OrderResponse>(list);
		return RestResult.sucess(pageData);
	}
	
	@RequiresUser
	@GetMapping("/detail/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "GET", value = "根据产品Id查询产品信息")
	public RestResult<OrderResponse> detail(@PathVariable (value = "id") long id) {
		OrderResponse order = orderService.findOrderDetail(id);
		return new RestResult<OrderResponse>().sucess(order, "操作成功");
	}
	
	@RequiresUser
	@PostMapping("/placeOrder")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "POST", value = "创建订单信息")
	public RestResult placeOrder(
			@RequestHeader(value=SysConstants.HEADER_TOKEN_KEY) String token,
			@RequestBody PlaceOrderReq orderReq) {
		logger.info("--------------------Token---------------------->" + token);
		String username = orderReq.getUsername();
		if(StringUtils.isBlank(username)) {
			return RestResult.invalidParams();
		}
		
		List<PlaceOrderProductReq> productList = orderReq.getProductList();
		if(productList==null || productList.size()==0) {
			return RestResult.invalidParams();
		}
		
		BigDecimal originalPrice = BigDecimal.ZERO;
		BigDecimal orderPrice = BigDecimal.ZERO;
		List<SmartOrderProduct> opList = new ArrayList<SmartOrderProduct>();
		for(PlaceOrderProductReq orderProduct : productList) {
			SmartProduct product = productService.findByProductNo(orderProduct.getProductNo());
			if(product==null) {
				logger.error("创建订单失败，无效的产品编码：" + orderProduct.getProductNo());
				return RestResult.invalidParams();
			}
			
			if(orderProduct.getQuantity()>0) {
				originalPrice = originalPrice.add(product.getOriginalPrice().multiply(new BigDecimal(orderProduct.getQuantity())));
				orderPrice = orderPrice.add(product.getSalePrice().multiply(new BigDecimal(orderProduct.getQuantity())));
			
				SmartOrderProduct op = new SmartOrderProduct();
				BeanUtils.copyProperties(product, op);
				op.setProductId(product.getId());
				op.setPrice(product.getSalePrice());
				op.setProductNum(orderProduct.getQuantity());
				
				opList.add(op);
			}
		}
		
		if(BigDecimal.ZERO.equals(originalPrice)) {
			logger.error("创建订单失败，未选择产品或所有产品数量均为0");
			return RestResult.invalidParams();
		}
		
		
		SmartOrder order = new SmartOrder();
		order.setOrderNo(OrderUtil.getOrderNo());
		order.setCreateTime(new Date());
		order.setOrderDate(new Date());
		order.setOrderPrice(orderPrice);
		order.setOriginalPrice(originalPrice);
		order.setStatus(OrderStatus.CREATED);
		order.setUpdateTime(new Date());
		order.setUserName(username);
		
		if(StringUtils.isNoneBlank(orderReq.getVipId())) {
			order.setVipId(Long.valueOf(orderReq.getVipId()));
		}
		
		orderService.placeOrder(order, opList);
		
		return RestResult.sucess(order);
		
	}
	
	
	@RequiresUser
	@PostMapping("/pay")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "POST", value = "订单支付")
	public RestResult<SmartOrder> payOrder(@RequestBody OrderPayReq orderPayReq) {
		if(StringUtils.isBlank(orderPayReq.getOrderNo()) || StringUtils.isBlank(orderPayReq.getPayType())) {
			return RestResult.invalidParams();
		}
		
		SmartOrder order = orderService.payOrder(orderPayReq.getOrderNo(), orderPayReq.getPayType());
		return RestResult.sucess(order);

	}
	
	@RequiresUser
	@PostMapping("/cancel/{orderNo}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "POST", value = "取消订单")
	public RestResult<String> cancel(@PathVariable String orderNo) {
		SmartOrder order = orderService.findByOrderNo(orderNo);
		if(order != null) {
			orderService.cancelOrder(order.getId());
		}
		return RestResult.sucess();		
	}
	
	@RequiresUser
	@DeleteMapping("/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartOrder.class) })
    @ApiOperation(httpMethod = "DELETE", value = "删除订单")
	public RestResult<String> delete(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return RestResult.sucess();		
	}
}
