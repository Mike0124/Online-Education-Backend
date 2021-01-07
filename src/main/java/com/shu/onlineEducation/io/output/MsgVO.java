package com.shu.onlineEducation.io.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "websocket消息内容")
public class MsgVO {
	
	@ApiModelProperty(value = "userId")
	private Integer userId;
	
	@ApiModelProperty(value = "type")
	private Integer type;
	
	@ApiModelProperty(value = "nickName")
	private String nickName;
	
	@ApiModelProperty(value = "msg")
	private String msg;
}