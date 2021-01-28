package com.shu.onlineEducation.utils.status;

/**
 * @author 黄悦麒
 * 学生未完成：
 * 未上传：0		学生可上传、保存
 * 已截止：1
 * 已驳回：2		学生可编辑
 * 学生已完成：
 * 已上传：3		学生可编辑		教师可批改(打分并回复)/驳回
 * 已批改：4		学生可查看		教师可修改
 */
public class HomeworkStatus {
	public static final int NOT_UPLOADED = 0;
	public static final int HAS_CLOSED = 1;
	public static final int HAS_REJECTED = 2;
	public static final int HAS_UPLOADED = 3;
	public static final int HAS_CORRECTED = 4;
}

