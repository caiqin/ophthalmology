package com.uniclans.ophthalmology.modules.main.viewcomponent.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uniclans.ophthalmology.modules.main.viewcomponent.IUserLoginViewComponent;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemResourceVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemUserVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserLoginService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;

@Service
public class UserLoginViewComponentImpl implements IUserLoginViewComponent {

	@Resource
	private ISystemUserLoginService systemUserLoginServComponent;
	@Resource
	private SystemUserVoConverter systemUserVoConverter;
	@Resource
	private SystemResourceVoConverter resourceVoConverter;

//	@Override
//	public List<SystemResourceVo> getUserResources(SystemUserVo systemUserVo)
//			throws Exception {
//		List<SystemResourceVo> sourceVoList = new ArrayList<SystemResourceVo>();
//		SystemUserDo systemUserDo = systemUserVoConverter
//				.systemUserVoConverDo(systemUserVo);
//		List<SystemResourceDo> sourceList = systemUserLoginServComponent
//				.getUserResources(systemUserDo);
//		if (sourceList != null && sourceList.size() > 0) {
//			for (SystemResourceDo resDo : sourceList) {
//				sourceVoList.add(resourceVoConverter
//						.systemResourceDoConverVo(resDo));
//			}
//		}
//		return sourceVoList;
//	}
	

	@Override
	public List<SystemResourceVo> getUserResources(SystemUserVo systemUserVo, String systemName,String rescType)
			throws Exception {
		List<SystemResourceVo> sourceVoList = new ArrayList<SystemResourceVo>();
		
		
		List<SystemResourceVo> sourceList = systemUserLoginServComponent.getUserResources(systemUserVo,rescType);
		
		if (sourceList != null && sourceList.size() > 0) {
			for (SystemResourceVo resDo : sourceList) {
				
				//1:
				if(resDo.getIsShow()==1 && resDo.getDeleteFlag().equals("0"))
				{
					sourceVoList.add(resDo);
				}
			}
		}
		return sourceVoList.isEmpty()?null:sourceVoList;
	}

	@Override
	public String toConverString(List<SystemResourceVo> list) throws Exception {
		
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
			SortList<SystemResourceVo> superSortList = new SortList<SystemResourceVo>();  
			superSortList.sort(superResourceList, "getSeqNum", "asc"); 
			SortList<SystemResourceVo> secondSortList = new SortList<SystemResourceVo>();  
			secondSortList.sort(seResourceList, "getSeqNum", "asc"); 
			SortList<SystemResourceVo> thirdSortList = new SortList<SystemResourceVo>();  
			thirdSortList.sort(thResourceList, "getSeqNum", "asc"); 
		}
		//List<SystemResourceVo> allList = new ArrayList<SystemResourceVo>();
		
		SystemResourceVo oneVo = null;
		SystemResourceVo twoVo = null;
		SystemResourceVo threeVo = null;
		
		Map<String,SystemResourceVo> tempList1 = null;
		Map<String,SystemResourceVo> tempList2 = null;
		Map<String,SystemResourceVo> allData = new HashMap<String, SystemResourceVo>();
		
		
		JsonObject jsOAll = new JsonObject();
		JsonArray jsA1 = new JsonArray();
		for(int idx1=0;idx1<superResourceList.size();idx1++){
			oneVo = superResourceList.get(idx1);
			tempList1 = oneVo.getChild();
			
			JsonArray jsA2 = new JsonArray();
			for(int idx2=0;idx2<seResourceList.size();idx2++){
				twoVo = seResourceList.get(idx2);
				tempList2 = twoVo.getChild();
				JsonArray jsA3 = new JsonArray();
				for(int idx3=0;idx3<thResourceList.size();idx3++){
					threeVo = thResourceList.get(idx3);
					if(threeVo.getSupperResId().equals(twoVo.getId())){
						tempList2.put(threeVo.getId(), threeVo);
						JsonObject jsO3 = new JsonObject();
						jsO3.addProperty("id", threeVo.getId());
						jsO3.addProperty("pid", threeVo.getSupperResId());
						jsO3.addProperty("text", threeVo.getResName());
						jsO3.addProperty("href", threeVo.getResUrl());
						jsA3.add(jsO3);
					}
				}
				
				if(twoVo.getSupperResId().equals(oneVo.getId())){
					tempList1.put(twoVo.getId(), twoVo);
					JsonObject jsO2 = new JsonObject();
					jsO2.addProperty("id", twoVo.getId());
					jsO2.addProperty("pid", twoVo.getSupperResId());
					jsO2.addProperty("text", twoVo.getResName());
					jsO2.addProperty("href", twoVo.getResUrl());
					jsO2.add("children", jsA3);
					jsA2.add(jsO2);
				}
				twoVo.setChild(tempList2);
			}
			
			
			JsonObject jsO1 = new JsonObject();
			jsO1.addProperty("id", oneVo.getId());
			jsO1.addProperty("text", oneVo.getResName());
			jsO1.add("children", jsA2);
			jsA1.add(jsO1);
			oneVo.setChild(tempList1);
			allData.put(oneVo.getId(), oneVo);
		}
		jsOAll.add("data", jsA1);
	//	System.out.println("=======================" + gs.toJson(jsOAll));
		
		return gs.toJson(jsOAll);
		
		
		
		
		
		
//		StringBuffer s = new StringBuffer("{\"data\": [");
//		if (superResourceList != null && superResourceList.size() > 0) {
//			for (SystemResourceVo first : superResourceList) {
//				String superId = first.getId();
//				s.append("{\"id\": \"" + first.getId() + "\", \"text\": \""
//						+ first.getResName() + "\" ");
//				// 遍历2级资源
//				if (seResourceList != null && seResourceList.size() > 0) {
//					s.append(", children:[ ");
//					for (int i = 0; i < seResourceList.size(); i++) {
//						SystemResourceVo second = seResourceList.get(i);
//						String secondSuperId = second.getSupperResId();
//						String secondId = second.getId();
//						// 判断是否是1级资源的子集
//						if (secondSuperId.equals(superId)) {
//							if (second.getResUrl() != null
//									&& !second.getResUrl().equals("")) {
//								s.append("{\"pid\": \"" + first.getId()
//										+ "\", \"id\": \"" + second.getId()
//										+ "\", \"text\": \""
//										+ second.getResName()
//										+ "\", \"href\": \""
//										+ second.getResUrl() + "\" ");
//							} else {
//								s.append("{\"pid\": \"" + first.getId()
//										+ "\", \"id\": \"" + second.getId()
//										+ "\", \"text\": \""
//										+ second.getResName() + "\" ");
//							}
//							seResourceList.remove(i);
//						}
//						// 遍历3级资源
//						if (thResourceList != null && thResourceList.size() > 0) {
//							s.append(", children:[");
//							for (int j = 0; j < thResourceList.size(); j++) {
//								SystemResourceVo third = thResourceList.get(i);
//								String thSuperId = third.getSupperResId();
//								if (thSuperId.equals(secondId)) {
//									if (third.getResUrl() != null
//											&& !third.getResUrl().equals("")) {
//										s.append("{\"pid\": \""
//												+ second.getId()
//												+ "\", \"id\": \""
//												+ third.getId()
//												+ "\", \"text\": \""
//												+ third.getResName()
//												+ "\", \"href\": \""
//												+ third.getResUrl() + "\"}");
//									} else {
//										s.append("{\"pid\": \""
//												+ second.getId()
//												+ "\", \"id\": \""
//												+ third.getId()
//												+ "\", \"text\": \""
//												+ third.getResName() + "\"}");
//									}
//									thResourceList.remove(j);
//								}
//							}
//							s.append("]");
//
//						} else {
//							s.append("},");
//						}
//					}
//					s.append("]");
//				} else {
//					s.append("},");
//				}
//
//			}
//		}
//		int index = s.toString().lastIndexOf(",");
//		return s.substring(0, index) + "]}";
	}
	
}
