package com.uniclans.ophthalmology.modules.permission.dao.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemResource;


public interface ISystemResourceRepository  extends JpaRepository<SystemResource, String>, JpaSpecificationExecutor<SystemResource>{

	public List<SystemResource> findByIdIn(Collection<String> resIds);
}

