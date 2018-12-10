package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DiseaseTypeSuggest;
/**
 * 疾病类型Repository
 * @author Stanley
 *
 */
public interface IDiseaseTypeSuggestRepository  extends JpaRepository<DiseaseTypeSuggest, String>, JpaSpecificationExecutor<DiseaseTypeSuggest>{
	public List<DiseaseTypeSuggest> findByDiseaseTypeId(String diseaseTypeId)throws Exception;
	public DiseaseTypeSuggest findBySuggestId(String suggestId)throws Exception;
}
