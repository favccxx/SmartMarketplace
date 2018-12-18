package com.favccxx.mp.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.mp.entity.SmartFaq;
import com.favccxx.mp.repository.FaqRepository;
import com.favccxx.mp.service.FaqService;

@Service
public class FaqServiceImpl implements FaqService {
	
	@Autowired
	FaqRepository faqRepository;

	@Override
	public SmartFaq findById(long id) {
		return faqRepository.findById(id).get();
	}

	@Transactional
	@Override
	public void save(SmartFaq faq) {
		faqRepository.save(faq);
	}

	@Transactional
	@Override
	public void delete(long id) {
		faqRepository.deleteById(id);
	}

	@Override
	public Page<SmartFaq> pageQuery(SmartFaq faq, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("content", GenericPropertyMatchers.contains())
				.withIgnorePaths("id")
				.withIgnorePaths("score")
				.withIgnorePaths("replyId")
				.withIgnorePaths("userId");
		
		if(faq.getProductId()==0) {
			matcher.getIgnoredPaths().add("productId");
		}else {
			matcher.withMatcher("productId", GenericPropertyMatchers.exact());
		}
		
		if(StringUtils.isBlank(faq.getUsername())) {
			matcher.getIgnoredPaths().add("username");
		}else {
			matcher.withMatcher("username", GenericPropertyMatchers.exact());
		}

		Example<SmartFaq> example = Example.of(faq, matcher);

		return faqRepository.findAll(example, pageable);
	}

}
