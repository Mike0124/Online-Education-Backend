package com.shu.online_education.Service;

import com.shu.online_education.Entity.TeacherInfo;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;

import java.util.List;

public interface TeacherService {
    boolean phone_valid(String phone_id);
    List<TeacherInfo> getAllTeachers();
    void addUser(String phone_id,String password) throws UserHasExistedException;
    void deleteTeacherById(int userId) throws UserNotFoundException;
}
