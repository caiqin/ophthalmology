package com.uniclans.ophthalmology.modules.diagnose.dao.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 设备实体
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_device")
public class Device {
	private String id;
	private String deviceId;//DEVICE_ID` varchar(32) NOT NULL COMMENT '设备编号',
	private String deviceName;//DEVICE_NAME` varchar(32) DEFAULT NULL COMMENT '设备名称',
	private String deviceBrand;//DEVICE_BRAND` varchar(32) DEFAULT NULL COMMENT '设备品牌',
	private String deviceMod;//DEVICE_MOD` varchar(32) DEFAULT NULL COMMENT '设备型号',
	private String qrCode;//QR_CODE` varchar(32) DEFAULT NULL COMMENT '设备二维码',
	private String hospitalId;//HOSPITAL_ID` varchar(32) DEFAULT NULL COMMENT '设备所处医院或社康'
	private String hospitalName;//HOSPITAL_ID` varchar(32) DEFAULT NULL COMMENT '设备所处医院或社康'
	
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "DEVICE_ID")
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Column(name = "DEVICE_NAME")
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	@Column(name = "DEVICE_BRAND")
	public String getDeviceBrand() {
		return deviceBrand;
	}
	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}
	@Column(name = "DEVICE_MOD")
	public String getDeviceMod() {
		return deviceMod;
	}
	public void setDeviceMod(String deviceMod) {
		this.deviceMod = deviceMod;
	}
	@Column(name = "QR_CODE")
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	@Column(name = "HOSPITAL_ID")
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	@Column(name = "HOSPITAL_Name")
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	
	
}
