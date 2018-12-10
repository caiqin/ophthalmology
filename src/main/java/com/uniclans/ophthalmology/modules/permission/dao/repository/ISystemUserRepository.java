package com.uniclans.ophthalmology.modules.permission.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemUser;


public interface ISystemUserRepository extends JpaRepository<SystemUser, String>, JpaSpecificationExecutor<SystemUser>{

	public SystemUser findByLoginNameAndLoginPassword(String userName,String password);
	
	public SystemUser findByLoginName(String loginName);
	
	public SystemUser findByUserNo(String userNo);
}
