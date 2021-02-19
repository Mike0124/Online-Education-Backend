package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.LiveDto;
import com.shu.onlineEducation.dao.LiveJpaRepository;
import com.shu.onlineEducation.service.LiveService;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Slf4j
@RestController
@RequestMapping("/api/Live")
@Api(tags = "6-直播模块")
public class LiveController {
	
	@Autowired
	LiveService liveService;
	
	@Autowired
	LiveJpaRepository liveJpaRepository;
	
	@PostMapping("/findArrangeIsValidInDay")
	@ApiOperation(value = "查看当天的预约情况")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	public Result findArrangeIsValidInDay(@RequestParam("liveDate") Date liveDate, @RequestHeader("Authorization") String jwt) {
		return Result.success(liveService.isValidInDay(liveDate));
	}
	
	@PostMapping("/addLive")
	@ResponseBody
	@ApiOperation(value = "添加直播")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	public Result addLive(@RequestBody LiveDto liveDto, @RequestHeader("Authorization") String jwt) throws ExistedException {
		liveService.addLive(liveDto);
		return Result.success();
	}
	
	@PostMapping("/modifyLive")
	@ResponseBody
	@ApiOperation(value = "修改直播信息")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	public Result modifyLive(@RequestParam("liveId") Integer liveId, @RequestBody LiveDto liveDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		liveService.modifyLive(liveDto, liveId);
		return Result.success();
	}
	
	@PostMapping("/deleteLive")
	@ResponseBody
	@ApiOperation(value = "删除直播")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	public Result deleteLive(@RequestParam("liveId") Integer liveId, @RequestHeader("Authorization") String jwt) {
		liveService.deleteLive(liveId);
		return Result.success();
	}
	
	@PostMapping("/findAllLiveByTeacher")
	@ResponseBody
	@ApiOperation(value = "查找老师的所有直播")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	public Result findAllLiveByTeacher(@RequestParam("teacherId") Integer teacherId, @RequestHeader("Authorization") String jwt) {
		return Result.success(liveService.findAllLiveByTeacherId(teacherId));
	}
	
	@PostMapping("/findAllLiveByLiveDate")
	@ResponseBody
	@ApiOperation(value = "查找某天的所有直播")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	public Result findAllLiveByDate(@RequestParam("liveDate") Date liveDate, @RequestHeader("Authorization") String jwt) {
		return Result.success(liveService.findAllLiveByDate(liveDate));
	}
	
}
