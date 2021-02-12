package com.shu.onlineEducation.utils;

import org.springframework.data.domain.Page;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	public static Map<String, Object> pageResponse(Page page){
		Map<String, Object> map = new HashMap<>(2);
		map.put("total_page",page.getTotalPages());
		map.put("list",page.getContent());
		map.put("total_element",page.getTotalElements());
		return map;
	}
}
