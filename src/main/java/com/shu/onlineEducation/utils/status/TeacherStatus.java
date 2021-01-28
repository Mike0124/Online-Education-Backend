package com.shu.onlineEducation.utils.status;

/**
 * @author 黄悦麒
 * 教师状态：
 * 申请认证：0	管理员可同意认证/拒绝
 * 成功认证：1
 * 驳回认证：2	教师可申请认证
 */
public class TeacherStatus {
	public static final int APPLY_CERTIFICATE = 0;
	public static final int HAS_CERTIFICATED = 1;
	public static final int NOT_CERTIFICATED = 2;
}
