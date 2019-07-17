package com.hjh.springbootshiro2.mapper;

import com.hjh.springbootshiro2.pojo.Role;
import com.hjh.springbootshiro2.pojo.RoleExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Role record);

	int insertSelective(Role record);

	List<Role> selectByExample(RoleExample example);

	Role selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);
}