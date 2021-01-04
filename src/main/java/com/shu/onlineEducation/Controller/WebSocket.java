package com.shu.onlineEducation.Controller;


import com.shu.onlineEducation.Dao.StudentJpaRepository;
import com.shu.onlineEducation.Entity.Student;
import com.shu.onlineEducation.utils.Result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
@ServerEndpoint(value = "/websocket/{sid}/{StudentId}")   //房间号、类型（1，直播聊天）、用户Id、
@Component
public class WebSocket {
	private static WebSocket webSocket;
	private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);
	//当前连接数
	private static int onlineCount = 0;
	//存放每个客户端对应的MyWebSocket对象。
	private static final CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();
	//与某个客户端的连接会话
	private Session session;
	//客户端唯一标识sid(直播ID)
	private String sid = "";
	//学生ID
	private Integer studentId = 0;
	//用户昵称
	private String nickName = "";
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
	
	@PostConstruct
	public void init() {
		webSocket = this;
		webSocket.studentJpaRepository = this.studentJpaRepository;
	}
	
	//连接建立成功调用的方法
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid, @PathParam("studentId") Integer studentId) {
		moreWindow(sid, studentId);
		//在线数加1
		addOnlineCount();
		this.session = session;
		//加入set中
		webSocketSet.add(this);
		this.sid = sid;
		this.studentId = studentId;
		Student student = WebSocket.webSocket.studentJpaRepository.findStudentByUserId(studentId);
		this.nickName = student.getNickName();
		logger.info("用户ID:" + studentId + "用户昵称:" + nickName + "新连接：sid=" + sid + " 当前在线人数" + getOnlineCount());
		try {
			sendMessage("连接成功");
		} catch (IOException e) {
			logger.error("websocket IO异常");
		}
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		//群发消息
		for (WebSocket item : webSocketSet) {
			try {
				if (item.sid.equals(this.sid)) {
					item.sendMessage(message);
					logger.info("--------------------" + message + "总人数" + onlineCount);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 同一用户打开多个窗口问题
	public void moreWindow(String sid, Integer studentId) {
		if (webSocketSet.isEmpty()) {
			return;
		}
		for (WebSocket item : webSocketSet) {
			if (item.sid.equals(sid) && item.studentId.equals(studentId)) {
				//已经有相同的了
				webSocketSet.remove(item);
				subOnlineCount();
			}
		}
	}
	
	//发送消息给指定用户
	public static void sendMessage(String message, @PathParam("sid") String sid) {
		logger.info("发送消息：sid=" + sid + " message：" + message);
		for (WebSocket item : webSocketSet) {
			try {
				if (sid == null) {
					item.sendMessage(message);
					logger.info("+++++++++++++++" + message);
				} else if (item.sid.equals(sid)) {
					logger.info("开始发送消息：sid=" + sid);
					item.sendMessage(message);
				}
			} catch (IOException e) {
				logger.error("发送消息失败 sid:" + sid, e);
			}
		}
	}
	
	@OnClose
	public void onClose() {
		logger.info("连接关闭：sid=" + sid + " 当前在线人数" + getOnlineCount());
		webSocketSet.remove(this);
		subOnlineCount();
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}
	
	//当前在线人数
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}
	
	// 添加在线人数
	public static synchronized void addOnlineCount() {
		WebSocket.onlineCount++;
	}
	
	//减少在线人数
	public static synchronized void subOnlineCount() {
		if (WebSocket.onlineCount <= 0) {
			WebSocket.onlineCount = 0;
			return;
		}
		WebSocket.onlineCount--;
	}
	
	//人数列表
	@PostMapping(value = "api/WebSocket/studentList")
	@ResponseBody
	public Result studentList(@RequestParam(value = "sid") String sid) {
		Map map = new HashMap<>();
		List<Student> studentList = new ArrayList<>();
		Integer count = 0;
		for (WebSocket item : webSocketSet) {
			if (item.sid != null && item.sid.equals(sid)) {
				Student student = new Student();
				student.setNickName(item.nickName);
				student.setUserId(item.studentId);
				studentList.add(student);
				count++;
			}
		}
		map.put("studentList", studentList);
		map.put("count", count);
		return Result.success(map);
	}
}

