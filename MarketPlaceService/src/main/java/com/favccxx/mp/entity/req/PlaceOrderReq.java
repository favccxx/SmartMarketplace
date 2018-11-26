package com.favccxx.mp.entity.req;

import java.util.List;

public class PlaceOrderReq {

	/**
	 * 会员Id
	 */
	private String vipId;

	/**
	 * 员工用户名
	 */
	private String username;

	/**
	 * 产品列表
	 */
	private List<PlaceOrderProductReq> productList;

	public List<PlaceOrderProductReq> getProductList() {
		return productList;
	}

	public void setProductList(List<PlaceOrderProductReq> productList) {
		this.productList = productList;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
