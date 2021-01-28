package com.shu.onlineEducation.utils.status;

/**
 * @author 黄悦麒
 * 课程状态：
 * 申请开设：0	教师可申请删除		管理员可同意开设/拒绝
 * 成功开设：1	教师可申请删除
 * 驳回开设：2	教师可申请开设/删除
 * 申请删除：3	教师可申请删除		管理员可同意删除
 */
public class CourseStatus {
	public static final int APPLY_OPEN = 0;
	public static final int HAS_CONSENTED = 1;
	public static final int NOT_CONSENTED = 2;
	public static final int APPLY_DELETE = 3;
}
