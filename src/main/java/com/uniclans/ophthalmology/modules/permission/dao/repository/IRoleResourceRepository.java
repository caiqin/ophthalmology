package com.uniclans.ophthalmology.modules.permission.dao.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.uniclans.ophthalmology.modules.permission.dao.model.entity.RoleResource;

public interface IRoleResourceRepository extends JpaRepository<RoleResource, String>, JpaSpecificationExecutor<RoleResource> {
	
	
	public List<RoleResource> findByRoleIdIn(Collection<String> roleIds);
	
	
	public Page<RoleResource> 	findByRoleId(String roleId,Pageable pageable);

	@Modifying
	@Query(value = "delete from tbl_system_role_resource  where role_id=?1 and resource_id=?2",nativeQuery=true)
	public int delete(String roleId, String resId) throws Exception;
	
}
