package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.dao.MajorJpaRepository;
import com.shu.onlineEducation.dao.PreferJpaRepository;
import com.shu.onlineEducation.entity.Major;
import com.shu.onlineEducation.common.dto.MajorDto;
import com.shu.onlineEducation.entity.Prefer;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/Major")
@Api(tags = "8-专业模块")
public class MajorController {
	
	@Autowired
	MajorJpaRepository majorJpaRepository;
	@Autowired
	PreferJpaRepository preferJpaRepository;
	
	@GetMapping("/findAllMajor")
	@ApiOperation("获取所有的专业")
	@ResponseBody
	public Result findAllMajor() {
		return Result.success(majorJpaRepository.findAll());
	}
	
	@PostMapping("/addMajor")
	@ApiOperation("添加专业")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result addMajor(@RequestParam("major_content") String major, @RequestHeader("Authorization") String jwt) throws ExistedException {
		if (majorJpaRepository.existsByMajorContent(major)) {
			throw new ExistedException(ResultCode.MAJOR_HAS_EXISTED);
		}
		Major majorTmp = new Major();
		majorTmp.setMajorContent(major);
		majorJpaRepository.saveAndFlush(majorTmp);
		return Result.success();
	}
	
	@PostMapping("/deleteMajor")
	@ApiOperation("删除专业")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result deleteMajor(@RequestParam("major_id") int majorId, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		if (!majorJpaRepository.existsByMajorId(majorId)) {
			throw new NotFoundException(ResultCode.MAJOR_NOT_FOUND);
		}
		majorJpaRepository.deleteMajorByMajorId(majorId);
		return Result.success();
	}
	
	@PostMapping("/updateMajor")
	@ApiOperation("更新专业信息")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result updateMajor(@RequestBody MajorDto majorDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		if (!majorJpaRepository.existsByMajorId(majorDto.getMajorId())) {
			throw new NotFoundException(ResultCode.MAJOR_NOT_FOUND);
		}
		Major major = majorJpaRepository.findMajorByMajorId(majorDto.getMajorId());
		major.setMajorContent(majorDto.getMajorContent());
		majorJpaRepository.saveAndFlush(major);
		return Result.success();
	}
	
	@PostMapping("/addPrefer")
	@ApiOperation("添加子专业")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result addPrefer(@RequestParam("major_id") Integer majorId, @RequestParam("prefer_content") String preferContent, @RequestHeader("Authorization") String jwt) throws ExistedException, NotFoundException {
		Prefer prefer = preferJpaRepository.findByPreferContent(preferContent);
		if (prefer != null) {
			throw new ExistedException(ResultCode.PARAM_IS_INVALID);
		}
		Major major = majorJpaRepository.findMajorByMajorId(majorId);
		if (major == null) {
			throw new NotFoundException(ResultCode.MAJOR_NOT_FOUND);
		}
		prefer = new Prefer();
		prefer.setMajorId(major.getMajorId());
		prefer.setPreferContent(preferContent);
		preferJpaRepository.saveAndFlush(prefer);
		return Result.success();
	}
	
	@PostMapping("/deletePrefer")
	@ApiOperation("删除子专业")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result deletePrefer(@RequestParam("prefer_id") int preferId, @RequestHeader("Authorization") String jwt) {
		preferJpaRepository.deleteById(preferId);
		return Result.success();
	}
	
	@PostMapping("/updatePrefer")
	@ApiOperation("更新子专业信息")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result updatePrefer(@RequestParam("prefer_id") int preferId, @RequestParam("major_id") Integer majorId, @RequestParam("prefer_content") String preferContent, @RequestHeader("Authorization") String jwt) throws NotFoundException, ExistedException {
		Prefer prefer = preferJpaRepository.findByPreferId(preferId);
		Major major = majorJpaRepository.findMajorByMajorId(majorId);
		if (prefer == null || major == null) {
			throw new NotFoundException(ResultCode.MAJOR_NOT_FOUND);
		}
		if (preferJpaRepository.findByPreferContent(preferContent) != null) {
			throw new ExistedException(ResultCode.PARAM_IS_INVALID);
		}
		prefer.setMajorId(major.getMajorId());
		prefer.setPreferContent(preferContent);
		preferJpaRepository.saveAndFlush(prefer);
		return Result.success();
	}
	
	@PostMapping("/getPreferByMajor")
	@ApiOperation("获取所有专业的子专业")
	@ResponseBody
	public Result getPreferByMajor(@RequestParam("major_id") Integer majorId) throws NotFoundException {
		Major major = majorJpaRepository.findMajorByMajorId(majorId);
		if (major == null) {
			throw new NotFoundException(ResultCode.MAJOR_NOT_FOUND);
		}
		return Result.success(preferJpaRepository.findByMajor(major));
	}
}
