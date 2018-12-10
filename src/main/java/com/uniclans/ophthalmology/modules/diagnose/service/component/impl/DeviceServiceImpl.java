package com.uniclans.ophthalmology.modules.diagnose.service.component.impl;

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
import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.Device;
import com.uniclans.ophthalmology.modules.diagnose.dao.repository.IDeviceRepository;
import com.uniclans.ophthalmology.modules.diagnose.service.component.IDeviceService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DeviceVo;

@Component
public class DeviceServiceImpl implements IDeviceService {
	@Resource
	private IDeviceRepository deviceRepository;
	@Override
	@Transactional(readOnly=true)
	public PageFinder<DeviceVo> pagedDevices(DeviceVo queryVo) throws Exception {
		final String deviceId = StringUtils.parseStrNull(queryVo.getDeviceId());
		Specification<Device> sf = new Specification<Device>() {
			@Override
			public Predicate toPredicate(Root<Device> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate doctorNameLike = null;
				if (!deviceId.equals("")) {
					// 这里也可以root.get("deviceId").as(String.class)这种方式来强转泛型类型
					doctorNameLike = cb.equal(root.<String>get("deviceId"),  deviceId );
				}
				if (null != doctorNameLike) {
					query.where(cb.and(doctorNameLike));
				}
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<Device> pages = deviceRepository.findAll(sf, pageable);		
		PageFinder<Device> pageFinder = new PageFinder<Device>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<DeviceVo> data = new ArrayList<>();
			for (Device device : pageFinder.getData()) {
				DeviceVo deviceVo = new DeviceVo();
				BeanUtils.deepCopy(device, deviceVo);
				data.add(deviceVo);
			}
			return new PageFinder<DeviceVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<DeviceVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}
	@Transactional
	@Override
	public void addDevice(DeviceVo deviceVo) throws Exception {
		Device device = new Device();
		BeanUtils.deepCopy(deviceVo, device);
		device.setDeviceId(CreatorNoUtil.getCode());
		deviceRepository.save(device);
	}

	@Override
	@Transactional
	public void updateDevice(DeviceVo deviceVo) throws Exception {
		Device device = deviceRepository.findOne(deviceVo.getId());
//		String deviceId = deviceVo.getDeviceId();
		String deviceName = deviceVo.getDeviceName();
		String deviceBrand = deviceVo.getDeviceBrand();
		String deviceMod = deviceVo.getDeviceMod();
		String hospitalId = deviceVo.getHospitalId();
		String hospitalName = deviceVo.getHospitalName();
		if(LogicUtil.isNotNullAndEmpty(deviceName)&&!deviceName.equals(device.getDeviceName())) {
			device.setDeviceName(deviceName);
		}
		if(LogicUtil.isNotNullAndEmpty(deviceBrand)&&!deviceBrand.equals(device.getDeviceBrand())) {
			device.setDeviceBrand(deviceBrand);
		}
		if(LogicUtil.isNotNullAndEmpty(deviceMod)&&!deviceMod.equals(device.getDeviceMod())) {
			device.setDeviceMod(deviceMod);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalId)&&!hospitalId.equals(device.getHospitalId())) {
			device.setHospitalId(hospitalId);
		}
		if(LogicUtil.isNotNullAndEmpty(hospitalName)&&!hospitalName.equals(device.getHospitalName())) {
			device.setHospitalName(hospitalName);
		}

	}

}
