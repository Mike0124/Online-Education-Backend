package com.shu.online_education.Service.impl;

import com.shu.online_education.Dao.TeacherJpaRepository;
import com.shu.online_education.Entity.TeacherInfo;
import com.shu.online_education.Service.TeacherService;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherJpaRepository teacherJpaRepository;

    @Override
    public List<TeacherInfo> getAllTeachers() {
        return teacherJpaRepository.findAll();
    }

    @Override
    public void addUser(String phone_id, String password) throws UserHasExistedException {
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setPhoneId(phone_id);
        teacherInfo.setPassword(password);
        if (teacherJpaRepository.existsByPhoneId(phone_id)) throw new UserHasExistedException();
        teacherJpaRepository.save(teacherInfo);
    }

    @Override
    public boolean phone_valid(String phone_id) {
        return teacherJpaRepository.existsByPhoneId(phone_id);
    }

    @Override
    public void deleteTeacherById(int userId) throws UserNotFoundException {
        if (!teacherJpaRepository.existsByUserId(userId)) throw new UserNotFoundException();
        teacherJpaRepository.deleteTeacherInfoByUserId(userId);
    }
}
