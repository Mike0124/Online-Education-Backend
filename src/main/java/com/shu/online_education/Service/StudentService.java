package com.shu.online_education.Service;

import com.shu.online_education.Entity.StudentInfo;
import com.shu.online_education.utils.ExceptionUtil.ClassHasEnrolledException;
import com.shu.online_education.utils.ExceptionUtil.UserHasExistedException;
import com.shu.online_education.utils.ExceptionUtil.UserNotFoundException;

import java.util.List;

public interface StudentService {

    List<StudentInfo> getAllStudents();         //获取所有学生信息

    boolean phone_valid(String phone_id);       //判断手机号是否被注册

    void addUser(String phone_id, String password) throws UserHasExistedException;      //添加刚注册的学生

    void deleteStudentById(int userId) throws UserNotFoundException;        //删除学生

    void completeStudentInfo(int userId, String nickname, String sex, String school, String major, int grade)
            throws UserNotFoundException;       //完善学生信息

    //学生报名课程
    void enrollClassById(int userId, int classId) throws ClassHasEnrolledException;

}