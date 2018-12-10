package com.uniclans.ophthalmology.modules.diagnose.service.component.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.CreatorNoUtil;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DiseaseType;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DiseaseTypeSuggest;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Doctor;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Hospital;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Patient;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDiseaseTypeRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDiseaseTypeSuggestRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDoctorRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IHospitalRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IPatientRepository;
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.DiagnoseRecord;
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.TestRecord;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.IDiagnoseRecordRepository;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.ITestRecordRepository;
import com.uniclans.ophthalmology.modules.diagnose.service.component.IDiagnoseRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DiagnoseRecordVo;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemUser;
import com.uniclans.ophthalmology.modules.permission.dao.repository.ISystemUserRepository;

@Component
public class DiagnoseRecordServiceImpl implements IDiagnoseRecordService {
	@Resource
	private EntityManager entityManager;
	@Resource
	private IDiagnoseRecordRepository diagnoseRecordRepository;
	@Resource
	private ITestRecordRepository testRecordRepository;
	@Resource
	private IDiseaseTypeRepository diseaseTypeRepository;
	@Resource
	private IPatientRepository patientRepository;
	@Resource
	private IDoctorRepository doctorRepository;
	@Resource
	private IDiseaseTypeSuggestRepository diseaseTypeSuggestRepository;
	@Resource
	private IHospitalRepository hospitalRepository;
	@Resource
	private ISystemUserRepository userRepository;

	@Override
	@Transactional
	public PageFinder<DiagnoseRecordVo> pagedDiagnoseRecords(DiagnoseRecordVo queryVo,String userNo) throws Exception {
		final String doctorName = StringUtils.parseStrNull(queryVo.getDoctorName());
		final String patientName = StringUtils.parseStrNull(queryVo.getPatientName());
		final String startTime = StringUtils.parseStrNull(queryVo.getStartTime());
		final String endTime = StringUtils.parseStrNull(queryVo.getEndTime());
		
		SystemUser systemUser = userRepository.findByUserNo(userNo);
		String doctorId = systemUser.getDoctorId();
		String loginName = systemUser.getLoginName();
		Specification<DiagnoseRecord> sf = new Specification<DiagnoseRecord>() {
			@Override
			public Predicate toPredicate(Root<DiagnoseRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate doctorNameLike = null;
				List<Predicate> list = new ArrayList<>();
				if (!doctorName.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					doctorNameLike = cb.like(root.<String>get("doctorName"), "%" + doctorName + "%");
					list.add(doctorNameLike);
				}
				Predicate patientNameLike = null;
				if (!patientName.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					patientNameLike = cb.like(root.<String>get("patientName"), "%" + patientName + "%");
					list.add(patientNameLike);
				}
				Predicate patientIdEqual = null;
				Predicate testRecordIdIn = null;
				if(!loginName.equals("admin")) {
					if(doctorId!=null&&!doctorId.equals("")) {
						Doctor doctor = null;
						try {
							doctor = doctorRepository.findByDoctorId(doctorId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(doctor!=null&&!doctor.getHospitalId().equals("18101013263100001")){
							String hospitalId = doctor.getHospitalId();
//							hospitalIdPre = cb.equal(root.<String>get("hospitalId"), hospitalId);
							List<TestRecord> testRecords = testRecordRepository.findByHospitalIdAndDiagnoseState(hospitalId, "2");
							List<String> testIds = new ArrayList<>();
							if(testRecords!=null&&!testRecords.isEmpty()) {
								for (TestRecord tr : testRecords) {
									testIds.add(tr.getTestRecordId());
								}
								testRecordIdIn = root.<String>get("testRecordId").in(testIds);
							}
							List<Patient> patients = patientRepository.findByHospitalId(hospitalId);
							List<String> patientIds = new ArrayList<>();
							if(patients!=null&&!patients.isEmpty()) {
								for (Patient patient : patients) {
									patientIds.add(patient.getPatientId());
								}
								patientIdEqual = root.<String>get("patientId").in(patientIds);
								
							}
							if(patientIdEqual!=null&&testRecordIdIn!=null) {
								list.add(cb.or(patientIdEqual,testRecordIdIn));
							}else if(patientIdEqual!=null&&testRecordIdIn==null) {
								list.add(patientIdEqual);
							}else if(patientIdEqual==null&&testRecordIdIn!=null) {
								list.add(testRecordIdIn);
							}
							
							
						}
						
					}
				}
				
				Predicate diagnoseTimePredicate = null;
				if (!startTime.isEmpty() && !endTime.isEmpty()) {
					try {
						diagnoseTimePredicate = cb.between(root.<Date>get("diagnoseTime"), DateUtils.parseTime(startTime),DateUtils.parseTime(endTime));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(diagnoseTimePredicate);
				} else if (!startTime.isEmpty() && endTime.isEmpty()) {
					try {
						diagnoseTimePredicate = cb.greaterThanOrEqualTo(root.<Date>get("diagnoseTime"), DateUtils.parseTime(startTime));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(diagnoseTimePredicate);
				} else if (startTime.isEmpty() && !endTime.isEmpty()) {
					try {
						diagnoseTimePredicate = cb.lessThanOrEqualTo(root.<Date>get("diagnoseTime"),DateUtils.parseTime(endTime));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(diagnoseTimePredicate);
				}
				if(list!=null&&!list.isEmpty()) {
					Predicate[] a = new Predicate[list.size()];
					query.where(list.toArray(a));
				}
				return null;
			}
			
		};
		Sort sort = new Sort(Direction.DESC, "diagnoseTime");
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize(), sort);
		Page<DiagnoseRecord> pages = diagnoseRecordRepository.findAll(sf, pageable);
		PageFinder<DiagnoseRecord> pageFinder = new PageFinder<DiagnoseRecord>(queryVo.getPageNo(),
				queryVo.getPageSize(), (int) pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<DiagnoseRecordVo> data = new ArrayList<>();
			for (DiagnoseRecord diagnoseRecord : pageFinder.getData()) {
				String name = diagnoseRecord.getPatientName();
				if (name == null || name.isEmpty()) {
					Patient patient = patientRepository.findByPatientId(diagnoseRecord.getPatientId());
					diagnoseRecord.setPatientName(patient.getName());
				}

				DiagnoseRecordVo diagnoseRecordVo = new DiagnoseRecordVo();
				BeanUtils.deepCopy(diagnoseRecord, diagnoseRecordVo);
				String diseaseLeft = diagnoseRecordVo.getDiseaseLeft();
				if (diseaseLeft != null && !diseaseLeft.isEmpty()) {
					String[] dis = diseaseLeft.split(",");
					String diseases = "";
					if (dis != null && dis.length > 0) {
						for (String string : dis) {
							if (string.contains("#")) {
								DiseaseType diseaseType = diseaseTypeRepository
										.findByDiseaseTypeId(string.split("#")[0]);
								if (diseaseType != null) {
									diseases = diseases + diseaseType.getDiseaseTypeName() + string.split("#")[1] + "级";
									diseases = diseases + ",";
								}
							} else {
								DiseaseType diseaseType = diseaseTypeRepository.findByDiseaseTypeId(string);
								if (diseaseType != null) {
									diseases = diseases + diseaseType.getDiseaseTypeName();
									diseases = diseases + ",";
								}
							}
						}
						if (diseases.endsWith(",")) {
							diseases = diseases.substring(0, diseases.length() - 1);
						}

					}
					diagnoseRecordVo.setDiseaseLeft(diseases);
				}
				String diseaseRight = diagnoseRecordVo.getDiseaseRight();
				if (diseaseRight != null && !diseaseRight.isEmpty()) {
					String[] dis = diseaseRight.split(",");
					String diseases = "";
					if (dis != null && dis.length > 0) {
						for (String string : dis) {
							if (string.contains("#")) {
								DiseaseType diseaseType = diseaseTypeRepository
										.findByDiseaseTypeId(string.split("#")[0]);
								if (diseaseType != null) {
									diseases = diseases + diseaseType.getDiseaseTypeName() + string.split("#")[1] + "级";
									diseases = diseases + ",";
								}
							} else {
								DiseaseType diseaseType = diseaseTypeRepository.findByDiseaseTypeId(string);
								if (diseaseType != null) {
									diseases = diseases + diseaseType.getDiseaseTypeName();
									diseases = diseases + ",";
								}
							}
						}
						if (diseases.endsWith(",")) {
							diseases = diseases.substring(0, diseases.length() - 1);
						}

					}
					diagnoseRecordVo.setDiseaseRight(diseases);
				}

				data.add(diagnoseRecordVo);
			}
			return new PageFinder<DiagnoseRecordVo>(queryVo.getPageNo(), queryVo.getPageSize(),
					pageFinder.getRowCount(), data);
		}
		return new PageFinder<DiagnoseRecordVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}
	@Override
	@Transactional
	public PageFinder<DiagnoseRecordVo> pagedDiagnoseRecords2Export(DiagnoseRecordVo queryVo) throws Exception {
		final String doctorName = StringUtils.parseStrNull(queryVo.getDoctorName());
		final String patientName = StringUtils.parseStrNull(queryVo.getPatientName());
		final String startTime = StringUtils.parseStrNull(queryVo.getStartTime());
		final String endTime = StringUtils.parseStrNull(queryVo.getEndTime());
		Specification<DiagnoseRecord> sf = new Specification<DiagnoseRecord>() {
			@Override
			public Predicate toPredicate(Root<DiagnoseRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate doctorNameLike = null;
				List<Predicate> list = new ArrayList<>();
				if (!doctorName.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					doctorNameLike = cb.like(root.<String>get("doctorName"), "%" + doctorName + "%");
					list.add(doctorNameLike);
				}
				Predicate patientNameLike = null;
				if (!patientName.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					patientNameLike = cb.like(root.<String>get("patientName"), "%" + patientName + "%");
					list.add(patientNameLike);
				}
				Predicate diagnoseTimePredicate = null;
				if (!startTime.isEmpty() && !endTime.isEmpty()) {
					try {
						diagnoseTimePredicate = cb.between(root.<Date>get("diagnoseTime"), DateUtils.parseTime(startTime),DateUtils.parseTime(endTime));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(diagnoseTimePredicate);
				} else if (!startTime.isEmpty() && endTime.isEmpty()) {
					try {
						diagnoseTimePredicate = cb.greaterThanOrEqualTo(root.<Date>get("diagnoseTime"), DateUtils.parseTime(startTime));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(diagnoseTimePredicate);
				} else if (startTime.isEmpty() && !endTime.isEmpty()) {
					try {
						diagnoseTimePredicate = cb.lessThanOrEqualTo(root.<Date>get("diagnoseTime"),DateUtils.parseTime(endTime));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					list.add(diagnoseTimePredicate);
				}
				if(list!=null&&!list.isEmpty()) {
					Predicate[] a = new Predicate[list.size()];
					query.where(list.toArray(a));
				}
				return null;
			}
			
		};
		Sort sort = new Sort(Direction.DESC, "diagnoseTime");
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize(), sort);
		Page<DiagnoseRecord> pages = diagnoseRecordRepository.findAll(sf, pageable);
		PageFinder<DiagnoseRecord> pageFinder = new PageFinder<DiagnoseRecord>(queryVo.getPageNo(),
				queryVo.getPageSize(), (int) pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<DiagnoseRecordVo> data = new ArrayList<>();
			for (DiagnoseRecord diagnoseRecord : pageFinder.getData()) {
				
				DiagnoseRecordVo diagnoseRecordVo = new DiagnoseRecordVo();
				BeanUtils.deepCopy(diagnoseRecord, diagnoseRecordVo);
				String testRecordId = diagnoseRecord.getTestRecordId();
				TestRecord testRecord = testRecordRepository.findByTestRecordId(testRecordId);
				diagnoseRecordVo.setHospitalName(testRecord.getHospitalName());
				data.add(diagnoseRecordVo);
			}
			return new PageFinder<DiagnoseRecordVo>(queryVo.getPageNo(), queryVo.getPageSize(),
					pageFinder.getRowCount(), data);
		}
		return new PageFinder<DiagnoseRecordVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public String add(DiagnoseRecordVo diagnoseRecordVo) throws Exception {
		String testRecordId = diagnoseRecordVo.getTestRecordId();
		DiagnoseRecord findByTestRecordId = diagnoseRecordRepository.findByTestRecordId(testRecordId);
		if (findByTestRecordId != null) {
			findByTestRecordId.setDiseaseLeft(diagnoseRecordVo.getDiseaseLeft());
			findByTestRecordId.setDiseaseRight(diagnoseRecordVo.getDiseaseRight());
			findByTestRecordId.setSuggest(diagnoseRecordVo.getSuggest());
			findByTestRecordId.setResultRemark(diagnoseRecordVo.getResultRemark());
			return findByTestRecordId.getId();
		} else {
			DiagnoseRecord diagnoseRecord = new DiagnoseRecord();
			BeanUtils.deepCopy(diagnoseRecordVo, diagnoseRecord);
			diagnoseRecord.setDiagnoseRecId(CreatorNoUtil.getCode());
			diagnoseRecord.setDiagnoseTime(new Date());
			TestRecord testRecord = testRecordRepository.findByTestRecordId(testRecordId);
			diagnoseRecord.setPatientId(testRecord.getPatientId());
			diagnoseRecord.setPatientName(testRecord.getPatientName() == null ? "" : testRecord.getPatientName());
			testRecord.setDiagnoseState("1");// 确诊状态改完待复核

			diagnoseRecordRepository.save(diagnoseRecord);
			return testRecord.getId();
		}
	}

	@Override
	@Transactional
	public void update(DiagnoseRecordVo diagnoseRecordVo) throws Exception {
		DiagnoseRecord diagnoseRecord = diagnoseRecordRepository.findOne(diagnoseRecordVo.getId());
		String diseaseRight = diagnoseRecordVo.getDiseaseRight();
		String diseaseLeft = diagnoseRecordVo.getDiseaseLeft();
		String resultRemark = diagnoseRecordVo.getResultRemark();
		if (LogicUtil.isNotNullAndEmpty(diseaseRight) && !diseaseRight.equals(diagnoseRecord.getDiseaseRight())) {
			diagnoseRecord.setDiseaseRight(diseaseRight);
		}
		if (LogicUtil.isNotNullAndEmpty(diseaseLeft) && !diseaseLeft.equals(diagnoseRecord.getDiseaseLeft())) {
			diagnoseRecord.setDiseaseLeft(diseaseLeft);
		}
		if (LogicUtil.isNotNullAndEmpty(resultRemark) && !resultRemark.equals(diagnoseRecord.getResultRemark())) {
			diagnoseRecord.setResultRemark(resultRemark);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getDetail(String testRecordId) throws Exception {
		TestRecord testRecord = testRecordRepository.findByTestRecordId(testRecordId);
		DiagnoseRecord diagnoseRecord = diagnoseRecordRepository.findByTestRecordId(testRecordId);
		Map<String, Object> result = new HashMap<>();
		if (testRecord != null) {
			String patientId = testRecord.getPatientId();
			Patient patient = patientRepository.findByPatientId(patientId);
			if (patient != null) {
				result.put("age", patient.getAge());
				result.put("cardNo", patient.getCardNo());
				result.put("patientName", patient.getName());
				result.put("gender", patient.getGender());
			}

			result.put("aiResult", testRecord.getAiResult());
			result.put("deviceId", testRecord.getDeviceId());
			result.put("hospitalName", testRecord.getHospitalName());
			result.put("testPic", testRecord.getTestPic());
			result.put("testTime", testRecord.getTestTime());
		}
		if (diagnoseRecord != null) {
			String doctorId = diagnoseRecord.getDoctorId();
			Doctor doctor = doctorRepository.findByDoctorId(doctorId);
			if (doctor != null) {
				result.put("url", doctor.getUrl());
				result.put("doctorName", doctor.getDoctorName());
			}
			result.put("diagnoseTime", diagnoseRecord.getDiagnoseTime());
			result.put("resultRemark", diagnoseRecord.getResultRemark());
			String suggest = diagnoseRecord.getSuggest();

			if (suggest != null && !suggest.isEmpty()) {
				String[] dis = suggest.split(",");
				String suggests = "";
				if (dis != null && dis.length > 0) {
					for (String string : dis) {
						DiseaseTypeSuggest diseaseTypeSuggest = diseaseTypeSuggestRepository.findBySuggestId(string);
						if (diseaseTypeSuggest != null) {
							suggests = suggests + diseaseTypeSuggest.getSuggestContent();
							suggests = suggests + ",";
						}
					}
					if (suggests.endsWith(",")) {
						suggests = suggests.substring(0, suggests.length() - 1);
					}
				}
				result.put("suggest", suggest);
				result.put("suggests", suggests);

			}
			String diseaseLeft = diagnoseRecord.getDiseaseLeft();
			if (diseaseLeft != null && !diseaseLeft.isEmpty()) {
				String[] dis = diseaseLeft.split(",");
				String diseases = "";
				if (dis != null && dis.length > 0) {
					for (String string : dis) {
						if (string.contains("#")) {
							DiseaseType diseaseType = diseaseTypeRepository.findByDiseaseTypeId(string.split("#")[0]);
							if (diseaseType != null) {
								diseases = diseases + diseaseType.getDiseaseTypeName() + string.split("#")[1] + "级";
								diseases = diseases + ",";
							}
						} else {
							DiseaseType diseaseType = diseaseTypeRepository.findByDiseaseTypeId(string);
							if (diseaseType != null) {
								diseases = diseases + diseaseType.getDiseaseTypeName();
								diseases = diseases + ",";
							}
						}
					}
					if (diseases.endsWith(",")) {
						diseases = diseases.substring(0, diseases.length() - 1);
					}

				}
				result.put("diseaseIdLeft", diseaseLeft);
				result.put("diseaseLeft", diseases);
			}
			String diseaseRight = diagnoseRecord.getDiseaseRight();
			if (diseaseRight != null && !diseaseRight.isEmpty()) {
				String[] dis = diseaseRight.split(",");
				String diseases = "";
				if (dis != null && dis.length > 0) {
					for (String string : dis) {
						if (string.contains("#")) {
							DiseaseType diseaseType = diseaseTypeRepository.findByDiseaseTypeId(string.split("#")[0]);
							if (diseaseType != null) {
								diseases = diseases + diseaseType.getDiseaseTypeName() + string.split("#")[1] + "级";
								diseases = diseases + ",";
							}
						} else {
							DiseaseType diseaseType = diseaseTypeRepository.findByDiseaseTypeId(string);
							if (diseaseType != null) {
								diseases = diseases + diseaseType.getDiseaseTypeName();
								diseases = diseases + ",";
							}
						}
					}
					if (diseases.endsWith(",")) {
						diseases = diseases.substring(0, diseases.length() - 1);
					}

				}
				result.put("diseaseIdRight", diseaseRight);
				result.put("diseaseRight", diseases);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public PageFinder<Map<String, Object>> pagedDiagnoseRecordReports(DiagnoseRecordVo queryVo) throws Exception {
		String doctorName = queryVo.getDoctorName();
		String hospitalName = queryVo.getHospitalName();
		String startTime = queryVo.getStartTime();
		String endTime = queryVo.getEndTime();
		String aiResult = queryVo.getAiResult();
		String diagnoseResult = queryVo.getDiagnoseResult();
		int pageNo = queryVo.getPageNo();
		int pageSize = queryVo.getPageSize();
		String sql = "select tdr.doctor_name doctorName,tdr.result_remark remark,ttr.patient_name patientName,diagnose_time diagnoseTime,\n"
				+ "ttr.hospital_name hospitalName,tdr.disease_left diseaseLeft,tdr.disease_right diseaseRight,\n"
				+ "tdr.suggest suggestm,ttr.ai_result aiResult,ttr.code code "
				+ " from tbl_test_rec ttr left join tbl_diagnose_rec tdr on tdr.TEST_REC_ID = ttr.TEST_REC_ID where 1=1 ";
		if (StringUtils.isNotBlank(doctorName)) {
			sql += "and tdr.doctor_name like :doctorName ";
		}
		if (StringUtils.isNotBlank(hospitalName)) {
			sql += "and ttr.hospital_name like :hospitalName ";
		}
		if (StringUtils.isNotBlank(aiResult)) {
			if(aiResult.equals("1")) {//未诊断
				sql += " and ttr.ai_result is null ";
			}else if(aiResult.equals("2")) {//无有效图像
				sql += " and ttr.ai_result = '图像不合格' ";
			}else if(aiResult.equals("3")) {//阴性
				sql += " and (ttr.ai_result like '%右眼无明显糖网病变%' and ttr.ai_result like '%左眼无明显糖网病变%' ) ";
			}else if(aiResult.equals("4")) {//阳性
				sql += " and ttr.ai_result like '%PDR%' ";
			}
		}
		if (StringUtils.isNotBlank(diagnoseResult)) {
			 if(diagnoseResult.equals("1")) {//未诊断
				sql += " and ttr.diagnose_state!='2'";
			}else if(diagnoseResult.equals("2")) {//无有效图像
				sql += " and (tdr.disease_left like '%#5' and tdr.disease_left like '%#5')";
			}else if(diagnoseResult.equals("3")) {//阴性
				sql += " and (tdr.disease_left like '%#0' and tdr.disease_right like '%#0' ) ";
			}else if(diagnoseResult.equals("4")) {//阳性
				sql += " and (ttr.diagnose_state=2 and ((tdr.disease_left not like '%#0' and tdr.disease_left not like '%#5') or (tdr.disease_right not like '%#0' and tdr.disease_right not like '%#5'))) ";
			}
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			sql += "and tdr.diagnose_time between :startTime and :endTime ";
		} else if (!StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			sql += "and tdr.diagnose_time > :startTime";
		} else if (StringUtils.isNotBlank(startTime) && !StringUtils.isNotBlank(endTime)) {
			sql += "and tdr.diagnose_time < :endTime ";
		}
		sql += " order by diagnose_time desc";

		String csql = "select count(*) from  (" + sql + ") p";

		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query queryCount = entityManager.createNativeQuery(csql);
		if (StringUtils.isNotBlank(doctorName)) {
			query.setParameter("doctorName", "%" + doctorName + "%");
			queryCount.setParameter("doctorName", "%" + doctorName + "%");
		}
		if (StringUtils.isNotBlank(hospitalName)) {
			query.setParameter("hospitalName", "%" + hospitalName + "%");
			queryCount.setParameter("hospitalName", "%" + hospitalName + "%");
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			query.setParameter("startTime", startTime);
			queryCount.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
			queryCount.setParameter("endTime", endTime);
		} else if (!StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			query.setParameter("endTime", endTime);
			queryCount.setParameter("endTime", endTime);
		} else if (StringUtils.isNotBlank(startTime) && !StringUtils.isNotBlank(endTime)) {
			query.setParameter("startTime", startTime);
			queryCount.setParameter("startTime", startTime);
		}
		BigInteger totalRows = (BigInteger) queryCount.getSingleResult();
		if (totalRows.intValue() == 0) {
			return new PageFinder<Map<String, Object>>(pageNo, pageSize, 0);
		}
		PageFinder<Map<String, Object>> pageFinder = new PageFinder<Map<String, Object>>(pageNo, pageSize,
				totalRows.intValue());
		query.setMaxResults(pageFinder.getPageSize());
		query.setFirstResult(pageFinder.getStartOfPage());
		pageFinder.setData(query.getResultList());
		return pageFinder;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getAiStatisticData(String startTime,String endTime) throws Exception {
		String condition="";
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			condition += "and ttr.test_time between :startTime and :endTime ";
		} else if (!StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			condition += "and ttr.test_time > :startTime";
		} else if (StringUtils.isNotBlank(startTime) && !StringUtils.isNotBlank(endTime)) {
			condition += "and ttr.test_time < :endTime ";
		}
		String sql = "select  SUM(case(ai_result_description ='2') when 1 then 1 else 0 end) negativeCount," + //阴性
				" SUM(case(ai_result_description ='1') when 1 then 1 else 0 end) positiveCount," + //阳性
				" SUM(case(ai_result_description is null or ai_result_description='0') when 1 then 1 else 0 end) notDiagnoseCount," +//未诊断 
				" SUM(case(ai_result_description ='3') when 1 then 1 else 0 end) invalidCount," + //不合格
				" count(*) totalCount,hospital_id hospitalId from tbl_test_rec ttr where 1=1 "+condition+" group by hospital_id";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
		} else if (!StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			query.setParameter("endTime", endTime);
		} else if (StringUtils.isNotBlank(startTime) && !StringUtils.isNotBlank(endTime)) {
			query.setParameter("startTime", startTime);
		}
		List<Map<String, Object>> list = query.getResultList();
		if(list!=null&&!list.isEmpty()) {
			for (Map<String, Object> map : list) {
				String hospitalId = String.valueOf(map.get("hospitalId")==null?"":map.get("hospitalId"));
				Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
				map.put("hospitalName", hospital.getHospitalName());
			}
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getDiagnoseStatisticData(String startTime,String endTime) throws Exception {
		String condition="";
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			condition += " and ttr.test_time between :startTime and :endTime ";
		} else if (!StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			condition += " and ttr.test_time > :startTime ";
		} else if (StringUtils.isNotBlank(startTime) && !StringUtils.isNotBlank(endTime)) {
			condition += " and ttr.test_time < :endTime ";
		}
		String sql = "select  SUM(case(ttr.diagnose_state!='2') when 1 then 1 else 0 end) notDiagnoseCount," + 
				" SUM(case(tdr.disease_left like '%#5' and tdr.disease_left like '%#5') when 1 then 1 else 0 end) invalidCount," + 
				" SUM(case(tdr.disease_left like '%#0' and tdr.disease_right like '%#0' ) when 1 then 1 else 0 end) negativeCount," + 
				" SUM(case(tdr.disease_left not like '%#0' or tdr.disease_right not like '%#0') when 1 then 1 else 0 end) positiveCount," + 
				" count(*) totalCount,hospital_id hospitalId  from " + 
				" tbl_test_rec ttr left  join tbl_diagnose_rec tdr on ttr.test_rec_id = tdr.test_rec_id where 1=1 " +condition+ 
				" group by hospital_id";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
		} else if (!StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			query.setParameter("endTime", endTime);
		} else if (StringUtils.isNotBlank(startTime) && !StringUtils.isNotBlank(endTime)) {
			query.setParameter("startTime", startTime);
		}
		List<Map<String, Object>> list = query.getResultList();
		if(list!=null&&!list.isEmpty()) {
			for (Map<String, Object> map : list) {
				String hospitalId = String.valueOf(map.get("hospitalId")==null?"":map.get("hospitalId"));
				Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
				map.put("hospitalName", hospital.getHospitalName());
			}
		}
		return list;
	}
}
