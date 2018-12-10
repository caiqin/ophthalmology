package com.uniclans.ophthalmology.modules.diagnose.service.model;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 设备Vo
 * 
 * @author Stanley
 *
 */
public class DeviceVo extends PageVo{
	private String id;
	private String deviceId;// '设备编号',
	private String deviceName;// '设备名称',
	private String deviceBrand;// '设备品牌',
	private String deviceMod;// '设备型号',
	private String qrCode;//'设备二维码',
	private String hospitalId;// '设备所处医院或社康'
	private String hospitalName;// '设备所处医院或社康'

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceMod() {
		return deviceMod;
	}

	public void setDeviceMod(String deviceMod) {
		this.deviceMod = deviceMod;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

}
