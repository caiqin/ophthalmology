package com.uniclans.ophthalmology.modules.permission.viewcomponent.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uniclans.ophthalmology.basecomponent.constans.PermissionConstant;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemUserVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemRoleService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserRoleService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.IUserRoleViewComponent;


@Service
public class UserRoleViewComponentImpl implements IUserRoleViewComponent {

	@Resource
	private ISystemUserRoleService userRoleServComponent;
	@Resource
	private SystemUserVoConverter systemUserVoConverter;
	@Resource
	private ISystemRoleService systemRoleServComponent;

	@Override
	public boolean saveUserRoles(String roleId, String userIds)
			throws Exception {
		String[] ids = null;
		if (userIds!=null&&!userIds.trim().isEmpty()) {
			ids = userIds.split(PermissionConstant.spit_dot);
			return userRoleServComponent.saveUserRoles(roleId, ids);
		}
		return false;
	}

	@Override
	public boolean addUserRoles(String userId, String roleIds,String deleteIds) throws Exception {
		String[] ids = null;
		String [] delIds = null;
		if(deleteIds!=null&&!deleteIds.trim().isEmpty()) {
			delIds = deleteIds.split(PermissionConstant.spit_dot);
		}
		if (roleIds!=null&&!roleIds.trim().isEmpty()) {
			ids = roleIds.split(PermissionConstant.spit_dot);
			return userRoleServComponent.addUserRoles(userId, ids,delIds);
		}
		return false;
	}

	@Override
	public List<SystemUserVo> getUsers(String loginType) throws Exception {
		List<SystemUserVo> doList = userRoleServComponent
				.getAllSystemUser(loginType);
		return doList;
	}

	@Override
	public PageFinder<SystemRoleVo> getUserRoleList(String userId,
			Integer pageNo, Integer pageSize) throws Exception {
		List<SystemRoleVo> roleVoList = new ArrayList<SystemRoleVo>();
		PageFinder<UserRoleVo> userRoleList = userRoleServComponent
				.getUserRoleforPage(userId, pageNo, pageSize);
		if (userRoleList != null && userRoleList.getData().size() > 0) {
			for (UserRoleVo userRoleDo : userRoleList.getData()) {
				SystemRoleVo systemRoleDo = systemRoleServComponent
						.getSystemRoleVo(userRoleDo.getRoleId());
				if (!systemRoleDo.getIsValid().equals("0")) {
					roleVoList.add(systemRoleDo);
				}

			}
			return new PageFinder<SystemRoleVo>(pageNo, pageSize,
					userRoleList.getRowCount(), roleVoList);
		}
		return new PageFinder<SystemRoleVo>(pageNo, pageSize, 0, roleVoList);
	}

	@Override
	public PageFinder<UserRoleQueryVo> pageUserRoleList(
			UserRoleQueryVo userRoleQueryVo, int pageNo, int pageSize)
			throws Exception {
		Map<String, String> queryMap = new HashMap<String, String>();
		List<UserRoleQueryVo> voList = new ArrayList<UserRoleQueryVo>();
		if (StringUtils.isNotBlank(userRoleQueryVo.getUserName())) {
			queryMap.put("userName", userRoleQueryVo.getUserName());
		}
		if (StringUtils.isNotBlank(userRoleQueryVo.getRoleName())) {
			queryMap.put("roleName", userRoleQueryVo.getRoleName());
		}
		PageFinder<UserRoleQueryVo> queryDo = userRoleServComponent
				.pageFindUserRoles(queryMap, pageNo, pageSize);
		if(queryDo!=null&&queryDo.getData()!=null){
			voList =queryDo.getData();
			return new PageFinder<UserRoleQueryVo>(pageNo, pageSize,
					queryDo.getRowCount(), voList);
		}
		return new PageFinder<UserRoleQueryVo>(pageNo, pageSize, 0, voList);
	}

	@Override
	public boolean delUserRole(String id) throws Exception {
		
		return userRoleServComponent.delUserRole(id);
	}

	@Override
	public boolean batchDelUserRole(String ids) throws Exception {
		if(StringUtils.isNotBlank(ids)){
			String[] delIds = ids.split(",");
			for(int i=0;i<delIds.length;i++){
				userRoleServComponent.delUserRole(delIds[i]);
			}
			return true;
		}
		return false;
	}

}
