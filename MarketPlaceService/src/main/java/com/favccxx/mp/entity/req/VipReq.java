package com.favccxx.mp.entity.req;

import java.math.BigDecimal;

public class VipReq {

	private long id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 会员姓名
	 */
	private String vipName;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 会员状态
	 */
	private String status;

	/**
	 * 会员邮箱
	 */
	private String vipMail;

	/**
	 * 会员电话
	 */
	private String vipTel;

	/**
	 * 会员等级
	 */
	private String vipStar;

	/**
	 * 会员费用
	 */
	private BigDecimal vipFee;

	/**
	 * 会员简介
	 */
	private String detail;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVipMail() {
		return vipMail;
	}

	public void setVipMail(String vipMail) {
		this.vipMail = vipMail;
	}

	public String getVipTel() {
		return vipTel;
	}

	public void setVipTel(String vipTel) {
		this.vipTel = vipTel;
	}

	public String getVipStar() {
		return vipStar;
	}

	public void setVipStar(String vipStar) {
		this.vipStar = vipStar;
	}

	public BigDecimal getVipFee() {
		return vipFee;
	}

	public void setVipFee(BigDecimal vipFee) {
		this.vipFee = vipFee;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}
