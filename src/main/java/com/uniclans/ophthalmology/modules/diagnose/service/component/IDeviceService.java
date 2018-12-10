package com.uniclans.ophthalmology.modules.diagnose.service.component;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DeviceVo;

/**
 * 设备信息Service
 * @author Stanley
 *
 */
public interface IDeviceService {

	/**
	 *  * 分页查询设备信息
	 * @param queryVo
	 * @return
	 * @throws Exception
	 */
	public PageFinder<DeviceVo> pagedDevices(DeviceVo queryVo) throws Exception;
	/**
	 * 新增设备信息
	 * @param deviceVo
	 * @throws Exception
	 */
	public void addDevice(DeviceVo deviceVo)throws Exception;
	/**
	 * 修改设备信息
	 * @param deviceVo
	 * @throws Exception
	 */
	public void updateDevice(DeviceVo deviceVo)throws Exception;
}
