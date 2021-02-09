package com.shu.onlineEducation.utils.idempotent;

import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.RedisUtil;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@Component
public class IdempotentToken {
	@Autowired
	RedisUtil redisUtil;
	
	//过期时间为30秒
	private final static Long TOKEN_EXPIRE = 30L;
	private static final String API_IDEMPOTENT_TOKEN_NAME = "IdempotentToken";
	
	//生成token
	public String generateToken() {
		String uuid = UUID.randomUUID().toString();
		redisUtil.set(uuid,uuid,TOKEN_EXPIRE);
		log.info((String) redisUtil.get(uuid));
		return uuid;
	}
	
	//校验token
	public boolean verifyToken(HttpServletRequest request) throws NotFoundException {
		String token = request.getHeader(API_IDEMPOTENT_TOKEN_NAME);
		// header中不存在token
		if(StringUtils.isEmpty(token)) {
			// 抛出自定义异常
			log.info("token不存在");
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		// 缓存中不存在
		if(!redisUtil.hasKey(token)) {
			// 抛出自定义异常
			log.info("token已经过期");
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		String cachToekn = (String) redisUtil.get(token);
		if (!token.equals(cachToekn)){
			// 抛出自定义异常
			log.info("token校验失败");
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		// 移除token
		Boolean del = redisUtil.del(token);
		if (!del){
			// 抛出自定义异常
			log.info("token删除失败");
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return true;
	}
	
	
}
