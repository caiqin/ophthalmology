package com.uniclans.ophthalmology.modules.basedata.service.component.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.uniclans.ophthalmology.basecomponent.upload.component.IContentPublisher;
import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.CreatorNoUtil;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.basecomponent.utils.MatrixToImageWriter;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Doctor;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Hospital;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Patient;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDoctorRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IHospitalRepository;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IPatientRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IPatientService;
import com.uniclans.ophthalmology.modules.basedata.service.model.PatientVo;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.ITestRecordRepository;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemUser;
import com.uniclans.ophthalmology.modules.permission.dao.repository.ISystemUserRepository;

@Component
public class PatientServiceImpl implements IPatientService {
	private static Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
	@Resource
	private EntityManager entityManager;
	@Resource
	private IContentPublisher contentPublisher;
	@Value(value="${qrcode.url}")
	private String qrUrl;
	@Resource
	private ISystemUserRepository userRepository;
	@Resource
	private ITestRecordRepository testRecordRepository;
	@Resource
	private IPatientRepository patientRepository;
	@Resource
	private IHospitalRepository hospitalRepository;
	@Resource
	private IDoctorRepository doctorRepository;
	
	@Override
	@Transactional(readOnly=true)
	public PageFinder<PatientVo> pagedPatients(PatientVo queryVo,String userNo) throws Exception {
		SystemUser systemUser = userRepository.findByUserNo(userNo);
		String doctorId = systemUser.getDoctorId();
		String loginName = systemUser.getLoginName();
		
		
		final String name = StringUtils.parseStrNull(queryVo.getName());
		final String mobileNo = StringUtils.parseStrNull(queryVo.getMobileNo());
		Specification<Patient> sf = new Specification<Patient>() {
			@Override
			public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder cb){
				Predicate patientIdPre = null;
				Predicate creatorPre = null;
				if(!loginName.equals("admin")) {
					if (!userNo.equals("")) {
						// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
						creatorPre = cb.equal(root.<String>get("creator"), userNo);
					}
					if(doctorId!=null&&!doctorId.equals("")) {
						Doctor doctor = null;
						try {
							doctor = doctorRepository.findByDoctorId(doctorId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(doctor!=null){
							String hospitalId = doctor.getHospitalId();
							List<String> hospitalIds = new ArrayList<>();
							hospitalIds.add(hospitalId);
							List<Hospital> hospitals = hospitalRepository.findByParentNo(hospitalId);
							if(hospitals!=null&&!hospitals.isEmpty()) {
								for (Hospital hospital : hospitals) {
									hospitalIds.add(hospital.getHospitalId());
								}
							}
							
//							List<TestRecord> testRecord = testRecordRepository.findByHospitalIdIn(hospitalIds);
							
							if(hospitalIds!=null&&!hospitalIds.isEmpty()) {
								Expression<String> exp = root.get("hospitalId");
								patientIdPre = exp.in(hospitalIds);
							}
						}
					}
				}
				
				Predicate patientNameLike = null;
				if (!name.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					patientNameLike = cb.like(root.<String>get("name"), "%" + name + "%");
				}
				Predicate mobileNoLike = null;
				if (!mobileNo.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					mobileNoLike = cb.like(root.<String>get("mobileNo"), "%" + mobileNo + "%");
				}
				if (null != creatorPre&&null != patientIdPre) {
					query.where(cb.or(creatorPre,patientIdPre));
				}else if(null != creatorPre&&null == patientIdPre) {
					query.where(cb.and(creatorPre));
				}else if(null == creatorPre&&null != patientIdPre) {
					query.where(cb.and(patientIdPre));
				}
				if (null != patientNameLike) {
					query.where(cb.and(patientNameLike));
				}
				if (null != mobileNoLike) {
					query.where(cb.and(mobileNoLike));
				}
				return null;
			}
		};
		Sort sort = new Sort(Direction.DESC, "createTime");
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize(),sort);
		Page<Patient> pages = patientRepository.findAll(sf, pageable);		
		PageFinder<Patient> pageFinder = new PageFinder<Patient>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<PatientVo> data = new ArrayList<>();
			for (Patient patient : pageFinder.getData()) {
				PatientVo patientVo = new PatientVo();
				BeanUtils.deepCopy(patient, patientVo);
				data.add(patientVo);
			}
			return new PageFinder<PatientVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<PatientVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public void addPatient(PatientVo patientVo) throws Exception {
		Patient patient = new Patient();
		BeanUtils.deepCopy(patientVo, patient);
		String patientId = CreatorNoUtil.getCode();
		patient.setPatientId(patientId);
		patient.setCodeUrl(createQrCode(patientId));
		patient.setCreateTime(new Date());
		patientRepository.save(patient);
		
	}
	private String createQrCode(String patientId) throws WriterException, IOException {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		for(int i = 0 ; i < 5; i++){
			builder.append(random.nextInt(10));
		}
		
		String code = builder.toString();
		String text = patientId; // 二维码内容  
		int width = 423; // 二维码图片宽度  
		int height = 423; // 二维码图片高度  
		String format = "jpg";// 二维码的图片格式  
		  
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码  
		  
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);  
		String realPath = "/ophthalmology-api/code/"+DateUtils.getCurrentYear()+""+DateUtils.getCurrentMonth()+"/";

		String absolutePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		absolutePath = absolutePath.replaceAll("target/classes/", "");
		String filePath = absolutePath + realPath;
		// 生成二维码  
		File path = new File(filePath);
		if(!path.exists()){
			path.mkdirs();
		}
		String fileName = patientId+"qrcode.jpg";
		File outputFile = new File(filePath + File.separator + fileName);  
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);  

		File f1 = new File(filePath);

		if (!f1.exists()) {
			f1.mkdirs();
		}
		// 发布图片至图片服务器
		try {
			List<String> imgFiles = new ArrayList<String>();
			imgFiles.add("/" + realPath + patientId+"qrcode.jpg");
			contentPublisher.asynchronousPublish(absolutePath, imgFiles);
		} catch (Exception e) {
			logger.error("上传图片服务器异常：" + e.getMessage(), e);
		}
		String url = realPath.replace("\\", "/") + fileName;
		logger.info("url:"+url);
		
		return url;
		
	}
	@Override
	@Transactional
	public void updatePatient(PatientVo patientVo) throws Exception {
		Patient patient = patientRepository.findOne(patientVo.getId());
		String name = patientVo.getName();
		String gender = patientVo.getGender();
		int age = patientVo.getAge();
		String cardNo = patientVo.getCardNo();
		String mobileNo = patientVo.getMobileNo();
		String cardAddress = patientVo.getCardAddress();
		String realAddress = patientVo.getRealAddress();
		String hospitalId = patientVo.getHospitalId();
		String hospitalName = patientVo.getHospitalName();
		if(LogicUtil.isNotNullAndEmpty(name)&&!name.equals(patient.getName())) {
			patient.setName(name);
		}
		if(LogicUtil.isNotNullAndEmpty(gender)&&!gender.equals(patient.getGender())) {
			patient.setGender(gender);
		}
		if(age!=0&&age!=patient.getAge()) {
			patient.setAge(age);
		}
		if(LogicUtil.isNotNullAndEmpty(cardNo)&&!cardNo.equals(patient.getCardNo())) {
			patient.setCardNo(cardNo);
		}
		if(LogicUtil.isNotNullAndEmpty(mobileNo)&&!mobileNo.equals(patient.getMobileNo())) {
			patient.setMobileNo(mobileNo);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalId)&&!hospitalId.equals(patient.getHospitalId())) {
			patient.setHospitalId(hospitalId);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalName)&&!hospitalName.equals(patient.getHospitalName())) {
			patient.setHospitalName(hospitalName);
		}
		if(LogicUtil.isNotNullAndEmpty(cardAddress)&&!cardAddress.equals(patient.getCardAddress())) {
			patient.setCardAddress(cardAddress);
		}
		if(LogicUtil.isNotNullAndEmpty(realAddress)&&!realAddress.equals(patient.getRealAddress())) {
			patient.setRealAddress(realAddress);
		}
		patient.setUpdateTime(new Date());
	}

}
