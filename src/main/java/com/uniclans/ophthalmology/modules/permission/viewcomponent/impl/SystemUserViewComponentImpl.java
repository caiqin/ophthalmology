package com.uniclans.ophthalmology.modules.permission.viewcomponent.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uniclans.ophthalmology.basecomponent.constans.SystemManagerConstants;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserQueryVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.ISystemUserViewComponent;


@Service
public class SystemUserViewComponentImpl implements ISystemUserViewComponent {

	@Resource
	private ISystemUserService systemUserServComponent;

	@Override
	public SystemUserVo getSystemUserByName(String loginName,
			String loginPassword) throws Exception {
		return systemUserServComponent.getSystemUserByName(loginName, loginPassword);
	}

	@Override
	public SystemUserVo getSystemUserById(String userId) throws Exception {
		return systemUserServComponent.getSystemUserById(userId);
	}

	@Override
	public boolean addUser(SystemUserVo systemUserVo) throws Exception {
		
		boolean re = false;
		
		boolean b = systemUserServComponent.checkUserExist(systemUserVo);
		boolean r = systemUserServComponent.checkMobileExist(systemUserVo);
		if(!b&&!r){
			systemUserVo.setCreateDate(DateUtils.getCurrentDateTime());
			systemUserVo.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_0);
			systemUserServComponent.saveOrUpdateUser(systemUserVo);
			re=true;
		}
		return re;
		
	}

	@Override
	public SystemUserVo deleteUser(SystemUserVo systemUserVo) throws Exception {
		return systemUserServComponent.deleteUser(systemUserVo);
	}

	@Override
	public SystemUserVo updateUser(SystemUserVo systemUserVo) throws Exception {
		SystemUserVo vo = systemUserServComponent
				.getSystemUserById(systemUserVo.getId());
		vo.setEmail(systemUserVo.getEmail());
		vo.setLoginName(systemUserVo.getLoginName());
		vo.setMobilePhone(systemUserVo.getMobilePhone());
		vo.setQQNum(systemUserVo.getQQNum());
		vo.setSex(systemUserVo.getSex());
		vo.setTelPhone(systemUserVo.getTelPhone());
		vo.setUserName(systemUserVo.getUserName());
		return systemUserServComponent
						.saveOrUpdateUser(vo);
	}

	@Override
	public PageFinder<SystemUserVo> getUsers(UserQueryVo userQueryVo,
			Integer pageNo, Integer pageSize) throws Exception {
		PageFinder<SystemUserVo> doList = systemUserServComponent.getUsers(
				userQueryVo, pageNo, pageSize);

		return doList;

	}

	@Override
	public boolean checkUserExist(SystemUserVo systemUserVo) throws Exception {
		return systemUserServComponent.checkUserExist(systemUserVo);
	}

	@Override
	public boolean lockUser(String userId) throws Exception {
		return systemUserServComponent.unLockOrlockUser(userId);
	}

	@Override
	public boolean unLockUser(String userId) throws Exception {
		return systemUserServComponent.unLockOrlockUser(userId);
	}

	@Override
	public boolean remotePassword(SystemUserVo systemUserVo) throws Exception {
		return systemUserServComponent.remotePassword(systemUserVo);
	}

	@Override
	public void relation(SystemUserVo systemUserVo) throws Exception {
		systemUserServComponent.relation(systemUserVo);
	}

	@Override
	public boolean updatePassword(SystemUserVo systemUserVo) throws Exception {
		return systemUserServComponent.updatePassword(systemUserVo);
	}
}
