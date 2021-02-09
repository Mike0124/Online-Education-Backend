package com.shu.onlineEducation.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
	public static Map<String, Object> pageResponse(Page page){
		Map<String, Object> map = new HashMap<>(2);
		map.put("page_size",page.getTotalPages());
		map.put("list",page.getContent());
		return map;
	}
}
