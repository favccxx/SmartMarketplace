package com.favccxx.mp.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.favccxx.mp.entity.SmartLog;
import com.favccxx.mp.repository.LogRepository;
import com.favccxx.mp.utils.LogUtil;

/**
 * 日志拦截器
 * 
 * @author favccxx
 *
 */
public class LogInterceptor implements HandlerInterceptor {

	private static final String LOG_SEND_TIME = "SEND_TIME"; // 日志请求时间
	private static final String LOG_ENTITY = "LOG_ENTITY"; // 日志请求实体

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SmartLog log = new SmartLog();

		String sessionId = request.getRequestedSessionId();
		String url = request.getRequestURI();
		String paramData = JSON.toJSONString(request.getParameterMap(),
				SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
		// 设置客户端ip
		log.setClientIp(LogUtil.getCliectIp(request));
		log.setMethod(request.getMethod());
		log.setType(LogUtil.getRequestType(request));
		log.setParams(paramData);
		log.setUri(url);
		log.setSessionId(sessionId);
		log.setStartTime(new Date());
		request.setAttribute(LOG_SEND_TIME, System.currentTimeMillis());
		// 设置请求实体到request内，方面afterCompletion方法调用
		request.setAttribute(LOG_ENTITY, log);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		int statusCode = response.getStatus();

		long currentTime = System.currentTimeMillis();
		long time = Long.valueOf(request.getAttribute(LOG_SEND_TIME).toString());
		SmartLog log = (SmartLog) request.getAttribute(LOG_ENTITY);
		log.setEndTime(new Date());

		log.setSpendTime(currentTime - time);
		log.setStatusCode(statusCode);

		// 设置返回值
		log.setRespData(JSON.toJSONString(request.getAttribute(LogUtil.LOG_RESP_DATA),
				SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue));
		
		// 执行将日志写入数据库
		LogRepository logRepository = getDao(LogRepository.class, request);
		logRepository.save(log);
	}

	private <T> T getDao(Class<T> t, HttpServletRequest request) {
		BeanFactory beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return beanFactory.getBean(t);
	}

}
