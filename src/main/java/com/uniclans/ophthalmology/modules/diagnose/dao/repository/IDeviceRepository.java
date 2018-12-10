package com.uniclans.ophthalmology.modules.diagnose.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uniclans.ophthalmology.modules.diagnose.dao.model.entity.Device;
/**
 * 设备Repository
 * @author Stanley
 *
 */
public interface IDeviceRepository  extends JpaRepository<Device, String>, JpaSpecificationExecutor<Device>{
	public Device findByDeviceId(String deviceId);
}
