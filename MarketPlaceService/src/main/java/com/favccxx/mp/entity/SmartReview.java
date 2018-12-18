package com.favccxx.mp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 评论对象
 * @author favccxx
 *
 */
@Entity
public class SmartReview implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 产品Id
	 */
	private long productId;
	
	
	/**
	 * 评价内容
	 */
	private String content;
	
	/**
	 * 评论分数
	 */
	private int score;
	
	/**
	 * 回复某个评论Id
	 */
	private long replyId;
	
	/**
	 * 评论用户Id
	 */
	private long userId;
	
	/**
	 * 评论人
	 */
	private String username;
	
	/**
	 * 评论时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date reviewTime;

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getReplyId() {
		return replyId;
	}

	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	
}
