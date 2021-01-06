package com.shu.onlineEducation.controller;

import javax.websocket.Session;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.shu.onlineEducation.dao.StudentJpaRepository;
import com.shu.onlineEducation.dao.TeacherJpaRepository;
import com.shu.onlineEducation.io.output.MsgVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Component
@RestController
@ServerEndpoint(value = "/websocket/{sid}/{type}/{userId}")   //房间号、类型（1，直播聊天）、用户Id、
public class WebSocketController {
	private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	@Autowired
	private TeacherJpaRepository teacherJpaRepository;
	/**
	 * 房间号 -> 组成员信息
	 */
	private static final ConcurrentHashMap<String, List<Session>> groupMemberInfoMap = new ConcurrentHashMap<>();
	/**
	 * 房间号 -> 在线人数
	 */
	private static final ConcurrentHashMap<String, Set<Integer>> onlineUserMap = new ConcurrentHashMap<>();
	
	/**
	 * 收到消息调用的方法，群成员发送消息
	 *
	 * @param sid:直播房间号
	 * @param userId：用户id
	 * @param message：发送消息
	 */
	@OnMessage
	public void onMessage(@PathParam("sid") String sid, @PathParam("userId") Integer userId, String message) {
		List<Session> sessionList = groupMemberInfoMap.get(sid);
		Set<Integer> onlineUserList = onlineUserMap.get(sid);
		// 先一个群组内的成员发送消息
		sessionList.forEach(item -> {
			try {
				// json字符串转对象
				MsgVO msg = JSONObject.parseObject(message, MsgVO.class);
				msg.setCount(onlineUserList.size());
				// json对象转字符串
				String text = JSONObject.toJSONString(msg);
				item.getBasicRemote().sendText(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * 建立连接调用的方法，群成员加入
	 *
	 * @param session
	 * @param sid 直播房间号
	 * @param type:用户类型 老师/学生
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid, @PathParam("type") Integer type, @PathParam("userId") Integer userId) {
		List<Session> sessionList = groupMemberInfoMap.computeIfAbsent(sid, k -> new ArrayList<>());
		Set<Integer> onlineUserList = onlineUserMap.computeIfAbsent(sid, k -> new HashSet<>());
		onlineUserList.add(userId);
		sessionList.add(session);
		
		// 发送上线通知
//		sendInfo(sid, type, userId, onlineUserList.size(), "上线了~");
		
		log.info("Connection connected");
		log.info("sid: {}, sessionList size: {}", sid, sessionList.size());
	}
	
	
	public void sendInfo(String sid, @PathParam("type") Integer type, Integer userId, Integer onlineSum, String info) {
		// 发送通知
		MsgVO msg = new MsgVO();
		// 获取该连接用户信息
		if (type.equals(0)) {
			msg.setMsg("学生：" + this.studentJpaRepository.findStudentByUserId(userId).getNickName() + info);
		} else if (type.equals(1)) {
			msg.setMsg("教师：" + this.teacherJpaRepository.findTeacherByUserId(userId).getName() + info);
		}
		msg.setCount(onlineSum);
		msg.setUserId(userId);
		
		// json对象转字符串
		String text = JSONObject.toJSONString(msg);
		onMessage(sid, userId, text);
	}
	
	/**
	 * 关闭连接调用的方法，群成员退出
	 *
	 * @param session
	 * @param type:用户类型 老师/学生
	 * @param sid 直播号
	 */
	@OnClose
	public void onClose(Session session, @PathParam("sid") String sid, @PathParam("type") Integer type, @PathParam("userId") Integer userId) {
		List<Session> sessionList = groupMemberInfoMap.get(sid);
		sessionList.remove(session);
		Set<Integer> onlineUserList = onlineUserMap.get(sid);
		onlineUserList.remove(userId);
		
		// 发送离线通知
//		sendInfo(sid, type, userId, onlineUserList.size(), "下线了~");
		
		log.info("Connection closed");
		log.info("sid: {}, sessionList size: {}", sid, sessionList.size());
	}
	
	/**
	 * 传输消息错误调用的方法
	 *
	 * @param error
	 */
	@OnError
	public void onError(Throwable error) {
		log.info("Connection error"+error.getMessage());
	}
}
