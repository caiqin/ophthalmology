package com.uniclans.ophthalmology.modules.basedata.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.AiResultTranslate;
/**
 * 患者Repository
 * @author Stanley
 *
 */
public interface IAiResultTranslateRepository  extends JpaRepository<AiResultTranslate, String>, JpaSpecificationExecutor<AiResultTranslate>{
	
}
