package com.shu.onlineEducation.service.impl;

import com.shu.onlineEducation.dao.TeacherJpaRepository;
import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.service.TeacherService;
import com.shu.onlineEducation.utils.ExceptionUtil.PassWordErrorException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserHasExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.UserNotFoundException;
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
    public void addUser(String phoneId, String password) throws UserHasExistedException {
        Teacher teacher = new Teacher();
        teacher.setPhoneId(phoneId);
        teacher.setPassword(password);
        if (teacherJpaRepository.existsByPhoneId(phoneId)) {
            throw new UserHasExistedException();
        }
        teacherJpaRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(int userId) throws UserNotFoundException {
        if (!teacherJpaRepository.existsByUserId(userId)) {
            throw new UserNotFoundException();
        }
        teacherJpaRepository.deleteTeacherByUserId(userId);
    }

    @Override
    public void completeTeacherInfo(int userId, String name, String sex, String school, String major) throws UserNotFoundException {
        Teacher tea = teacherJpaRepository.findTeacherByUserId(userId);
        if (tea == null){
            throw new UserNotFoundException();
        }
        tea.setName(name);
        tea.setSex(sex);
        tea.setSchool(school);
        tea.setMajorId(major);
        teacherJpaRepository.save(tea);
    }
    
    @Override
    public Teacher loginByPassword(String phoneId, String password) throws UserNotFoundException,PassWordErrorException {
        if (!teacherJpaRepository.existsByPhoneId(phoneId)) {
            throw new UserNotFoundException();
        }
        if(!password.equals(teacherJpaRepository.findTeacherByPhoneId(phoneId).getPassword())) {
            throw new PassWordErrorException();
        }
        return teacherJpaRepository.findTeacherByPhoneId(phoneId);
    }
}
