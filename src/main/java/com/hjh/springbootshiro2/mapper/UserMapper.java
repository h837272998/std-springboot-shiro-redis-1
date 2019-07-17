package com.hjh.springbootshiro2.mapper;

import com.hjh.springbootshiro2.pojo.User;
import com.hjh.springbootshiro2.pojo.UserExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(User record);

	int insertSelective(User record);

	List<User> selectByExample(UserExample example);

	User selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
}