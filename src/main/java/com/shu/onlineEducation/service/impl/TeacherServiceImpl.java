package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.TeacherJpaRepository;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
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
            throw new ExistedException(2001,"用户已存在");
        }
        teacherJpaRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(int userId) throws NotFoundException {
        if (!teacherJpaRepository.existsByUserId(userId)) {
            throw new NotFoundException(2002, "用户不存在");
        }
        teacherJpaRepository.deleteTeacherByUserId(userId);
    }

    @Override
    public void completeTeacherInfo(int userId, String name, String sex, String school, String major) throws NotFoundException {
        Teacher tea = teacherJpaRepository.findTeacherByUserId(userId);
        if (tea == null){
            throw new NotFoundException(2002, "用户不存在");
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
            throw new NotFoundException(2002, "用户不存在");
        }
        if(!password.equals(teacherJpaRepository.findTeacherByPhoneId(phoneId).getPassword())) {
            throw new ParamErrorException(2003, "密码错误");
        }
        return teacherJpaRepository.findTeacherByPhoneId(phoneId);
    }
}
