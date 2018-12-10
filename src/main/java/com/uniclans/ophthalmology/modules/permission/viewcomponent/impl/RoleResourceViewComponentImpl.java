package com.uniclans.ophthalmology.modules.permission.viewcomponent.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemResourceVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.IRoleResourceService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemResourceService;
import com.uniclans.ophthalmology.modules.permission.service.model.RoleResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.IRoleResourceViewComponent;


@Service
public class RoleResourceViewComponentImpl implements
		IRoleResourceViewComponent {

	@Resource
	private IRoleResourceService roleResourceServComponent;

	@Resource
	private SystemResourceVoConverter resourceVoConverter;

	@Resource
	private ISystemResourceService systemResourceServComponent;

	@Override
	public boolean saveRolesResource(String roleId, String perIds,String delIds)
			throws Exception {
		String[] ids = null;
		String[] delId = null;
		if (perIds!=null&&!perIds.trim().isEmpty()) {
			ids = perIds.split(",");
		}
		if(delIds!=null&&!delIds.equals("")) {
			delId = delIds.split(",");
		}
		return roleResourceServComponent.saveRoleResources(roleId, ids,delId);
	}


	@Override
	public PageFinder<RoleResourceQueryVo> getResources(
			RoleResourceQueryVo queryVo, Integer pageNo, Integer pageSize)
			throws Exception {
		// String roleId = queryVo.getRoleId();
		// List<SystemPermissionVo> permissionVoList = new
		// ArrayList<SystemPermissionVo>();
		// if (roleId != null) {
		// PageFinder<SysytemRolePermissionDo> rolePerDoList =
		// roleResourceServComponent
		// .getRolePemissionsByRole(roleId, pageNo, pageSize);
		//
		// if (rolePerDoList != null && rolePerDoList.getData().size() > 0) {
		// String[] perIds = new String[rolePerDoList.getData().size()];
		// for (int i = 0; i < rolePerDoList.getData().size(); i++) {
		// perIds[i] = rolePerDoList.getData().get(i).getPrmissionId();
		// }
		// List<SystemPermissionDo> permissionDoList =
		// systemResourceServComponent
		// .getSystemPemissionByIds(perIds);
		// if (permissionDoList != null && permissionDoList.size() > 0) {
		// for (SystemPermissionDo permissionDo : permissionDoList) {
		// permissionVoList.add(resourceVoConverter
		// .permissionDoConverVo(permissionDo));
		// }
		// return new PageFinder<SystemPermissionVo>(pageNo, pageSize,
		// rolePerDoList.getRowCount(), permissionVoList);
		// }
		// }
		// }
		// return new PageFinder<SystemPermissionVo>(pageNo, pageSize, 0,
		// permissionVoList);

		Map<String, String> queryMap = new HashMap<String, String>();
		if (StringUtils.isNotBlank(queryVo.getRoleId())) {
			queryMap.put("roleId", queryVo.getRoleId());
		}
		if (StringUtils.isNotBlank(queryVo.getRoleName())) {
			queryMap.put("roleName", queryVo.getRoleName());
		}
		if (StringUtils.isNotBlank(queryVo.getResourceName())) {
			queryMap.put("ResourceName", queryVo.getResourceName());
		}

		PageFinder<RoleResourceQueryVo> resultList = roleResourceServComponent
				.pageRoleResources(queryMap, pageNo, pageSize);

		return resultList;
	}

	@Override
	public boolean delRolesResource(String rolePerId) throws Exception {

		return roleResourceServComponent.delRoleResource(rolePerId);
	}

	@Override
	public boolean batchDelRolesResource(String ids) throws Exception {
		if(StringUtils.isNotBlank(ids)){
			String[] delIds = ids.split(",");
			for(int i=0;i<delIds.length;i++){
				roleResourceServComponent.delRoleResource(delIds[i]);
			}
			return true;
		}
		return false;
	}

}
