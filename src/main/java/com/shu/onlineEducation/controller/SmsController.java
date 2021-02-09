package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.service.SmsService;
import com.shu.onlineEducation.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class SmsController {
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 发送短信
	 * @param phone
	 */
	@Async
	public String sendCode(String phone) {
		
		// 根据手机号从redis中拿验证码
		String code = (String) redisUtil.get(phone);
		if (!StringUtils.isEmpty(code)) {
			return phone + " : " + code + "已经存在，还没有过期！";
		}
		
		// 如果redis中无手机号对应验证码，则生成4位随机验证码
		code = UUID.randomUUID().toString().substring(0, 4);
		
		// 发送短信
		Map<String, Object> codeMap = new HashMap<>(5);
		codeMap.put("code", code);
		
		// 如果发送成功，则将生成的4位随机验证码存入redis缓存,5分钟后过期
		if (smsService.sendMessage(phone, codeMap)) {
			redisUtil.set(phone, code, 5 * 60);
			return phone + " : " + code + "发送成功！";
		} else {
			return phone + " : " + code + "发送失败！";
		}
	}
}
