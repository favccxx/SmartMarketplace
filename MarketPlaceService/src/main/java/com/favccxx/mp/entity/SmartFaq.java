package com.favccxx.mp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 问答对象
 * @author favccxx
 *
 */
@Entity
public class SmartFaq  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 产品Id
	 */
	private long productId;
	
	/**
	 * 问题
	 */
	private String content;
	
	/**
	 * 类型 ANSWER/QUESTION
	 */
	private String type;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 是否管理员答复 0：否 1：是
	 */
	private int isAdmin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	
	
	

}
