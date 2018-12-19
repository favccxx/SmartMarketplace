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
	 * 评论用户Id
	 */
	private long userId;
	
	/**
	 * 评论人
	 */
	private String reviewUsername;
	
	/**
	 * 评论时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date reviewTime;
	
	/**
	 * 回复人
	 */
	private String replyUsername;
	
	/**
	 * 回复时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date replyTime;
	
	/**
	 * 回复内容
	 */
	private String replyContent;

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getReviewUsername() {
		return reviewUsername;
	}

	public void setReviewUsername(String reviewUsername) {
		this.reviewUsername = reviewUsername;
	}

	public String getReplyUsername() {
		return replyUsername;
	}

	public void setReplyUsername(String replyUsername) {
		this.replyUsername = replyUsername;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	
	
}
