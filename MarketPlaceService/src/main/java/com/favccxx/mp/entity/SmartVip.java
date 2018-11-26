package com.favccxx.mp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 俱乐部会员
 * @author favccxx
 *
 */
@Entity
public class SmartVip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Column(columnDefinition = "enum('GOLD','SILVER', 'BRONZE ')")
	private String vipStar;
	
	/**
	 * 会员费用
	 */
	private BigDecimal vipFee;
	
	/**
	 * 会员简介
	 */
	private String detail;
	
	/**
	 * 入会时间
	 */
	private Date createTime;
	
	/**
	 * 创建人
	 */
	private String createUserName;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 更新用户
	 */
	private String updateUserName;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
	
	
}
