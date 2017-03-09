package cn.sowell.ddxyz.model.admin.dao;

import cn.sowell.ddxyz.model.admin.pojo.AdminUser;

public interface AdminUserDao {
	AdminUser getUser(String username);
}
