package com.uniclans.ophthalmology.modules.diagnose.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.TestRecord;
/**
 * 检查记录表Repository
 * @author Stanley
 *
 */
public interface ITestRecordRepository  extends JpaRepository<TestRecord, String>, JpaSpecificationExecutor<TestRecord>{
	
	public TestRecord findByTestRecordId(String testRecordId)throws Exception;
	
	public List<TestRecord> findByHospitalId(String hospitalId);
	
	public List<TestRecord> findByHospitalIdIn(List<String> hospitalId);
	
	public List<TestRecord> findByPatientId(String patientId);
	
	public List<TestRecord> findByHospitalIdAndDiagnoseState(String hospitalId,String diagnoseState);
}
