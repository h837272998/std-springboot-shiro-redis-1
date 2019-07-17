package com.hjh.springbootshiro2.service;

import com.hjh.springbootshiro2.pojo.User;

public interface UserRoleService {

	public void setRoles(User user, long[] roleIds);

	public void deleteByUser(long userId);

	public void deleteByRole(long roleId);

}