package com.favccxx.mp.entity.req;

/**
 * 创建产品类别请求
 * @author favccxx
 *
 */
public class CategoryPutReq {

	/**
	 * 类别代码
	 */
	private String categoryCode;

	/**
	 * 类别名称
	 */
	private String categoryName;

	/**
	 * 类别状态 1： 正常 0： 已删除
	 */
	private int status;

	/**
	 * 父类别Id
	 */
	private long parentId;

	/**
	 * 类别详情
	 */
	private String detail;

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
