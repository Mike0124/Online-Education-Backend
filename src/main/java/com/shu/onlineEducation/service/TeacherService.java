package com.shu.onlineEducation.service;

import com.shu.onlineEducation.common.dto.TeacherDto;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
	Teacher getTeacherById(Integer userId);
	
	List<Teacher> getAllTeachers();         //获得所有教师信息
	
	boolean phoneValid(String phoneId);       //检查手机号是否被注册
	
	void addUser(String phoneId, String password) throws ExistedException;      //添加刚注册的教师
	
	void deleteTeacherById(Integer userId);        //删除教师
	
	void completeTeacherInfo(Integer userId, TeacherDto teacherDto)
			throws NotFoundException;//完善教师信息
	
	Teacher loginByPassword(String phoneId, String password) throws NotFoundException, ParamErrorException;
	
	void updateTeacherStatusById(Integer teacherId, Integer status) throws NotFoundException;
	
	Page<Teacher> getTeacherByStatus(Pageable pageable, Integer status);
}
