package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Patient;
/**
 * 患者Repository
 * @author Stanley
 *
 */
public interface IPatientRepository  extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient>{
	
	public Patient findByPatientId(String patientId);
	
	public List<Patient> findByHospitalId(String hospitalId);
}
