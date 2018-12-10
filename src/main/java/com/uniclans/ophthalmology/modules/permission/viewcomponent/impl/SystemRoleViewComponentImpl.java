package com.uniclans.ophthalmology.modules.permission.viewcomponent.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uniclans.ophthalmology.basecomponent.constans.SystemManagerConstants;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.permission.service.component.IRoleResourceService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemRoleService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserRoleService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.ISystemRoleViewComponent;


@Service
public class SystemRoleViewComponentImpl implements ISystemRoleViewComponent {

	@Resource
	private ISystemRoleService systemRoleServComponent;
	@Resource
	private ISystemUserRoleService systemUserRoleServComponent;
	@Resource
	private IRoleResourceService rolePermissionServComponent;

	@Override
	public List<SystemRoleVo> getSystemRoleVos(SystemRoleVo systemRoleVo)
			throws Exception {
		List<SystemRoleVo> doList = systemRoleServComponent
				.getSystemRoleDos(systemRoleVo);
		return doList;
	}

	@Override
	public boolean addSystemRole(SystemRoleVo systemRoleVo) throws Exception {
		boolean turn = false;

		systemRoleVo.setRoleName(StringUtils
				.removeRepeatedWhitespaces(systemRoleVo.getRoleName()));
		boolean b = systemRoleServComponent.checkRoleExist(systemRoleVo);
		if (!b) {
			systemRoleVo.setRoleType("1");
			systemRoleVo.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_1);
			systemRoleServComponent.addSystemRole(systemRoleVo);
			turn = true;
		}
		return turn;
	}

	@Override
	public SystemRoleVo getSystemRoleVo(String systemRoleId) throws Exception {
		return systemRoleServComponent.getSystemRoleVo(systemRoleId);
	}

	@Override
	public void updateSystemRole(SystemRoleVo systemRoleVo) throws Exception {
		systemRoleVo.setRoleType("1");
		systemRoleServComponent.updateSystemRole(systemRoleVo);

	}

	@Override
	public boolean delSystemRole(SystemRoleVo systemRoleVo) throws Exception {

		boolean b = isHoldUserOrPermi(systemRoleVo.getId());
		if (!b) {
			systemRoleServComponent.delSystemRole(systemRoleVo);
			return true;
		}
		return false;

	}

	@Override
	public PageFinder<SystemRoleVo> getRoles(SystemRoleQueryVo queryVo,
			Integer pageNo, Integer pageSize) throws Exception {
		PageFinder<SystemRoleVo> doList = systemRoleServComponent.getRoles(
				queryVo, pageNo, pageSize);
		return doList;
	} 

	@Override
	public boolean isHoldUserOrPermi(String systemRoleId) throws Exception {

		if (StringUtils.isExist(systemRoleId)) {
			boolean isUser = false;
			boolean isPermision = false;
			List<UserRoleVo> userRoleList = systemUserRoleServComponent
					.getUserRoleList(null, systemRoleId);

			if (userRoleList != null && userRoleList.size() > 0) {
				isUser = true;
			}
			String rolds[] = { systemRoleId };
			List<SystemRoleResourceVo> roleResourcesList = rolePermissionServComponent.getRoleResourcesList(rolds);
			if (roleResourcesList != null && roleResourcesList.size() > 0) {
				isPermision = true;
			}
			if (isUser || isPermision) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean remoteValid(String systemRoleId) throws Exception {
		boolean returnType =false;
		SystemRoleVo systemRoleVo = systemRoleServComponent.getSystemRoleVo(systemRoleId);
		if(systemRoleVo!=null){
			if(systemRoleVo.getIsValid().equals("1")){
				systemRoleVo.setIsValid("0");
			}else{
				systemRoleVo.setIsValid("1");
			}
			systemRoleServComponent.updateSystemRole(systemRoleVo);
			returnType =true;
		}
		return returnType;
	}

}
