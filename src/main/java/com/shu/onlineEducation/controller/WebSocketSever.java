package com.shu.onlineEducation.controller;

import javax.websocket.Session;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.shu.onlineEducation.io.output.MsgVO;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * @author 黄悦麒
 * @className: WebSocketSever
 * @description :WebSocket服务端类
 * @date 2021/1/7
 */
@Component
@RestController
@Api(tags = "4-WebSocket")
@ServerEndpoint(value = "/websocket/{sid}/{type}/{userId}/{nickName}")   //房间号、类型（1，直播聊天）、用户Id、
public class WebSocketSever {
	private static final Logger log = LoggerFactory.getLogger(WebSocketSever.class);
	/**
	 * 直播号 -> 学生会话列表
	 */
	private static final ConcurrentHashMap<String, List<Session>> STUDENT_SESSION_MAP = new ConcurrentHashMap<>();
	/**
	 * 直播号 -> 学生列表
	 */
	private static final ConcurrentHashMap<String, Map<Integer, String>> STUDENT_MAP = new ConcurrentHashMap<>();
	/**
	 * 直播号 -> 教师会话
	 */
	private static final ConcurrentHashMap<String, Session> TEACHER_SESSION_MAP = new ConcurrentHashMap<>();
	
	/**
	 * 收到消息调用的方法，群成员发送消息
	 *
	 * @param sid     直播号
	 * @param message Json转换的发送消息
	 */
	@OnMessage
	public void onMessage(@PathParam("sid") String sid, String message) {
		List<Session> sessionList = STUDENT_SESSION_MAP.get(sid);
		// json字符串转对象
		MsgVO msg = JSON.parseObject(message, MsgVO.class);
		// json对象转字符串
		String text = JSON.toJSONString(msg);
		// 先一个群组内的成员发送消息
		sessionList.forEach(item -> {
			try {
				item.getBasicRemote().sendText(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		if (TEACHER_SESSION_MAP.get(sid) != null) {
			try {
				TEACHER_SESSION_MAP.get(sid).getBasicRemote().sendText(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 建立连接调用的方法，群成员加入
	 *
	 * @param session  会话
	 * @param sid      直播号
	 * @param type     用户类型:老师/学生
	 * @param userId   用户id
	 * @param nickName 用户昵称
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid, @PathParam("type") Integer type, @PathParam("userId") Integer userId, @PathParam("nickName") String nickName) {
		switch (type) {
			case 0:
				List<Session> sessionList = STUDENT_SESSION_MAP.computeIfAbsent(sid, k -> new ArrayList<>(30));
				Map<Integer, String> studentIdList = STUDENT_MAP.computeIfAbsent(sid, k -> new HashMap<>(30));
				studentIdList.put(userId, nickName);
				sessionList.add(session);
				log.info("Connection connected");
				log.info("sid: {}, studentList size: {}", sid, sessionList.size());
				break;
			case 1:
				TEACHER_SESSION_MAP.putIfAbsent(sid, session);
				log.info("Teacher Connection connected");
				break;
			default:
				log.error("Connection open error");
		}
	}
	
	/**
	 * 关闭连接调用的方法，群成员退出
	 *
	 * @param session 会话
	 * @param type    用户类型  老师/学生
	 * @param sid     直播号
	 */
	@OnClose
	public void onClose(Session session, @PathParam("sid") String sid, @PathParam("type") Integer type, @PathParam("userId") Integer userId) {
		switch (type) {
			case 0:
				List<Session> sessionList = STUDENT_SESSION_MAP.get(sid);
				sessionList.remove(session);
				Map<Integer, String> onlineUserMap = STUDENT_MAP.get(sid);
				onlineUserMap.remove(userId);
				log.info("Connection closed");
				log.info("sid: {}, sessionList size: {}", sid, sessionList.size());
				break;
			case 1:
				TEACHER_SESSION_MAP.remove(sid);
				log.info("Teacher Connection closed");
				break;
			default:
				log.error("Connection close error");
		}
		
	}
	
	/**
	 * 传输消息错误调用的方法
	 *
	 * @param error 错误
	 */
	@OnError
	public void onError(Throwable error) {
		log.info("Connection error:" + error.toString());
	}
	
	/**
	 * 返回在线学生列表
	 *
	 * @param sid 直播号
	 */
	@PostMapping(value = "api/WebSocket/onlineStudents")
	@ResponseBody
	public Result studentList(@RequestParam(value = "sid") String sid) {
		if (STUDENT_MAP.get(sid) == null) {
			return Result.failure(ResultCode.PARAM_IS_INVALID);
		}
		return Result.success(STUDENT_MAP.get(sid));
	}
}
