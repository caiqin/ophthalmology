package com.uniclans.ophthalmology.modules.permission.viewcomponent.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uniclans.ophthalmology.basecomponent.constans.SystemManagerConstants;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemResourceVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemUserVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemResourceService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserLoginService;
import com.uniclans.ophthalmology.modules.permission.service.model.ResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.IResourceViewComponent;

@Service
public class ResourceViewComponentImpl implements IResourceViewComponent {

	@Resource
	private SystemResourceVoConverter resourceVoConverter;
	@Resource
	private ISystemResourceService resourceServComponent;

	@Resource
	private ISystemUserLoginService systemUserLoginServComponent;
	@Resource
	private SystemUserVoConverter systemUserVoConverter;

	@Override
	public boolean saveResource(SystemResourceVo resourceVo) throws Exception {
		boolean isforce = false;
		resourceVo.setResName(StringUtils.removeRepeatedWhitespaces(resourceVo
				.getResName()));
		boolean isExist = resourceServComponent.checkSourceExist(resourceVo);
		if (!isExist) {
			resourceVo.setCreateTime(DateUtils.getCurrentDateTime());
			resourceVo.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_0);
			//resourceVo.setResType("1");
			resourceServComponent.addSystemResource(resourceVo);
			isforce = true;
		}

		return isforce;
	}

	@Override
	public PageFinder<SystemResourceVo> getResourcesByPage(ResourceQueryVo resourceQueryVo, Integer pageNo, Integer pageSize)
			throws Exception {
		PageFinder<SystemResourceVo> finder = resourceServComponent
				.getResourcesByPage(resourceQueryVo, pageNo, pageSize);
		return finder;
	}

	@Override
	public void updateResource(SystemResourceVo resourceVo) throws Exception {

		resourceServComponent.updateResource(resourceVo);
	}

	@Override
	public SystemResourceVo getResourceById(String resourceId) throws Exception {

		SystemResourceVo resourceVo = resourceServComponent
				.getResourceById(resourceId);
		return resourceVo;
	}

	@Override
	public String getResourcesStr(SystemUserVo systemUserVo) throws Exception {
		List<SystemResourceVo> sourceList = 
		resourceServComponent.getResourcesByPage(new ResourceQueryVo(), 0, Integer.MAX_VALUE).getData();
		return toConverResString(sourceList);
	}

	private String toConverResString(List<SystemResourceVo> list)
			throws Exception {

		Gson gs = new Gson();

		List<SystemResourceVo> superResourceList = new ArrayList<SystemResourceVo>();
		List<SystemResourceVo> seResourceList = new ArrayList<SystemResourceVo>();
		List<SystemResourceVo> thResourceList = new ArrayList<SystemResourceVo>();
		// 资源根据层级分组
		if (list != null && list.size() > 0) {
			for (SystemResourceVo resource : list) {
				if (resource.getLevel()==1) {
					superResourceList.add(resource);
				} else if (resource.getLevel()==2) {
					seResourceList.add(resource);
				} else {
					thResourceList.add(resource);
				}
			}
		}

		SystemResourceVo oneVo = null;
		SystemResourceVo twoVo = null;
		SystemResourceVo threeVo = null;

		Map<String, SystemResourceVo> tempList1 = null;
		Map<String, SystemResourceVo> tempList2 = null;
		Map<String, SystemResourceVo> allData = new HashMap<String, SystemResourceVo>();

		JsonObject jsOAll = new JsonObject();
		JsonArray jsA1 = new JsonArray();
		for (int idx1 = 0; idx1 < superResourceList.size(); idx1++) {
			oneVo = superResourceList.get(idx1);
			tempList1 = oneVo.getChild();

			JsonArray jsA2 = new JsonArray();
			for (int idx2 = 0; idx2 < seResourceList.size(); idx2++) {
				twoVo = seResourceList.get(idx2);
				tempList2 = twoVo.getChild();
				JsonArray jsA3 = new JsonArray();
				for (int idx3 = 0; idx3 < thResourceList.size(); idx3++) {
					threeVo = thResourceList.get(idx3);
					if (threeVo.getSupperResId().equals(twoVo.getId())) {
						tempList2.put(threeVo.getId(), threeVo);
						JsonObject jsO3 = new JsonObject();
						jsO3.addProperty("id", threeVo.getId());
						jsO3.addProperty("text", threeVo.getResName());
						jsO3.addProperty("level", threeVo.getLevel());
						jsA3.add(jsO3);
					}
				}

				if (twoVo.getSupperResId().equals(oneVo.getId())) {
					tempList1.put(twoVo.getId(), twoVo);
					JsonObject jsO2 = new JsonObject();
					jsO2.addProperty("id", twoVo.getId());
					jsO2.addProperty("text", twoVo.getResName());
					jsO2.addProperty("level", twoVo.getLevel());
					jsO2.add("nodes", jsA3);
					jsA2.add(jsO2);
				}
				twoVo.setChild(tempList2);
			}

			JsonObject jsO1 = new JsonObject();
			jsO1.addProperty("id", oneVo.getId());
			jsO1.addProperty("text", oneVo.getResName());
			jsO1.addProperty("level", oneVo.getLevel());
			jsO1.add("nodes", jsA2);
			jsA1.add(jsO1);
			oneVo.setChild(tempList1);
			allData.put(oneVo.getId(), oneVo);
		}
		jsOAll.add("data", jsA1);
		return gs.toJson(jsA1);

	}

	@Override
	public void delResource(SystemResourceVo resourceVo) throws Exception {
		SystemResourceVo resource = resourceServComponent
				.getResourceById(resourceVo.getId());
		resource.setDeleteFlag(SystemManagerConstants.DELETE_FLAG_1);
		resourceServComponent.updateResource(resource);
	}

}
