package com.hjh.springbootshiro2.mapper;

import com.hjh.springbootshiro2.pojo.Permission;
import com.hjh.springbootshiro2.pojo.PermissionExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PermissionMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Permission record);

	int insertSelective(Permission record);

	List<Permission> selectByExample(PermissionExample example);

	Permission selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Permission record);

	int updateByPrimaryKey(Permission record);
}