package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.dao.MajorJpaRepository;
import com.shu.onlineEducation.entity.Major;
import com.shu.onlineEducation.common.dto.MajorDto;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/Major")
@Api(tags = "8-专业模块")
public class MajorController {

    @Autowired
    MajorJpaRepository majorJpaRepository;

    @GetMapping("/findAllMajor")
    @ApiOperation("查询所有的专业")
    @ResponseBody
    public Result findAllMajor(){
        return Result.success(majorJpaRepository.findAll());
    }

    @PostMapping("/addMajor")
    @ApiOperation("添加专业")
    @ResponseBody
    public Result addMajor(@RequestParam("major_content") String major) throws ExistedException {
        if (majorJpaRepository.existsByMajorContent(major)){
            throw new ExistedException(ResultCode.MAJOR_HAS_EXISTED);
        }
        Major majorTmp = new Major();
        majorTmp.setMajorContent(major);
        majorJpaRepository.saveAndFlush(majorTmp);
        return Result.success();
    }

    @PostMapping("/deleteMajor")
    @ApiOperation("删除专业")
    @ResponseBody
    public Result deleteMajor(@RequestParam("major_id") int majorId) throws NotFoundException{
        if (!majorJpaRepository.existsByMajorId(majorId)){
            throw new NotFoundException(ResultCode.MAJOR_NOT_FOUND);
        }
        majorJpaRepository.deleteMajorByMajorId(majorId);
        return Result.success();
    }

    @PostMapping("/updateMajor")
    @ApiOperation("更新专业信息")
    @ResponseBody
    public Result updateMajor(@RequestBody MajorDto majorDto) throws NotFoundException{
        if (!majorJpaRepository.existsByMajorId(majorDto.getMajorId())){
            throw new NotFoundException(ResultCode.MAJOR_NOT_FOUND);
        }
        Major major = majorJpaRepository.findMajorByMajorId(majorDto.getMajorId());
        major.setMajorContent(majorDto.getMajorContent());
        majorJpaRepository.saveAndFlush(major);
        return Result.success();
    }
}
