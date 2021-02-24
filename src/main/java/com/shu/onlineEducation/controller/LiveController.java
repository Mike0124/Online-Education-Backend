package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.common.dto.LiveDto;
import com.shu.onlineEducation.dao.LiveAddressJpaRepository;
import com.shu.onlineEducation.dao.LiveJpaRepository;
import com.shu.onlineEducation.properties.AppProperties;
import com.shu.onlineEducation.service.LiveService;
import com.shu.onlineEducation.utils.DateUtil;
import com.shu.onlineEducation.utils.ExceptionUtil.ExistedException;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.MapUtil;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
@RestController
@RequestMapping("/api/Live")
@Api(tags = "6-直播模块")
public class LiveController {
	
	@Autowired
	LiveService liveService;
	
	@Autowired
	LiveJpaRepository liveJpaRepository;
	
	@Autowired
	LiveAddressJpaRepository liveAddressJpaRepository;
	
	@Autowired
	AppProperties appProperties;
	
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
	
	@PostMapping("/modifyLiveAddress")
	@ResponseBody
	@ApiOperation(value = "修改直播地址信息")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public Result modifyLive(@RequestParam("liveAddressId") Integer liveAddressId, String address, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		liveService.modifyLiveAddress(liveAddressId, address);
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
	
	@GetMapping("/findAllValidLive")
	@ResponseBody
	@ApiOperation(value = "查找所有直播")
	public Result findAllValidLive(Integer page) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page());
		Timestamp timestamp = DateUtil.getNowTimeStamp();
		Date liveDate = new Date(timestamp.getTime());
		String[] time = DateUtil.timestampToString(timestamp).split("\\s+");
		String[] hour = time[1].split(":");
		Integer liveArrange = (Integer.parseInt(hour[0]) - 8) / 2 + 1;
		return Result.success(MapUtil.pageResponse(liveService.findAllValidLive(pageable, liveDate, liveArrange)));
	}
	
	@GetMapping("/findAllValidLiveNow")
	@ResponseBody
	@ApiOperation(value = "查找所有正在直播的课程")
	public Result findAllValidLiveNow(Integer page) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page());
		Timestamp timestamp = DateUtil.getNowTimeStamp();
		Date liveDate = new Date(timestamp.getTime());
		String[] time = DateUtil.timestampToString(timestamp).split("\\s+");
		String[] hour = time[1].split(":");
		Integer liveArrange = (Integer.parseInt(hour[0]) - 8) / 2 + 1;
		return Result.success(MapUtil.pageResponse(liveService.findAllValidLiveNow(pageable, liveDate, liveArrange)));
	}
	
	@GetMapping("/findAllValidLiveFuture")
	@ResponseBody
	@ApiOperation(value = "查找所有当天即将直播的课程")
	public Result findAllValidLiveFuture(Integer page) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.ASC, "live_arrange"));
		Timestamp timestamp = DateUtil.getNowTimeStamp();
		Date liveDate = new Date(timestamp.getTime());
		String[] time = DateUtil.timestampToString(timestamp).split("\\s+");
		String[] hour = time[1].split(":");
		Integer liveArrange = (Integer.parseInt(hour[0]) - 8) / 2 + 1;
		return Result.success(MapUtil.pageResponse(liveService.findAllValidLiveFuture(pageable, liveDate, liveArrange)));
	}
	
	
	@PostMapping("/findAllValidLiveNowByTeacher")
	@ResponseBody
	@ApiOperation(value = "查找所有该老师正在直播的课程")
	public Result findAllValidLiveNowByTeacher(Integer teacherId) {
		Timestamp timestamp = DateUtil.getNowTimeStamp();
		Date liveDate = new Date(timestamp.getTime());
		String[] time = DateUtil.timestampToString(timestamp).split("\\s+");
		String[] hour = time[1].split(":");
		Integer liveArrange = (Integer.parseInt(hour[0]) - 8) / 2 + 1;
		return Result.success(liveService.findAllValidLiveNowByTeacher(teacherId, liveDate, liveArrange));
	}
	
	@PostMapping("/findAllValidLiveFutureByTeacher")
	@ResponseBody
	@ApiOperation(value = "查找所有该老师即将直播的课程")
	public Result findAllValidLiveFutureByTeacher(Integer teacherId) {
		Timestamp timestamp = DateUtil.getNowTimeStamp();
		Date liveDate = new Date(timestamp.getTime());
		String[] time = DateUtil.timestampToString(timestamp).split("\\s+");
		String[] hour = time[1].split(":");
		Integer liveArrange = (Integer.parseInt(hour[0]) - 8) / 2 + 1;
		return Result.success(liveService.findAllValidLiveFutureByTeacher(teacherId, liveDate, liveArrange));
	}
	
	@GetMapping("/findAllLiveAddress")
	@ResponseBody
	@ApiOperation(value = "查找所有直播地址")
	public Result findAllLiveAddress() {
		return Result.success(liveAddressJpaRepository.findAll());
	}
}
