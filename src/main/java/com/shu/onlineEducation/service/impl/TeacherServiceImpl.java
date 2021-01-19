package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.TeacherJpaRepository;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Teacher> getAllTeachers() {
        return teacherJpaRepository.findAll();
    }

    @Override
    public void addUser(String phoneId, String password) throws ExistedException {
        Teacher teacher = new Teacher();
        teacher.setPhoneId(phoneId);
        teacher.setPassword(password);
        if (teacherJpaRepository.existsByPhoneId(phoneId)) {
            throw new ExistedException(ResultCode.USER_HAS_EXISTED);
        }
        teacherJpaRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(int userId) throws NotFoundException {
        if (!teacherJpaRepository.existsByUserId(userId)) {
            throw new NotFoundException(ResultCode.USER_NOT_EXIST);
        }
        teacherJpaRepository.deleteTeacherByUserId(userId);
    }

    @Override
    public void completeTeacherInfo(int userId, String name, String sex, String school, String major) throws NotFoundException {
        Teacher tea = teacherJpaRepository.findTeacherByUserId(userId);
        if (tea == null){
            throw new NotFoundException(ResultCode.USER_NOT_EXIST);
        }
        tea.setName(name);
        tea.setSex(sex);
        tea.setSchool(school);
        tea.setMajorId(major);
        teacherJpaRepository.save(tea);
    }
    
    @Override
    public Teacher loginByPassword(String phoneId, String password) throws NotFoundException, ParamErrorException {
        if (!teacherJpaRepository.existsByPhoneId(phoneId)) {
            throw new NotFoundException(ResultCode.USER_NOT_EXIST);
        }
        if(!password.equals(teacherJpaRepository.findTeacherByPhoneId(phoneId).getPassword())) {
            throw new ParamErrorException(ResultCode.USER_LOGIN_ERROR);
        }
        return teacherJpaRepository.findTeacherByPhoneId(phoneId);
    }
}
