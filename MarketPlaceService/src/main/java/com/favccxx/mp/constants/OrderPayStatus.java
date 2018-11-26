package com.favccxx.mp.constants;

public enum OrderPayStatus {
	
	NEEDPAY("NEEDPAY", 0),
	PAID("PAID", 1),
	RETURNED("RETURNED", 2);

	private final int value;

	private final String detail;

	OrderPayStatus(String detail, int value) {
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
