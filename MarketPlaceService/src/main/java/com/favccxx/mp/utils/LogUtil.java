package com.favccxx.mp.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志工具类
 * 根据请求获取ip地址
 * @author favccxx
 *
 */
public class LogUtil {
	
	//响应请求
	public static final String LOG_RESP_DATA = "LOG_RESP_DATA";

	/**
	 * 根据客户端请求获取客户端IP地址
	 * @param request
	 * @return
	 */
	public static String getCliectIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ip = str;
				break;
			}
		}
		return ip;
	}

	/**
	 * 获取客户端请求类型
	 * @param request
	 * @return
	 */
	public static String getRequestType(HttpServletRequest request) {
		return request.getHeader("X-Requested-With");
	}

}
