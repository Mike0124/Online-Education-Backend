package com.shu.onlineEducation.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.websocket.Session;

@Data
@AllArgsConstructor
public class StudentChatVo {
	private Integer userId;
	private Session session;
	private String nickName;
	private boolean isVip;
}
