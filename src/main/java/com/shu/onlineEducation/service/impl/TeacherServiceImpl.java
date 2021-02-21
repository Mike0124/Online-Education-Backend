package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.common.dto.TeacherDto;
import com.shu.onlineEducation.dao.TeacherJpaRepository;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeacherJpaRepository teacherJpaRepository;
	
	@Override
	public boolean phoneValid(String phoneId) {
		return teacherJpaRepository.existsByPhoneId(phoneId);
	}
	
	@Override
	public Teacher getTeacherById(Integer userId) {
		return teacherJpaRepository.findTeacherByUserId(userId);
	}
	
	@Override
	public List<Teacher> getAllTeachers() {
		return teacherJpaRepository.findAll();
	}
	
	@Override
	public void addUser(String phoneId, String password) throws ExistedException {
		if (teacherJpaRepository.existsByPhoneId(phoneId)) {
			throw new ExistedException(ResultCode.USER_HAS_EXISTED);
		}
		Teacher teacher = new Teacher();
		teacher.setPhoneId(phoneId);
		teacher.setPassword(password);
		teacherJpaRepository.save(teacher);
	}
	
	@Override
	public void deleteTeacherById(Integer userId) {
		teacherJpaRepository.deleteTeacherByUserId(userId);
	}
	
	@Override
	public void completeTeacherInfo(Integer userId, TeacherDto teacherDto) throws NotFoundException {
		Teacher tea = teacherJpaRepository.findTeacherByUserId(userId);
		if (tea == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		tea.setName(teacherDto.getName());
		tea.setSex(teacherDto.getSex());
		tea.setSchool(teacherDto.getSchool());
		tea.setMajorId(teacherDto.getMajorId());
		tea.setTeacherPicUrl(teacherDto.getPicUrl());
		tea.setIntro(teacherDto.getIntro());
		teacherJpaRepository.save(tea);
	}
	
	@Override
	public Teacher loginByPassword(String phoneId, String password) throws NotFoundException, ParamErrorException {
		if (!teacherJpaRepository.existsByPhoneId(phoneId)) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		if (!password.equals(teacherJpaRepository.findTeacherByPhoneId(phoneId).getPassword())) {
			throw new ParamErrorException(ResultCode.USER_LOGIN_ERROR);
		}
		return teacherJpaRepository.findTeacherByPhoneId(phoneId);
	}
	
	@Override
	public void updateTeacherStatusById(Integer teacherId, Integer status) throws NotFoundException {
		Teacher teacher = teacherJpaRepository.findTeacherByUserId(teacherId);
		if (teacher == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		teacher.setTeacherStatus(status);
		teacherJpaRepository.saveAndFlush(teacher);
	}
	
	@Override
	public Page<Teacher> getTeacherByStatus(Pageable pageable, Integer status) {
		return teacherJpaRepository.findByTeacherStatus(pageable, 0);
	}
}
