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

import com.favccxx.mp.entity.SmartReview;
import com.favccxx.mp.repository.ReviewRepository;
import com.favccxx.mp.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	@Override
	public SmartReview findById(long id) {
		return reviewRepository.findById(id).get();
	}

	@Transactional
	@Override
	public void save(SmartReview review) {
		reviewRepository.save(review);
	}

	@Transactional
	@Override
	public void delete(long id) {
		reviewRepository.deleteById(id);
	}

	@Override
	public Page<SmartReview> pageQuery(SmartReview review, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("content", GenericPropertyMatchers.contains())
				.withIgnorePaths("id")
				.withIgnorePaths("score")
				.withIgnorePaths("replyId")
				.withIgnorePaths("userId");
		
		if(review.getProductId()==0) {
			matcher.getIgnoredPaths().add("productId");
		}else {
			matcher.withMatcher("productId", GenericPropertyMatchers.exact());
		}
		
		if(StringUtils.isBlank(review.getReviewUsername())) {
			matcher.getIgnoredPaths().add("reviewUsername");
		}else {
			matcher.withMatcher("reviewUsername", GenericPropertyMatchers.exact());
		}

		Example<SmartReview> example = Example.of(review, matcher);

		return reviewRepository.findAll(example, pageable);
	}

}
