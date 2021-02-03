package com.shu.onlineEducation.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 黄悦麒
 * @className SecurityUser
 * @description 安全用户类
 * @date 2021/1/26
 */
@Data
@NoArgsConstructor
public class SecurityUser {
	private String phoneId;
	private String password;
	private String type;
	private List<String> roles;
	transient String token;
}
