package com.uniclans.ophthalmology.modules.diagnose.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.DiagnoseRecord;
/**
 * 诊断记录Repository
 * @author Stanley
 *
 */
public interface IDiagnoseRecordRepository  extends JpaRepository<DiagnoseRecord, String>, JpaSpecificationExecutor<DiagnoseRecord>{
	
	public DiagnoseRecord findByTestRecordId(String testRecordId)throws Exception;
	/**
	 * 根据医生编号查询诊断记录
	 * @param doctorId
	 * @return
	 * @throws Exception
	 */
	public List<DiagnoseRecord> findByDoctorId(String doctorId)throws Exception;
}
