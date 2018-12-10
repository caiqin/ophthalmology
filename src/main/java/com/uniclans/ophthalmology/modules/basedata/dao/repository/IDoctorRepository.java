package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Doctor;
/**
 * 医生Repository
 * @author Stanley
 *
 */
public interface IDoctorRepository  extends JpaRepository<Doctor, String>, JpaSpecificationExecutor<Doctor>{
	public Doctor findByDoctorId(String doctorId)throws Exception;
}
