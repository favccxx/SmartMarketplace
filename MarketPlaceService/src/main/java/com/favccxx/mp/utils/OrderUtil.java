package com.favccxx.mp.utils;

import java.util.UUID;

public class OrderUtil {

	public static String getOrderNo() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
