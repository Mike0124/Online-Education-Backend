package com.shu.onlineEducation.service;

import java.util.Map;

public interface SmsService {
	Boolean sendMessage(String phone, Map<String, Object> codeMap);
}
