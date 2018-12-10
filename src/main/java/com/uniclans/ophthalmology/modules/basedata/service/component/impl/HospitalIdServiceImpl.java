package com.uniclans.ophthalmology.modules.basedata.service.component.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.ExcelUpLoadUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.HospitalId;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IHospitalIdRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IHospitalIdService;
import com.uniclans.ophthalmology.modules.basedata.service.model.HospitalIdVo;

@Component
public class HospitalIdServiceImpl implements IHospitalIdService {
	@Resource
	private IHospitalIdRepository hospitalIdRepository;

	@Override
	@Transactional
	public void add(HospitalIdVo hospitalIdVo) throws Exception {
		HospitalId entity = new HospitalId();
		BeanUtils.deepCopy(hospitalIdVo, entity);
		hospitalIdRepository.save(entity);
	}

	@Override
	@Transactional
	public void update(HospitalIdVo hospitalIdVo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void delete(String ids) throws Exception {
		String[] idList = ids.split(",");
		if(idList!=null&&idList.length>0) {
			for (String id : idList) {
				hospitalIdRepository.delete(id);
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public PageFinder<HospitalIdVo> pageData(HospitalIdVo queryVo) throws Exception {
		Specification<HospitalId> sf = new Specification<HospitalId>() {
			@Override
			public Predicate toPredicate(Root<HospitalId> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<HospitalId> pages = hospitalIdRepository.findAll(sf, pageable);		
		PageFinder<HospitalId> pageFinder = new PageFinder<HospitalId>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<HospitalIdVo> data = new ArrayList<HospitalIdVo>();
			for (HospitalId hospitalId : pageFinder.getData()) {
				HospitalIdVo hospitalIdVo = new HospitalIdVo();
				BeanUtils.deepCopy(hospitalId, hospitalIdVo);
				data.add(hospitalIdVo);
			}
			return new PageFinder<HospitalIdVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<HospitalIdVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public void importData(File file) throws Exception {
		// TODO Auto-generated method stub
		List<String[]> list = ExcelUpLoadUtils.getExcelDataWithPOI(file, 0);
		if(list!=null&&!list.isEmpty()) {
			for (String[] data : list) {
				if(data!=null&&data.length>0) {
					if(data[0]!=null&&!data[0].isEmpty()) {
						HospitalId byHospitalId = hospitalIdRepository.getByHospitalId(data[0]);
						if(byHospitalId==null) {
							HospitalId entity = new HospitalId();
							entity.setHospitalId(data[0]);
							entity.setStartTime(DateUtils.parseTime(data[1]));
							entity.setEndTime(DateUtils.parseTime(data[2]));
							hospitalIdRepository.save(entity);
						}
					}
				}
			}
		}
		
	}

}
