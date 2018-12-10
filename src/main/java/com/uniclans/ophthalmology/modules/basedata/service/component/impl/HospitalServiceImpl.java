package com.uniclans.ophthalmology.modules.basedata.service.component.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.basecomponent.utils.MatrixToImageWriter;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.Hospital;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IHospitalRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IHospitalService;
import com.uniclans.ophthalmology.modules.basedata.service.model.HospitalVo;
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.DiagnoseRecord;
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.TestRecord;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.ITestRecordRepository;

@Component
public class HospitalServiceImpl implements IHospitalService {
	@Resource
	private ITestRecordRepository testRecordRepository;
	@Resource
	private IContentPublisher contentPublisher;
	
	private static Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

	@Value(value="${qrcode.url}")
	private String qrUrl;
	@Resource
	private IHospitalRepository hospitalRepository;
	
	@Override
	@Transactional(readOnly=true)
	public PageFinder<HospitalVo> pagedHospitals(HospitalVo queryVo) throws Exception {
		final String name = StringUtils.parseStrNull(queryVo.getHospitalName());
		Specification<Hospital> sf = new Specification<Hospital>() {
			@Override
			public Predicate toPredicate(Root<Hospital> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate hospoitalNameLike = null;
				if (!name.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					hospoitalNameLike = cb.like(root.<String>get("hospitalName"), "%" + name + "%");
				}
				if (null != hospoitalNameLike) {
					query.where(cb.and(hospoitalNameLike));
				}
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<Hospital> pages = hospitalRepository.findAll(sf, pageable);		
		PageFinder<Hospital> pageFinder = new PageFinder<Hospital>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<HospitalVo> data = new ArrayList<>();
			for (Hospital hospital : pageFinder.getData()) {
				HospitalVo hospitalVo = new HospitalVo();
				BeanUtils.deepCopy(hospital, hospitalVo);
				data.add(hospitalVo);
			}
			return new PageFinder<HospitalVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<HospitalVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public void addHospital(HospitalVo hospitalVo) throws Exception {
		Hospital hospital = new Hospital();
		BeanUtils.deepCopy(hospitalVo, hospital);
		hospital.setHospitalId(CreatorNoUtil.getCode());
		hospitalRepository.save(hospital);
	}
	@Override
	@Transactional
	public void updateHospital(HospitalVo hospitalVo) throws Exception {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalVo.getHospitalId());
		String hospitalAddress = hospitalVo.getHospitalAddress();
		String hospitalName = hospitalVo.getHospitalName();
		String hospitalIntroduce = hospitalVo.getHospitalIntroduce();
		int level = hospitalVo.getLevel();
		String parentNo = hospitalVo.getParentNo();
		if(level!=hospital.getLevel()) {
			hospital.setLevel(level);
		}
		if(LogicUtil.isNotNullAndEmpty(parentNo)&&!parentNo.equals(hospital.getParentNo())) {
			hospital.setParentNo(parentNo);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalName)&&!hospitalName.equals(hospital.getHospitalName())) {
			hospital.setHospitalName(hospitalName);
			String hospitalId = hospital.getHospitalId();
			List<TestRecord> list = testRecordRepository.findByHospitalId(hospitalId);
			if(list!=null&&!list.isEmpty()) {
				for (TestRecord diagnoseRecord : list) {
					diagnoseRecord.setHospitalName(hospitalName);
				}
			}
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalAddress)&&!hospitalAddress.equals(hospital.getHospitalAddress())) {
			hospital.setHospitalAddress(hospitalAddress);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalIntroduce)&&!hospitalIntroduce.equals(hospital.getHospitalIntroduce())) {
			hospital.setHospitalIntroduce(hospitalIntroduce);
		}

	}

	@Override
	@Transactional(readOnly=true)
	public List<HospitalVo> getAll() throws Exception {
		List<Hospital> poList = hospitalRepository.findAll();
		List<HospitalVo> result = new ArrayList<>();
		if(poList!=null&&!poList.isEmpty()) {
			for (Hospital hospital : poList) {
				HospitalVo hospitalVo = new HospitalVo();
				BeanUtils.deepCopy(hospital, hospitalVo);
				result.add(hospitalVo);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public HospitalVo getHospital(String hospitalId) throws Exception {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		HospitalVo hospitalVo = new HospitalVo();
		BeanUtils.deepCopy(hospital, hospitalVo);
		if(hospital.getParentNo()!=null&&!hospital.getParentNo().isEmpty()&&hospital.getLevel()!=1) {
			hospitalVo.setParentName(hospitalRepository.findByHospitalId(hospital.getParentNo()).getHospitalName());
		}
		return hospitalVo;
	}
	@Override
	@Transactional
	public void createQrCode(HospitalVo hospitalVo) throws WriterException, IOException {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		for(int i = 0 ; i < 5; i++){
			builder.append(random.nextInt(10));
		}
		
		String code = builder.toString();
		String text = qrUrl+"?hospitalId="+hospitalVo.getHospitalId()+"&code="+code; // 二维码内容  
		int width = 423; // 二维码图片宽度  
		int height = 423; // 二维码图片高度  
		String format = "jpg";// 二维码的图片格式  
		  
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码  
		  
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);  
		String realPath = "/ophthalmology/hospital/qrcode/";

		String absolutePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		absolutePath = absolutePath.replaceAll("target/classes/", "");
		String filePath = absolutePath + realPath;
		// 生成二维码  
		File path = new File(filePath);
		if(!path.exists()){
			path.mkdirs();
		}
		String fileName = hospitalVo.getHospitalId()+"qrcode.jpg";
		File outputFile = new File(filePath + File.separator + fileName);  
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);  

		File f1 = new File(filePath);

		if (!f1.exists()) {
			f1.mkdirs();
		}
		// 发布图片至图片服务器
		try {
			List<String> imgFiles = new ArrayList<String>();
			imgFiles.add("/" + realPath + hospitalVo.getHospitalId()+"qrcode.jpg");
			contentPublisher.asynchronousPublish(absolutePath, imgFiles);
		} catch (Exception e) {
			logger.error("上传图片服务器异常：" + e.getMessage(), e);
		}
		String url = File.separator+realPath.replace("\\", "/") + fileName;
		logger.info("url:"+url);
		
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalVo.getHospitalId());
		if(hospital!=null) {
			hospital.setUrl(url);
			hospital.setCode(code);
			hospital.setStatus("0");//默认启用状态
		}
		
	}
	@Transactional
	@Override
	public void enableQr(HospitalVo hospitalVo) throws Exception {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalVo.getHospitalId());
		if(hospital!=null) {
			if(hospital.getStatus()!=null&&hospital.getStatus().equals("0")) {
				hospital.setStatus("1");//默认启用状态
			}else {
				hospital.setStatus("0");//默认启用状态
			}
		}
	}
}
