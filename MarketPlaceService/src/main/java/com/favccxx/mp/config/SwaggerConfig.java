package com.favccxx.mp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.favccxx.mp.constants.SysConstants;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {

		List<Parameter> list = new ArrayList<Parameter>();
		ParameterBuilder builder = new ParameterBuilder();
		builder.parameterType("header")
		.name(SysConstants.HEADER_TOKEN_KEY)
		.defaultValue("")
		.description("统一身份认证TOKEN")
		.modelRef(new ModelRef("string"))
		.required(false)
		.build();
		list.add(builder.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.favccxx.mp.controller"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(list);
	}

	@SuppressWarnings("rawtypes")
	private ApiInfo apiInfo() {
		return new ApiInfo( "商城服务管理接口",
			      "Restful后台管理接口",
			      "V1",
			      "xxx.www.xx",
			      new Contact("zhangsan", "www.baidu.com", "xxx@qq.com"),
			      "Apache license",
			      "Apache license Url",
			      new ArrayList<VendorExtension>());
	}

}
