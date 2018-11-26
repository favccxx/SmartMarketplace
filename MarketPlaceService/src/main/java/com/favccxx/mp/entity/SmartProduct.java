package com.favccxx.mp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
public class SmartProduct implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 产品类别Id
	 */
	private long categoryId;
	
	/**
	 * 俱乐部Id
	 */
	private long clubId;
	
	/**
	 * 产品编码
	 */
	private String productNo;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 产品类型
	 */	
	private String productType;
	
	/**
	 * 产品默认展示图
	 */
	private String productImg;
	
	/**
	 * 产品等级
	 */
	private int star;
	
	/**
	 * 产品状态
	 */	
	private int status;
	
	/**
	 * 是否为套餐
	 */
	private int isPackage;
	
	/**
	 * 产品原始价格/厂家建议价格
	 */
	private BigDecimal originalPrice;	
	
	
	/**
	 * 产品销售单价
	 */
	private BigDecimal salePrice;
	
	/**
	 * 产品服务时间（分钟）
	 */
	private int workTime;
	
	/**
	 * 产品详情
	 */
	@Lob @Basic(fetch = FetchType.LAZY) 
	@Column(columnDefinition = "text") 
	private String detail;
	
	/**
	 * 产品上线日期
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date onlineDate;
	
	/**
	 * 产品下线日期
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date offlineDate;
	
	/**
	 * 创建时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	private String createUserName;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	private String updateUserName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getClubId() {
		return clubId;
	}

	public void setClubId(long clubId) {
		this.clubId = clubId;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsPackage() {
		return isPackage;
	}

	public void setIsPackage(int isPackage) {
		this.isPackage = isPackage;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public int getWorkTime() {
		return workTime;
	}

	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
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

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}


	
	
}
