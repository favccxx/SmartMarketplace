package com.favccxx.mp.utils.result;

import com.favccxx.mp.constants.SysConstants;

public class RestResult<T> {
	
	
	
	public RestResult() {
		
	}
	
	public RestResult(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public RestResult(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	
	int code;
	
	String message;
	
	T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public RestResult<T> sucess(int code, String message) {
		return new RestResult<T>(code, message);
	}
	
	public RestResult<T> sucess(T data, String message) {
		return new RestResult<T>(200, message, data);
	}
	

	public static <T> RestResult<T> sucess(){
		return new RestResult<T>(200, "操作成功", null);
	}
	
	public static <T> RestResult<T> sucess(T data){
		return new RestResult<T>(200, "操作成功", data);
	}
	
	
	public static <T> RestResult<T> error(int code, String message){
		return new RestResult<T>(code, message);
	}
	
	public static <T> RestResult<T> invalidParams(){
		return new RestResult<T>(4000, "参数错误");
	}
	
	public static <T> RestResult<T> lackParams(String message){
		return new RestResult<T>(4001, message);
	}

}
