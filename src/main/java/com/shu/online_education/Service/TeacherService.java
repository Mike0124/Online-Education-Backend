package com.shu.online_education.Service;

import com.shu.online_education.Entity.TeacherInfo;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;

import java.util.List;

public interface TeacherService {

    List<TeacherInfo> getAllTeachers();         //获得所有教师信息

    boolean phone_valid(String phone_id);       //检查手机号是否被注册

    void addUser(String phone_id, String password) throws UserHasExistedException;      //添加刚注册的教师

    void deleteTeacherById(int userId) throws UserNotFoundException;        //删除教师

    void completeTeacherInfo(int userId, String name, String sex, String school, String major)
            throws UserNotFoundException;//完善教师信息
}
