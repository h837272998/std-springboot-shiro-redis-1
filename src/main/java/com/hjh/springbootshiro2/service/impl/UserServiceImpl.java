package com.hjh.springbootshiro2.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.hjh.springbootshiro2.pojo.UserOnlineBo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjh.springbootshiro2.mapper.UserMapper;
import com.hjh.springbootshiro2.pojo.User;
import com.hjh.springbootshiro2.pojo.UserExample;
import com.hjh.springbootshiro2.service.UserRoleService;
import com.hjh.springbootshiro2.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	RedisSessionDAO redisSessionDAO;

	@Override
	public String getPassword(String name) {
		User user = getByName(name);
		if (null == user)
			return null;
		return user.getPassword();
	}

	@Override
	public User getByName(String name) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name);
		List<User> users = userMapper.selectByExample(example);
		if (users.isEmpty())
			return null;
		return users.get(0);
	}

	@Override
	public void add(User u) {
		userMapper.insert(u);
	}

	@Override
	public void delete(Long id) {
		userMapper.deleteByPrimaryKey(id);
		userRoleService.deleteByUser(id);
	}

	@Override
	public void update(User u) {
		userMapper.updateByPrimaryKeySelective(u);
	}

	@Override
	public User get(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> list() {
		UserExample example = new UserExample();
		example.setOrderByClause("id desc");
		return userMapper.selectByExample(example);

	}

	/***
	 * @Description:获得在线用户
	 * @Author: HJH
	 * @Date: 2019-07-18 17:07
	 * @Param: []
	 * @Return: java.util.List<com.hjh.springbootshiro2.pojo.UserOnlineBo>
	 */
	public List<UserOnlineBo> onlineUser(){
		Collection<Session> sessions = redisSessionDAO.getActiveSessions();
		Iterator<Session> iterator = sessions.iterator();
		List<UserOnlineBo> userOnlineBos = new ArrayList<UserOnlineBo>();
		while (iterator.hasNext()){
			Session next = iterator.next();
			Object obj = next.getAttribute("kickout");
			if (obj!=null){
				continue;
			}
			UserOnlineBo onlineUser = getSessionBo(next);
			userOnlineBos.add(onlineUser);
		}
		return userOnlineBos;
	}

	public UserOnlineBo getSessionBo(Session session){
		//获取session登录信息。
		Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if(null == obj){
			return null;
		}
		//确保是 SimplePrincipalCollection对象。
		if(obj instanceof SimplePrincipalCollection){
			SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
			/**
			 * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中
			 * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());的user 对象。
			 */
			obj = spc.getPrimaryPrincipal();
			if(null != obj ){
				User user = new User();
				user.setName((String) obj);
				//存储session + user 综合信息
				UserOnlineBo userBo = new UserOnlineBo(user);
				//主机的ip地址
				userBo.setHost(session.getHost());
				//session ID
				userBo.setSessionId(session.getId().toString());
				//session最后一次与系统交互的时间
				userBo.setLastAccess(session.getLastAccessTime());
				//回话到期 ttl(ms)
				userBo.setTimeout(session.getTimeout());
				//session创建时间
				userBo.setStartTime(session.getStartTimestamp());
				//是否踢出
				userBo.setSessionStatus(false);
				return userBo;
			}
		}
		return null;
	}


}