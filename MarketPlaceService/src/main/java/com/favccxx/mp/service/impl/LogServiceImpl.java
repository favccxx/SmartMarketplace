package com.favccxx.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.mp.entity.SmartLog;
import com.favccxx.mp.repository.LogRepository;
import com.favccxx.mp.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogRepository logRepository;
	
	@Override
	public Page<SmartLog> pageQuery(SmartLog log, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("uri", GenericPropertyMatchers.startsWith())
				.withMatcher("type", GenericPropertyMatchers.startsWith())
				.withMatcher("method", GenericPropertyMatchers.exact())
				.withIgnorePaths("id")
				.withIgnorePaths("spendTime");
		if(log.getStatusCode() == 0) {
			matcher.getIgnoredPaths().add("statusCode");
		}else {
			matcher.withMatcher("statusCode", GenericPropertyMatchers.exact());
		}
		
		

		Example<SmartLog> example = Example.of(log, matcher);

		return logRepository.findAll(example, pageable);
	}

}
