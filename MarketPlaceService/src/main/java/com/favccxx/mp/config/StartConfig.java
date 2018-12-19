package com.favccxx.mp.config;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartRole;
import com.favccxx.mp.entity.SmartShop;
import com.favccxx.mp.repository.RoleRepository;
import com.favccxx.mp.repository.ShopRepository;

@Component
@Order(1)
public class StartConfig implements ApplicationRunner {

	@Autowired
	ShopRepository clubRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initClub();
		initRoles();
	}

	/**
	 * 初始化默认的俱乐部信息
	 */
	private void initClub() {
		SmartShop club = clubRepository.findByShopCode(SysConstants.DEFAULT_CLUB);
		if (club == null) {
			club = new SmartShop();
			club.setShopCode(SysConstants.DEFAULT_CLUB);
			club.setShopName("易商城");
			club.setCharger("陈先生");
			club.setChargeTel("1871xxxxxxx");
			club.setStatus("200");
			club.setDescription("<p><strong>易商城</strong>是陈先生基于Panjiachen的VUE-ELEMENT-ADMIN开发的一套简易WEB商城项目。前端基于VUE2+Element，支持国际化，动态路由和权限验证，后台采用SpringBoot微服务+Shiro进行身份认证与鉴权，同时又集成了Swagger方便进行接口测试。</p>");
			club.setCreateTime(new Date());
			club.setCreateUserName("admin");
			club.setUpdateTime(new Date());
			clubRepository.save(club);
		}
	}
	
	private void initRoles() {
		List<SmartRole> list = roleRepository.findAll();
		if(list == null || list.size() == 0) {
			SmartRole rootRole = new SmartRole();
			rootRole.setRoleCode("ROOT");
			rootRole.setRoleName("超级管理员");
			rootRole.setStatus(1);
			roleRepository.save(rootRole);
			
			SmartRole adminRole = new SmartRole();
			adminRole.setRoleCode("ADMIN");
			adminRole.setRoleName("管理员");
			adminRole.setStatus(1);
			roleRepository.save(adminRole);
			
			SmartRole guestRole = new SmartRole();
			guestRole.setRoleCode("GUEST");
			guestRole.setRoleName("访客");
			guestRole.setStatus(1);
			roleRepository.save(guestRole);
		}
	}

}
