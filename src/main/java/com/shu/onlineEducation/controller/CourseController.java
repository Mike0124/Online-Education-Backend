package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.common.dto.course.CourseChapterVideoDto;
import com.shu.onlineEducation.common.dto.course.CourseDto;
import com.shu.onlineEducation.properties.AppProperties;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.Result;
import com.shu.onlineEducation.utils.Result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Course")
@Api(tags = "3-课程模块")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	AppProperties appProperties;
	
	//管理员、教师、学生
	@GetMapping("/getClass")
	@ApiOperation(value = "获取所有课程详情")
	@ResponseBody
	public Iterable<Course> showInfo() {
		return courseService.getAllCourses();
	}
	
	@PostMapping("/getCourseByPreferId")
	@ApiOperation(value = "获取此偏好的所有课程,1表示按时间最新排序，2表示按课程评分排序，3表示按课程观看数量排序")
	@ResponseBody
	public Result getCourseByPreferId(Integer page, Integer sort, Integer preferId) throws NotFoundException {
		Pageable pageable;
		if (sort == 1) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
			return Result.success(courseService.getAllCoursesByPreferId(pageable, preferId));
		} else if (sort == 2) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseAvgMark"));
			return Result.success(courseService.getAllCoursesByPreferId(pageable, preferId));
		} else if (sort == 3) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseWatches"));
			return Result.success(courseService.getAllCoursesByPreferId(pageable, preferId));
		}
		return Result.failure(ResultCode.PARAM_IS_INVALID);
	}
	
	@PostMapping("/getCourseByNeedVipAndPreferId")
	@ApiOperation(value = "获取此偏好的所有免费/付费课程,1表示按时间最新排序，2表示按课程评分排序，3表示按课程观看数量排序")
	@ResponseBody
	public Result getCourseByNeedVipAndPreferId(Integer page, Integer sort, Integer preferId, Boolean needVip) throws NotFoundException {
		Pageable pageable;
		if (sort == 1) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
			return Result.success(courseService.getAllCoursesByNeedVipAndPreferId(pageable, needVip, preferId));
		} else if (sort == 2) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseAvgMark"));
			return Result.success(courseService.getAllCoursesByNeedVipAndPreferId(pageable, needVip, preferId));
		} else if (sort == 3) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseWatches"));
			return Result.success(courseService.getAllCoursesByNeedVipAndPreferId(pageable, needVip, preferId));
		}
		return Result.failure(ResultCode.PARAM_IS_INVALID);
	}
	
	
	@PostMapping("/getCourseByTeacherId")
	@ApiOperation(value = "获取老师所有课程信息")
	@ResponseBody
	public Result getCourseByTeacherId(Integer page, Integer sort, Integer teacherId) {
		Pageable pageable;
		if (sort == 1) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
			return Result.success(courseService.getAllCoursesByTeacherId(pageable, teacherId));
		} else if (sort == 2) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseAvgMark"));
			return Result.success(courseService.getAllCoursesByTeacherId(pageable, teacherId));
		} else if (sort == 3) {
			pageable = PageRequest.of(page - 1, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseWatches"));
			return Result.success(courseService.getAllCoursesByTeacherId(pageable, teacherId));
		}
		return Result.failure(ResultCode.PARAM_IS_INVALID);
	}
	@PostMapping("/getCourseDisplay")
	@ApiOperation(value = "获取课程展示信息")
	@ResponseBody
	public Result getCourseDisplay(Integer courseId) throws NotFoundException {
		return Result.success(courseService.getCourseDisplay(courseId));
	}
	
	//-----管理员、教师------
	//课程
	@PostMapping("/addCourse")
	@ApiOperation(value = "添加课程")
	@ResponseBody
	public Result addCourse(@RequestBody CourseDto courseDto) throws NotFoundException {
		courseService.addCourse(courseDto);
		return Result.success();
	}
	
	@PostMapping("/completeCourseInfo")
	@ApiOperation(value = "更新课程信息")
	@ResponseBody
	public Result completeCourseInfo(@RequestParam Integer courseId, @RequestBody CourseDto courseDto) throws NotFoundException {
		courseService.updateCourse(courseId,courseDto);
		return Result.success();
	}
	
	/*课程章节*/
	@PostMapping("/addCourseChapter")
	@ApiOperation(value = "添加/修改课程章节")
	@ResponseBody
	public Result addCourseChapter(Integer courseId, Integer chapterId, String intro) throws NotFoundException {
		courseService.addCourseChapter(courseId, chapterId, intro);
		return Result.success();
	}
	
	@GetMapping("/getCourseChapter/{courseId}")
	@ApiOperation(value = "获取该课程所有章节")
	@ResponseBody
	public Result getCourseChapterByCourseId(Integer courseId) throws NotFoundException {
		return Result.success(courseService.getAllCourseChapterByCourseId(courseId));
	}
	
	/*课程章节视频*/
	@PostMapping("/getCourseChapterViedo")
	@ApiOperation(value = "获取课程章节所有视频")
	@ResponseBody
	public Result getCourseChapterVideoByCourseChapter(Integer courseId, Integer chapterId) {
		return Result.success(courseService.getCourseChapterVideoByCourseChapter(courseId, chapterId));
	}
	
	@PostMapping("/addCourseChapterViedo")
	@ApiOperation(value = "添加/更新课程章节视频")
	@ResponseBody
	public Result addCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId, @RequestBody CourseChapterVideoDto courseChapterVideoDto) throws NotFoundException {
		courseService.addCourseChapterVideo(courseId, chapterId, videoId, courseChapterVideoDto.getVideoUrl(), courseChapterVideoDto.getVideoName());
		return Result.success();
	}
	
	@PostMapping("/deleteCourseChapterViedo")
	@ApiOperation(value = "删除课程章节视频")
	@ResponseBody
	public Result deleteCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId) {
		courseService.deleteCourseChapterVideo(courseId, chapterId, videoId);
		return Result.success();
	}
	
	//-----管理员------
	@PostMapping("/updateCourseStatus")
	@ApiOperation(value = "更新课程状态")
	@ResponseBody
	public Result updateCourseStatus(Integer courseId, Integer status) throws NotFoundException {
		courseService.updateCourseStatusById(courseId, status);
		return Result.success();
	}
	
	@PostMapping("/deleteCourseById")
	@ApiOperation(value = "删除课程")
	@ResponseBody
	public Result deleteCourseById(Integer courseId) {
		courseService.deleteCourseById(courseId);
		return Result.success();
	}
	
	@PostMapping("/deleteCourseChapter")
	@ApiOperation(value = "删除课程章节")
	@ResponseBody
	public Result deleteCourseChapter(Integer courseId, Integer chapterId) {
		courseService.deleteCourseChapter(courseId, chapterId);
		return Result.success();
	}
}
