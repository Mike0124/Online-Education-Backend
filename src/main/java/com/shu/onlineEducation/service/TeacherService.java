package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.Teacher;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.ExceptionUtil.ParamErrorException;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();         //获得所有教师信息

    boolean phoneValid(String phoneId);       //检查手机号是否被注册

    void addUser(String phoneId, String password) throws ExistedException;      //添加刚注册的教师

    void deleteTeacherById(int userId) throws NotFoundException;        //删除教师

    void completeTeacherInfo(int userId, String name, String sex, String school, String major)
            throws NotFoundException;//完善教师信息
    Teacher loginByPassword(String phoneId, String password) throws NotFoundException, ParamErrorException;
}
