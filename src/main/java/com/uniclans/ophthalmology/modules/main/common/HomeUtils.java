package com.uniclans.ophthalmology.modules.main.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;



public class HomeUtils {
	
	public static Map<String,SystemResourceVo> listToMap(List<SystemResourceVo> rescVos)throws Exception{
		
		if(rescVos==null || rescVos.size()<=0){
			return null;
		}
		Map<String,SystemResourceVo> rescMap=new HashMap<String,SystemResourceVo>(); 
		for(SystemResourceVo rescVo:rescVos){
			if(rescVo!=null && StringUtils.isNotEmpty(rescVo.getResCode())){
				rescMap.put(rescVo.getResCode(), rescVo);
			}
		}
		return rescMap;
	}

}
