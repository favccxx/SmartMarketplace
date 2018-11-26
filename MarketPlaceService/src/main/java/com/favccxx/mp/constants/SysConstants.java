package com.favccxx.mp.constants;

public interface SysConstants {
	
	public static final String DEFAULT_CLUB = "SMARTCLUB";
	
	//前端发送给后台的令牌参数
	public static final String HEADER_TOKEN_KEY = "X-Token";
	
	public static final String PAGE_SIZE = "10";	//默认分页每页显示的数目
	
	public enum OrderStatus implements SysConstants {
		CREATED,
		CANCELD,
		FINISHED,
		DELETED
	}
	
	
	public static final int CATEGORY_CREATED = 200;
	public static final int CATEGORY_FROZEN = 201;
	public static final int CATEGORY_DELETED = 202;
	
	
	public static final String IMAGE_PATH = "F://images/";	//图片存储路径
	public static final String IMAGE_SLIDER = "SLIDER";		//图片展示图类型
	public static final String IMAGE_CONTENT = "CONTENT";	//产品内容图片
	
	
	public static final String BASE_URL = "http://172.17.3.111:8088/api";
	
}
