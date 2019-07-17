package com.hjh.springbootshiro2.mapper;

import com.hjh.springbootshiro2.pojo.RolePermission;
import com.hjh.springbootshiro2.pojo.RolePermissionExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RolePermissionMapper {
	int deleteByPrimaryKey(Long id);

	int insert(RolePermission record);

	int insertSelective(RolePermission record);

	List<RolePermission> selectByExample(RolePermissionExample example);

	RolePermission selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(RolePermission record);

	int updateByPrimaryKey(RolePermission record);
}