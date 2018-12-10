package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DiseaseType;
/**
 * 疾病类型Repository
 * @author Stanley
 *
 */
public interface IDiseaseTypeRepository  extends JpaRepository<DiseaseType, String>, JpaSpecificationExecutor<DiseaseType>{
	public DiseaseType findByDiseaseTypeId(String diseaseTypeId)throws Exception;
}
