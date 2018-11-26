package com.favccxx.mp.constants;

/**
 * 产品类别
 * @author favccxx
 *
 */
public enum CategoryStatus {
	
	ENABLE("ENABLE", 200),
	DISABLE("DISABLE", 300),
	DELETED("DELETED", 400);
	

	private final int value;

	private final String detail;

	CategoryStatus(String detail, int value) {
		this.value = value;
		this.detail = detail;
	}
	
	public int value() {
		return this.value;
	}

	public String getDetail() {
		return this.detail;
	}
}
