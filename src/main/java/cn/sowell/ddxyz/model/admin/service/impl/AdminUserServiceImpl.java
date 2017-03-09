package cn.sowell.ddxyz.model.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.sowell.ddxyz.model.admin.dao.AdminUserDao;
import cn.sowell.ddxyz.model.admin.service.AdminUserService;

@Service("adminUserSerivice")
public class AdminUserServiceImpl implements AdminUserService {

	@Resource
	AdminUserDao userDao;
		
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if(StringUtils.hasText(username)){
			return userDao.getUser(username);
		}
		return null;
	}

}
