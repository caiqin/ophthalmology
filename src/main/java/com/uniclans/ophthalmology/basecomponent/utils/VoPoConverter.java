package com.uniclans.ophthalmology.basecomponent.utils;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;


public class VoPoConverter {
	private static DozerBeanMapper mapper;
	public static <DESC> DESC copyProperties(Object src, Class<DESC> clazz){
		if(src==null){
			return null;
		}
		if(mapper==null){
			mapper = new DozerBeanMapper();
		}
		return mapper.map(src, clazz);
	}
	
	public static void copyProperties(Object src, Object desc){
		if(mapper==null){
			mapper = new DozerBeanMapper();
		}
		mapper.map(src, desc);
	}
	
	
	public static <DESC> List<DESC> copyList(List srcList, Class<DESC> descClass){
		if(srcList == null) return null;
		
		List<DESC> descList = new ArrayList<DESC>();
		for(Object src : srcList){
			DESC desc = copyProperties(src, descClass);
			descList.add(desc);
		}
		return descList;
	}
	
}
