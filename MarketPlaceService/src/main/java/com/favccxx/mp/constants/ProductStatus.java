package com.favccxx.mp.constants;

public enum ProductStatus {
	
	
	CREATED(100, "Product Created"),
	
	DRAFT(201, "Draft product"),
	
	PUBLISHED(200, "Product Published"),
	
	OFFLINE(300, "Product Offline"),
	
	DELETED(400, "Product Deleted");
	
	private final int val;

//	private final int value;
//
	private final String reasonPhrase;


	ProductStatus(int val, String reasonPhrase) {
		this.val = val;
		this.reasonPhrase = reasonPhrase;
	}


	public int value() {
		return this.val;
	}


	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	

}
