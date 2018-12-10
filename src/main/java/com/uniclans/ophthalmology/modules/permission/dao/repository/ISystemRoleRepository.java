package com.uniclans.ophthalmology.modules.permission.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemRole;


public interface ISystemRoleRepository extends JpaRepository<SystemRole, String>, JpaSpecificationExecutor<SystemRole>{

}
