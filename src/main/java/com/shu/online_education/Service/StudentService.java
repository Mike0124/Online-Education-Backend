package com.shu.online_education.Service;

import com.shu.online_education.Entity.StudentInfo;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;

import java.util.List;

public interface StudentService {

    List<StudentInfo> getAllStudents();

    boolean phone_valid(String phone_id);

    void deleteStudentById(int userId) throws UserNotFoundException;
}