package com.hjh.springbootshiro2.service;

import java.util.List;
import com.hjh.springbootshiro2.pojo.User;
import com.hjh.springbootshiro2.pojo.UserOnlineBo;

public interface UserService {
	public String getPassword(String name);

	public User getByName(String name);

	public List<User> list();

	public void add(User user);

	public void delete(Long id);

	public User get(Long id);

	public void update(User user);

	public List<UserOnlineBo> onlineUser();
}