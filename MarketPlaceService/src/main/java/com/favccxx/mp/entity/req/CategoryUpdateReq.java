package com.favccxx.mp.entity.req;

/**
 * 类别更新请求
 * @author favccxx
 *
 */
public class CategoryUpdateReq {

	private long id;

	/**
	 * 类别名称
	 */
	private String categoryName;

	/**
	 * 类别状态
	 */
	private int status;		

	/**
	 * 类别详情
	 */
	private String detail;

	/**
	 * 更新人
	 */
	private String updateUserName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	
	
}
