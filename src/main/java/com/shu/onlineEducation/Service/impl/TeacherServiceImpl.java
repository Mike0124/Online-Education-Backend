package com.shu.onlineEducation.Service.impl;

import com.shu.onlineEducation.Dao.TeacherJpaRepository;
import com.shu.onlineEducation.Entity.Teacher;
import com.shu.onlineEducation.Service.TeacherService;
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
        if (teacherJpaRepository.existsByPhoneId(phoneId)) throw new UserHasExistedException();
        teacherJpaRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(int userId) throws UserNotFoundException {
        if (!teacherJpaRepository.existsByUserId(userId)) throw new UserNotFoundException();
        teacherJpaRepository.deleteTeacherInfoByUserId(userId);
    }

    @Override
    public void completeTeacherInfo(int userId, String name, String sex, String school, String major) throws UserNotFoundException {
        Teacher tea = teacherJpaRepository.findTeacherInfoByUserId(userId);
        if (tea == null){
            throw new UserNotFoundException();
        }
        tea.setName(name);
        tea.setSex(sex);
        tea.setSchool(school);
        tea.setMajor(major);
        teacherJpaRepository.save(tea);
    }
}
