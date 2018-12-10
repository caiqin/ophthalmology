package com.uniclans.ophthalmology.modules.basedata.service.component.impl;

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
import com.uniclans.ophthalmology.basecomponent.utils.CreatorNoUtil;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Doctor;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDoctorRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IDoctorService;
import com.uniclans.ophthalmology.modules.basedata.service.model.DoctorVo;
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.DiagnoseRecord;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.IDiagnoseRecordRepository;

/**
 * 
 * @author Stanley
 *
 */
@Component
public class DoctorServiceImpl implements IDoctorService {

	@Resource
	private IDoctorRepository doctorRepository;
	@Resource
	private IDiagnoseRecordRepository diagnoseRecordRepository;
	
	@Override
	@Transactional(readOnly=true)
	public PageFinder<DoctorVo> pagedDoctors(DoctorVo queryVo) throws Exception {
		final String name = StringUtils.parseStrNull(queryVo.getDoctorName());
		Specification<Doctor> sf = new Specification<Doctor>() {
			@Override
			public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate doctorNameLike = null;
				if (!name.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					doctorNameLike = cb.like(root.<String>get("doctorName"), "%" + name + "%");
				}
				if (null != doctorNameLike) {
					query.where(cb.and(doctorNameLike));
				}
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<Doctor> pages = doctorRepository.findAll(sf, pageable);		
		PageFinder<Doctor> pageFinder = new PageFinder<Doctor>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<DoctorVo> data = new ArrayList<>();
			for (Doctor doctor : pageFinder.getData()) {
				DoctorVo doctorVo = new DoctorVo();
				BeanUtils.deepCopy(doctor, doctorVo);
				data.add(doctorVo);
			}
			return new PageFinder<DoctorVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<DoctorVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public void addDoctor(DoctorVo doctorVo) throws Exception {
		Doctor doctor = new Doctor();
		BeanUtils.deepCopy(doctorVo, doctor);
		doctor.setDoctorId(CreatorNoUtil.getCode());
		doctorRepository.save(doctor);
	}

	@Override
	@Transactional
	public void updateDoctor(DoctorVo doctorVo) throws Exception {
		Doctor doctor = doctorRepository.findOne(doctorVo.getId());
		String docCatId = doctorVo.getDocCatId();
		String docCatName = doctorVo.getDocCatName();
		String doctorIntroduce = doctorVo.getDoctorIntroduce();
		String hospitalId = doctorVo.getHospitalId();
		String hospitalName = doctorVo.getHospitalName();
		String doctorName = doctorVo.getDoctorName();
		String url = doctorVo.getUrl();
		if(LogicUtil.isNotNullAndEmpty(doctorName)&&!doctorName.equals(doctor.getDoctorName())) {
			doctor.setDoctorName(doctorName);
			String doctorId = doctor.getDoctorId();
			List<DiagnoseRecord> list = diagnoseRecordRepository.findByDoctorId(doctorId);
			if(list!=null&&!list.isEmpty()) {
				for (DiagnoseRecord diagnoseRecord : list) {
					diagnoseRecord.setDoctorName(doctorName);
				}
			}
		}
		if(LogicUtil.isNotNullAndEmpty(docCatName)&&!docCatName.equals(doctor.getDocCatName())) {
			doctor.setDocCatName(docCatName);
		}
		if(LogicUtil.isNotNullAndEmpty(docCatId)&&!docCatId.equals(doctor.getDocCatId())) {
			doctor.setDocCatId(docCatId);
		}
		if(LogicUtil.isNotNullAndEmpty(doctorIntroduce)&&!doctorIntroduce.equals(doctor.getDoctorIntroduce())) {
			doctor.setDoctorIntroduce(doctorIntroduce);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalId)&&!hospitalId.equals(doctor.getHospitalId())) {
			doctor.setHospitalId(hospitalId);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalName)&&!hospitalName.equals(doctor.getHospitalName())) {
			doctor.setHospitalName(hospitalName);
		}
		if(url!=null&&!url.equals(doctor.getUrl())) {
			doctor.setUrl(url);
		}

	}

	@Override
	@Transactional(readOnly=true)
	public DoctorVo getDoctor(String id) throws Exception {
		Doctor findOne = doctorRepository.findOne(id);
		DoctorVo vo = new DoctorVo();
		BeanUtils.deepCopy(findOne, vo);
		return vo;
	}

}
