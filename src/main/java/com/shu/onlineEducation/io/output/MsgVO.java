package com.shu.onlineEducation.io.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.alibaba.fastjson.JSONObject;

/**
 *  <p>  websocket消息内容 </p>
 *
 * @author：  zhengqing <br/>
 * @date：  2019/12/4$ 10:27$ <br/>
 * @version：  <br/>
 */
@Data
@ApiModel(description = "websocket消息内容")
public class MsgVO {
	
	@ApiModelProperty(value = "userId")
	private Integer userId;
	
	@ApiModelProperty(value = "userName")
	private String username;
	
	@ApiModelProperty(value = "msg")
	private String msg;
	
	@ApiModelProperty(value = "count")
	private int count;
	
}