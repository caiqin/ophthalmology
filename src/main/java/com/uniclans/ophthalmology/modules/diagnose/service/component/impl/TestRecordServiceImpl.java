package com.uniclans.ophthalmology.modules.diagnose.service.component.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tencent.aimis.Utils;
import com.tencent.aimis.vo.AiResultData;
import com.tencent.aimis.vo.GetAiResultRsp;
import com.tencent.aimis.vo.ImagesInfo;
import com.tencent.aimis.vo.StudyUploadReq;
import com.tencent.aimis.vo.StudyUploadRsp;
import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.CreatorNoUtil;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Doctor;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Patient;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDoctorRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IHospitalRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IPatientRepository;
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.Device;
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.TestRecord;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.IDeviceRepository;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.ITestRecordRepository;
import com.uniclans.ophthalmology.modules.diagnose.service.component.ITestRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.TestRecordVo;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemUser;
import com.uniclans.ophthalmology.modules.permission.dao.repository.ISystemUserRepository;

@Component
public class TestRecordServiceImpl implements ITestRecordService {
	private static Logger logger = LoggerFactory.getLogger(TestRecordServiceImpl.class);
	@Resource
	private ITestRecordRepository testRecordRepository;
	@Resource
	private IPatientRepository patientRepository;
	@Resource
	private IHospitalRepository hospitalRepository;
	@Resource
	private IDeviceRepository deviceRepository;
	@Resource
	private ISystemUserRepository userRepository;
	@Resource
	private IDoctorRepository doctorRepository;
	@Resource
	private EntityManager entityManager;	
	@Resource
	private IAimisClient ai;
	
	@Override
	@Transactional
	public PageFinder<TestRecordVo> pagedTestRecords(TestRecordVo queryVo,String userNo) throws Exception {
		SystemUser systemUser = userRepository.findByUserNo(userNo);
		String doctorId = systemUser.getDoctorId();
		String loginName = systemUser.getLoginName();
		
		
		
		final String aiResult = StringUtils.parseStrNull(queryVo.getAiResult());
		final String hospitalName = StringUtils.parseStrNull(queryVo.getHospitalName());
		final String patientName = StringUtils.parseStrNull(queryVo.getPatientName());
		final Date startTime = queryVo.getStartTime();
		final Date endTime = queryVo.getEndTime();
		final String diagnoseState = queryVo.getDiagnoseState();
		Specification<TestRecord> sf = new Specification<TestRecord>() {
			@Override
			public Predicate toPredicate(Root<TestRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate hospitalNamelike = null;
				Predicate patientIdEqual = null;
				Predicate aiResultNotEmpty = null;
				List<Predicate> list = new ArrayList<>();
				if (!aiResult.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					aiResultNotEmpty = cb.isNotNull(root.<String>get("aiResult"));
				}
				if (!hospitalName.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					hospitalNamelike = cb.like(root.<String>get("hospitalName"), "%" + hospitalName + "%");
				}
				Predicate diagnoseStateEqual = null;
				if (!diagnoseState.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					diagnoseStateEqual = cb.equal(root.<String>get("diagnoseState"),  diagnoseState );
				}
				Predicate hospitalIdPre = null;
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
							hospitalIdPre = cb.equal(root.<String>get("hospitalId"), hospitalId);
							List<Patient> patients = patientRepository.findByHospitalId(hospitalId);
							List<String> patientIds = new ArrayList<>();
							if(patients!=null&&!patients.isEmpty()) {
								for (Patient patient : patients) {
									patientIds.add(patient.getPatientId());
								}
								patientIdEqual = root.<String>get("patientId").in(patientIds);
							}
						}
						
					}
				}
				Predicate patientNamelike = null;
				if (!patientName.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					patientNamelike = cb.like(root.<String>get("patientName"), "%" + patientName + "%");
				}
				Predicate timePredicate = null;
				if (startTime != null && endTime == null) {
					timePredicate = cb.greaterThanOrEqualTo(root.<String>get("testTime").as(String.class), DateUtils.formatTime(startTime));
				}else if(endTime != null && startTime == null) {
					timePredicate = cb.lessThanOrEqualTo(root.<String>get("testTime").as(String.class), DateUtils.formatTime(endTime));
				}else if(startTime != null && endTime != null ) {
					timePredicate = cb.between(root.<String>get("testTime").as(String.class), DateUtils.formatTime(startTime), DateUtils.formatTime(endTime));
				}
				
				
				
				if(aiResultNotEmpty!=null) {
					list.add(aiResultNotEmpty);
				}
				if(timePredicate!=null) {
					list.add(aiResultNotEmpty);
				}
				if(diagnoseStateEqual!=null) {
					list.add(diagnoseStateEqual);
				}
				if(hospitalIdPre!=null&&patientIdEqual!=null) {
					list.add(cb.or(hospitalIdPre,patientIdEqual));
				}else if(hospitalIdPre!=null&&patientIdEqual==null) {
					list.add(hospitalIdPre);
				}else if(hospitalIdPre==null&&patientIdEqual!=null) {
					list.add(patientIdEqual);
				}
				
				if (null != hospitalNamelike&&null == patientNamelike) {
					if(patientIdEqual!=null) {
						list.add(cb.and(hospitalNamelike,patientIdEqual));
					}else { 
						list.add(hospitalNamelike);
					}
				}else if (null == hospitalNamelike&&null != patientNamelike) {
					if(patientIdEqual!=null) {
						list.add(cb.and(patientNamelike,patientIdEqual));
					}else {
						list.add(patientNamelike);
					}
				}else if(null != hospitalNamelike&&null != patientNamelike) {
					if(patientIdEqual!=null) {
						list.add(cb.and(hospitalNamelike,patientNamelike,patientIdEqual));
					}else {
						list.add(cb.and(hospitalNamelike,patientNamelike));
					}
				}
				
				if(list!=null) {
					Predicate[] a = new Predicate[list.size()];
					query.where(list.toArray(a));
				}
				return null;
			
			}
		};
		Sort sort = new Sort(Direction.ASC, "diagnoseState");
		sort.and(new Sort(Direction.DESC, "testTime"));
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize(),sort);
		Page<TestRecord> pages = testRecordRepository.findAll(sf, pageable);		
		PageFinder<TestRecord> pageFinder = new PageFinder<TestRecord>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<TestRecordVo> data = new ArrayList<TestRecordVo>();
			for (TestRecord testRecord : pageFinder.getData()) {
				String name = testRecord.getPatientName();
				if(name==null||name.isEmpty()) {
					Patient patient = patientRepository.findByPatientId(testRecord.getPatientId());
					testRecord.setPatientName(patient.getName());
				}
				TestRecordVo testRecordVo = new TestRecordVo();
				BeanUtils.deepCopy(testRecord, testRecordVo);
				
				
				data.add(testRecordVo);
			}
			return new PageFinder<TestRecordVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<TestRecordVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}


	@Override
	@Transactional
	public void addTestRecord(TestRecordVo vo) throws Exception {
		String deviceId = vo.getDeviceId();
		Device device = deviceRepository.findByDeviceId(deviceId);
		vo.setDiagnoseState("0");
		vo.setHospitalId(device.getHospitalId());
		vo.setHospitalName(device.getHospitalName());
		TestRecord testRecord = new TestRecord();
		BeanUtils.deepCopy(vo, testRecord);
		testRecord.setTestRecordId(CreatorNoUtil.getCode());
		Patient patient = patientRepository.findByPatientId(vo.getPatientId());
		testRecord.setPatientName(patient.getName());
		testRecord.setTestTime(new Date());
		patient.setHospitalId(device.getHospitalId());
		patient.setHospitalName(device.getHospitalName());
		testRecordRepository.save(testRecord);
	}


	@Transactional
	public void sendImg2Ai(String testRecordId,String osUrl,String odUrl) throws Exception {
		TestRecord testRecord = testRecordRepository.findByTestRecordId(testRecordId);
		
		
		Patient patient=patientRepository.findByPatientId(testRecord.getPatientId());
		String wechatId = patient.getWechatId();
		if(wechatId!=null&&!wechatId.equals("")) {//非手动添加的用户不调AI
			return ;
		}
		StudyUploadReq req = new StudyUploadReq();
		req.setPatientId(patient.getPatientId());
		//非空,患者姓名
		req.setPatientName(patient.getName());
		//非空,患者性别（0未知 1 男 2 女 3 其他）
		req.setPatientGender(patient.getGender().equals("0")?"1":"2");
		//非空,患者生日，格式2017-08-22
		req.setPatientBirthday(DateUtils.formatDate(patient.getBirthDay()));
		//非空,本次检查在医院侧的序列号
		req.setStudyId(testRecord.getTestRecordId());
		ImagesInfo leftEye=new ImagesInfo();

		ImagesInfo rightEye=new ImagesInfo();
		String rightEyeImg="";
		String leftEyeImg="";
		rightEyeImg=odUrl;
		rightEye.setDescPosition("1");
		rightEye.setContent(Utils.getImageBase64("D:\\data\\"+rightEyeImg));
		leftEyeImg=osUrl;
		leftEye.setDescPosition("2");
		leftEye.setContent(Utils.getImageBase64("D:\\data\\"+leftEyeImg));
		
		StudyUploadRsp studyUpload = ai.studyUpload(req, leftEye, rightEye);
		
		logger.info("上传之后的AI结果===="+JsonUtils.toJson(studyUpload));
		if(studyUpload.getCode()==0){
			GetAiResultRsp rsp=ai.getAiResult(testRecord.getTestRecordId());
			logger.info("查询的AI结果===="+JsonUtils.toJson(rsp));
			if(rsp.getCode()==0){
				AiResultData data=rsp.getData();
				if(data.getResult()==1){/**返回 值  -5、-4、-3、-2、-1、1 描述未收到图 像图像不合 格待处理 处理失败 处理中 处理完成*/
					String desc = StringUtils.parseStrNull(data.getDesc());
					testRecord.setAiResult(desc);
					if(desc.contains("PDR")) {
						testRecord.setAiResultDescription("1");//阳性
					}else if((desc.contains("右眼无明显糖网病变")&&desc.contains("左眼无明显糖网病变"))||
							(desc.contains("右眼无明显糖网病变")&&desc.contains("左眼无有效图像信息"))||
							(desc.contains("左眼无明显糖网病变")&&desc.contains("右眼无有效图像信息"))) {
						testRecord.setAiResultDescription("2");//阴性
					}
				}else if(data.getResult()==-4){/**返回 值  -5、-4、-3、-2、-1、1 描述未收到图 像图像不合 格待处理 处理失败 处理中 处理完成*/
					testRecord.setAiResult("图像不合格");//
					testRecord.setAiResultDescription("3");//图像不合格
				}
				
			}
		}
	}
	@Override
	@Transactional
	public void updateState() throws Exception {
		String sql = "select ttr.* from tbl_test_rec ttr left join tbl_diagnose_rec tdr on ttr.test_rec_id = tdr.test_rec_id " + 
		"where ttr.diagnose_state=:diagnoseState and tdr.diagnose_time<:diagnoseTime";
		Query query = entityManager.createNativeQuery(sql,TestRecord.class);
		query.setParameter("diagnoseState", "1");
		Date date = new Date(System.currentTimeMillis()-(5*60*1000));//
		query.setParameter("diagnoseTime", date);
		List<TestRecord> list = query.getResultList();
		if(list!=null&&!list.isEmpty()) {
			for (TestRecord testRecord : list) {
				testRecord.setDiagnoseState("2");//改为已复核
			}
		}
	}

}
