package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Hospital;
/**
 * 医院Repository
 * @author Stanley
 *
 */
public interface IHospitalRepository  extends JpaRepository<Hospital, String>, JpaSpecificationExecutor<Hospital>{
	/**
	 * 根据医院Id查询
	 * @param hospitalId
	 * @return
	 */
	public Hospital findByHospitalId(String hospitalId);
	/**
	 * 查询医院的下属医院
	 * @param hospitalId
	 * @return
	 */
	public List<Hospital> findByParentNo(String hospitalId);
}
