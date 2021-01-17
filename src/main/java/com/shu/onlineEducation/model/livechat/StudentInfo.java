package com.shu.onlineEducation.model.livechat;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.websocket.Session;

/**
*@className StudentInfo
*@description 便于扩展websocket传递给前端的信息
*@author 黄悦麒
*@date 2021/1/7
*/
@Data
@AllArgsConstructor
public class StudentInfo {
	private Integer userId;
	private Session session;
	private String nickName;
	private boolean isVip;
}
