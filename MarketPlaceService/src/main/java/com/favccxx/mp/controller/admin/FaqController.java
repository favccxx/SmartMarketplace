package com.favccxx.mp.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartFaq;
import com.favccxx.mp.service.FaqService;
import com.favccxx.mp.utils.SortUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/mgr/faq")
@Api(tags="1.7 问答管理接口" )
public class FaqController {

	@Autowired
	FaqService faqService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SmartFaq.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询评论信息")
	public RestResult<Page<SmartFaq>> list(			
			@RequestParam(value = "content", required=false) String content,
			@RequestParam(value = "productId", required=false) String productId,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		
		Page<SmartFaq> pageData = null;
		SmartFaq faq = new SmartFaq();

		
		if(StringUtils.isNotBlank(content)) {
			faq.setContent(content);
		}
		
		if(StringUtils.isNoneBlank(productId)) {
			faq.setProductId(Long.valueOf(productId));
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = faqService.pageQuery(faq, pageable);
		
		return RestResult.sucess(pageData);	
	}
	
}
