package com.shu.online_education.Service.impl;

import com.shu.online_education.Dao.StudentJpaRepository;
import com.shu.online_education.Entity.StudentInfo;
import com.shu.online_education.Service.StudentService;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Override
    public List<StudentInfo> getAllStudents() {
        return studentJpaRepository.findAll();
    }

    @Override
    public boolean phone_valid(String phone_id) {
        if (studentJpaRepository.findStudentInfoByPhoneIdEquals(phone_id).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteStudentById(int userId) throws UserNotFoundException {
        if (studentJpaRepository.existsByUserId(userId) == false){
            throw new UserNotFoundException();
        }
        studentJpaRepository.deleteStudentInfoByUserId(userId);
    }
}
