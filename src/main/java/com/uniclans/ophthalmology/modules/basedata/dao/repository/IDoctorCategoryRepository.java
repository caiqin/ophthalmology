package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DoctorCategory;
/**
 * 医生专长分类Repository
 * @author Stanley
 *
 */
public interface IDoctorCategoryRepository  extends JpaRepository<DoctorCategory, String>, JpaSpecificationExecutor<DoctorCategory>{
	
}
