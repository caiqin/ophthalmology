package com.uniclans.ophthalmology.modules.permission.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.permission.dao.model.entity.UserRole;

public interface IUserRoleRepository  extends JpaRepository<UserRole, String>, JpaSpecificationExecutor<UserRole>{
	/**
	 * 
	 * @param roleId
	 * @return
	 */
	public List<UserRole> findByRoleId(String roleId);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserRole> findByUserId(String userId);
	
}
