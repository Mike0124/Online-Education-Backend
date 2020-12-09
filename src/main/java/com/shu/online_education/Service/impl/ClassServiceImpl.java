package com.shu.online_education.Service.impl;

import com.shu.online_education.Dao.ClassJpaRepository;
import com.shu.online_education.Entity.ClassInfo;
import com.shu.online_education.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassJpaRepository classJpaRepository;

    @Override
    public List<ClassInfo> getAllClasses() {
        return classJpaRepository.findAll();
    }
}
