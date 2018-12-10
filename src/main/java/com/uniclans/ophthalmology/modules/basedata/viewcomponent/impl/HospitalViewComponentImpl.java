package com.uniclans.ophthalmology.modules.basedata.viewcomponent.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uniclans.ophthalmology.modules.basedata.service.component.IHospitalService;
import com.uniclans.ophthalmology.modules.basedata.service.model.HospitalVo;
import com.uniclans.ophthalmology.modules.basedata.viewcomponent.IHospitalViewComponent;
import com.uniclans.ophthalmology.modules.permission.service.model.ResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;

@Service
public class HospitalViewComponentImpl implements IHospitalViewComponent{

	@Resource
	private IHospitalService hospitalService;
	
	@Override
	public String getHospitalStr() throws Exception {
		List<HospitalVo> hospitalList = hospitalService.getAll();
		return toConverResString(hospitalList);
	}

	private String toConverResString(List<HospitalVo> list )
			throws Exception {

		Gson gs = new Gson();

		List<HospitalVo> superResourceList = new ArrayList<HospitalVo>();
		List<HospitalVo> seResourceList = new ArrayList<HospitalVo>();
		List<HospitalVo> thResourceList = new ArrayList<HospitalVo>();
		// 资源根据层级分组
		if (list != null && list.size() > 0) {
			for (HospitalVo resource : list) {
				if (resource.getLevel()==1) {
					superResourceList.add(resource);
				} else if (resource.getLevel()==2) {
					seResourceList.add(resource);
				} else {
					thResourceList.add(resource);
				}
			}
		}

		HospitalVo oneVo = null;
		HospitalVo twoVo = null;
		HospitalVo threeVo = null;

		Map<String, HospitalVo> tempList1 = null;
		Map<String, HospitalVo> tempList2 = null;
		Map<String, HospitalVo> allData = new HashMap<String, HospitalVo>();

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
					if (threeVo.getParentNo().equals(twoVo.getHospitalId())) {
						tempList2.put(threeVo.getId(), threeVo);
						JsonObject jsO3 = new JsonObject();
						jsO3.addProperty("id", threeVo.getId());
						jsO3.addProperty("text", threeVo.getHospitalName());
						jsO3.addProperty("hospitalId", threeVo.getHospitalId());
						jsO3.addProperty("level", threeVo.getLevel());
						jsA3.add(jsO3);
					}
				}

				if (twoVo.getParentNo().equals(oneVo.getHospitalId())) {
					tempList1.put(twoVo.getId(), twoVo);
					JsonObject jsO2 = new JsonObject();
					jsO2.addProperty("id", twoVo.getId());
					jsO2.addProperty("text", twoVo.getHospitalName());
					jsO2.addProperty("hospitalId", twoVo.getHospitalId());
					jsO2.addProperty("level", twoVo.getLevel());
					jsO2.add("nodes", jsA3);
					jsA2.add(jsO2);
				}
				twoVo.setChild(tempList2);
			}

			JsonObject jsO1 = new JsonObject();
			jsO1.addProperty("id", oneVo.getId());
			jsO1.addProperty("text", oneVo.getHospitalName());
			jsO1.addProperty("hospitalId", oneVo.getHospitalId());
			jsO1.addProperty("level", oneVo.getLevel());
			jsO1.add("nodes", jsA2);
			jsA1.add(jsO1);
			oneVo.setChild(tempList1);
			allData.put(oneVo.getId(), oneVo);
		}
		jsOAll.add("data", jsA1);
		return gs.toJson(jsA1);

	}
}
