package com.favccxx.mp.utils;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 获取排序顺序
 * @author favccxx
 *
 */
public class SortUtil {
	
	public static Sort getSort(String field) {
		Direction direction = Direction.ASC;
		if(field.startsWith("-")) {
			direction = Direction.DESC;
		}
		
		if(field.startsWith("+") || field.startsWith("-")) {
			return new Sort(direction, field.substring(1));
		}
		
		return new Sort(direction, field);
		
	}

}
