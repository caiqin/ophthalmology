package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.HospitalId;
/**
 * 患者Repository
 * @author Stanley
 *
 */
public interface IHospitalIdRepository  extends JpaRepository<HospitalId, String>, JpaSpecificationExecutor<HospitalId>{
	
	public HospitalId getByHospitalId(String hospitalId)throws Exception;
}
