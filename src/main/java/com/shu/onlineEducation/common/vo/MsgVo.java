package com.shu.onlineEducation.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author 黄悦麒
 * @className StudentInfo
 * @description 便于扩展websocket传递给前端的信息
 * @date 2021/1/7
 */
@Data
@ApiModel(description = "websocket消息内容")
public class MsgVo {
	
	@ApiModelProperty(value = "userId")
	private Integer userId;
	
	@ApiModelProperty(value = "type")
	private Integer type;
	
	@ApiModelProperty(value = "nickName")
	private String nickName;
	
	@ApiModelProperty(value = "isVip")
	private boolean isVip;
	
	@ApiModelProperty(value = "msg")
	private String msg;
}
