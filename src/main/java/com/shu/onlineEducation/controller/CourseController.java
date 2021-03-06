package com.shu.onlineEducation.controller;

import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.common.dto.course.CourseChapterVideoDto;
import com.shu.onlineEducation.common.dto.course.CourseDto;
import com.shu.onlineEducation.properties.AppProperties;
import com.shu.onlineEducation.service.CourseCommentService;
import com.shu.onlineEducation.service.CourseService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.MapUtil;
import com.shu.onlineEducation.utils.Result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Course")
@Api(tags = "3-课程模块")
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseCommentService courseCommentService;
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	@Autowired
	private AppProperties appProperties;
	
	//管理员、教师、学生、游客
	@GetMapping("/getClass")
	@ApiOperation(value = "获取所有课程详情")
	@ResponseBody
	public Iterable<Course> showInfo() {
		return courseService.getAllCourses();
	}
	
	@PostMapping("/getCourseById")
	@ApiOperation(value = "获取课程信息")
	@ResponseBody
	public Result getCourseById(Integer courseId) {
		return Result.success(courseJpaRepository.findByCourseId(courseId));
	}
	
	@PostMapping("/getAllCoursesOrderByWatches")
	@ApiOperation(value = "默认推荐课程信息")
	@ResponseBody
	public Result getAllCoursesOrderByWatches() {
		Pageable pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "courseWatches"));
		return Result.success(courseJpaRepository.findAllOrderBy(pageable));
	}
	
	@PostMapping("/getCourseByStatus0")
	@ApiOperation(value = "获取待审核的课程信息")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result getCourseByStatus0(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestHeader("Authorization") String jwt) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
		return Result.success(MapUtil.pageResponse(courseService.getCourseByStatus(pageable, 0)));
	}
	
	@PostMapping("/getCourseByTeacherAndStatus")
	@ApiOperation(value = "根据教师和课程状态获取课程")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result getCourseByTeacherAndStatus(@RequestParam(required = false, defaultValue = "1") Integer page, Integer teacherId, Integer status, @RequestHeader("Authorization") String jwt) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
		return Result.success(MapUtil.pageResponse(courseService.getCourseByTeacherAndStatus(pageable, teacherId, status)));
	}
	
	@PostMapping("/getCourseByMajorId")
	@ApiOperation(value = "获取此专业的所有课程	1.按时间最新排序，2.按课程评分排序，3.按课程观看数量排序")
	@ResponseBody
	public Result getCourseByMajorId(Integer page, @RequestParam(required = false, defaultValue = "3") Integer sort, Integer majorId) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable;
		switch (sort) {
			case (1):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "upload_time"));
				break;
			case (2):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "course_avg_mark"));
				break;
			case (3):
			default:
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "course_watches"));
		}
		return Result.success(MapUtil.pageResponse(courseService.getAllCoursesByMajorId(pageable, majorId)));
	}
	
	@PostMapping("/getCourseByMajorIdAndNeedVip")
	@ApiOperation(value = "获取此专业的所有免费/付费课程	1.按时间最新排序，2.按课程评分排序，3.按课程观看数量排序")
	@ResponseBody
	public Result getCourseByMajorIdAndNeedVip(Integer page, @RequestParam(required = false, defaultValue = "3") Integer sort, Integer majorId, Boolean needVip) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable;
		switch (sort) {
			case (1):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "upload_time"));
				break;
			case (2):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "course_avg_mark"));
				break;
			case (3):
			default:
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "course_watches"));
		}
		return Result.success(MapUtil.pageResponse(courseService.getAllCoursesByMajorIdAndNeedVip(pageable, majorId, needVip)));
	}
	
	@PostMapping("/getCourseByPreferId")
	@ApiOperation(value = "获取此偏好的所有课程	1.按时间最新排序，2.按课程评分排序，3.按课程观看数量排序")
	@ResponseBody
	public Result getCourseByPreferId(Integer page, @RequestParam(required = false, defaultValue = "3") Integer sort, Integer preferId) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable;
		switch (sort) {
			case (1):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
				break;
			case (2):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseAvgMark"));
				break;
			case (3):
			default:
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseWatches"));
		}
		return Result.success(MapUtil.pageResponse(courseService.getAllCoursesByPreferId(pageable, preferId)));
	}
	
	@PostMapping("/getCourseByNeedVipAndPreferId")
	@ApiOperation(value = "获取此偏好的所有免费/付费课程	1.按时间最新排序，2.按课程评分排序，3.按课程观看数量排序")
	@ResponseBody
	public Result getCourseByNeedVipAndPreferId(Integer page, @RequestParam(required = false, defaultValue = "3") Integer sort, Integer preferId, Boolean needVip) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable;
		switch (sort) {
			case (1):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
				break;
			case (2):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseAvgMark"));
				break;
			case (3):
			default:
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseWatches"));
		}
		return Result.success(MapUtil.pageResponse(courseService.getAllCoursesByNeedVipAndPreferId(pageable, needVip, preferId)));
	}
	
	
	@PostMapping("/getCourseByTeacherId")
	@ApiOperation(value = "获取老师所有课程信息	1.按时间最新排序，2.按课程评分排序，3.按课程观看数量排序")
	@ResponseBody
	public Result getCourseByTeacherId(Integer page, @RequestParam(required = false, defaultValue = "3") Integer sort, Integer teacherId) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable;
		switch (sort) {
			case (1):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
				break;
			case (2):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseAvgMark"));
				break;
			case (3):
			default:
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseWatches"));
		}
		return Result.success(MapUtil.pageResponse(courseService.getAllCoursesByTeacherId(pageable, teacherId)));
	}
	
	@PostMapping("/getCourseByTeacherIdAndStatus")
	@ApiOperation(value = "获取老师所有课程信息	1.按时间最新排序，2.按课程评分排序，3.按课程观看数量排序")
	@ResponseBody
	public Result getCourseByTeacherIdAndStatus(Integer page, @RequestParam(required = false, defaultValue = "3") Integer sort, Integer teacherId) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable;
		switch (sort) {
			case (1):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "uploadTime"));
				break;
			case (2):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseAvgMark"));
				break;
			case (3):
			default:
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "courseWatches"));
		}
		return Result.success(MapUtil.pageResponse(courseService.getAllCoursesByTeacherAndStatus(pageable, teacherId)));
	}
	
	@PostMapping("/getCourseDisplay")
	@ApiOperation(value = "获取课程展示信息")
	@ResponseBody
	public Result getCourseDisplay(Integer courseId) throws NotFoundException {
		return Result.success(courseService.getCourseDisplay(courseId));
	}
	
	@PostMapping("/getCourseComments")
	@ApiOperation(value = "获取课程评论	1.按时间最新排序，2.按点赞量排序，3.好评优先 4.差评优先")
	@ResponseBody
	public Result getCourseComments(Integer page, @RequestParam(required = false, defaultValue = "2") Integer sort, Integer courseId) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable;
		switch (sort) {
			case (1):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "time"));
				break;
			case (3):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "commentMark"));
				break;
			case (4):
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.ASC, "commentMark"));
				break;
			case (2):
			default:
				pageable = PageRequest.of(page, appProperties.getMax_rows_in_one_page(), Sort.by(Sort.Direction.DESC, "likes"));
		}
		return Result.success(MapUtil.pageResponse(courseCommentService.getCommentsByCourse(pageable, courseId)));
	}
	
	@PostMapping("/getRelatedCourses")
	@ApiOperation(value = "获取相关课程")
	@ResponseBody
	public Result getRelatedCourses(Integer courseId) throws NotFoundException {
		return Result.success(courseService.getRelatedCourses(courseId));
	}
	
	@PostMapping("/getCoursesWithRegex")
	@ApiOperation(value = "正则搜索课程")
	@ResponseBody
	public Result getCoursesWithRegex(@RequestParam(required = false, defaultValue = "1") Integer page, String query) {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "course_watches"));
		return Result.success(MapUtil.pageResponse(courseService.getCoursesWithRegex(pageable, query)));
	}
	
	@PostMapping("/getCourseCommentWithRegex")
	@ApiOperation(value = "正则搜索课程评论")
	@ResponseBody
	public Result getCourseCommentWithRegex(@RequestParam(required = false, defaultValue = "1") Integer page, Integer courseId, String query) throws NotFoundException {
		page = page < 1 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "likes"));
		return Result.success(MapUtil.pageResponse(courseCommentService.getCommentsByCourseWithRegex(pageable, courseId, query)));
	}
	
	//-----管理员、教师------
	//课程
	@PostMapping("/addCourse")
	@ApiOperation(value = "添加课程")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addCourse(@RequestBody CourseDto courseDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		courseService.addCourse(courseDto);
		return Result.success();
	}
	
	@PostMapping("/completeCourseInfo")
	@ApiOperation(value = "更新课程信息")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result completeCourseInfo(Integer courseId, @RequestBody CourseDto courseDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		courseService.updateCourse(courseId, courseDto);
		return Result.success();
	}
	
	/*课程章节*/
	@PostMapping("/addCourseChapter")
	@ApiOperation(value = "添加/修改课程章节")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addCourseChapter(Integer courseId, Integer chapterId, String intro, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		courseService.addCourseChapter(courseId, chapterId, intro);
		return Result.success();
	}
	
	@GetMapping("/getCourseChapter/{courseId}")
	@ApiOperation(value = "获取该课程所有章节")
	@ResponseBody
	public Result getCourseChapterByCourseId(@PathVariable("courseId") Integer courseId) throws NotFoundException {
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
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result addCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId, @RequestBody CourseChapterVideoDto courseChapterVideoDto, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		courseService.addCourseChapterVideo(courseId, chapterId, videoId, courseChapterVideoDto.getVideoUrl(), courseChapterVideoDto.getVideoName());
		return Result.success();
	}
	
	@PostMapping("/deleteCourseChapterViedo")
	@ApiOperation(value = "删除课程章节视频")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteCourseChapterVideo(Integer courseId, Integer chapterId, Integer videoId, @RequestHeader("Authorization") String jwt) {
		courseService.deleteCourseChapterVideo(courseId, chapterId, videoId);
		return Result.success();
	}
	
	//-----管理员------
	@PostMapping("/updateCourseStatus")
	@ApiOperation(value = "认证/驳回课程状态 1.认证成功  2.驳回")
//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result updateCourseStatus(Integer courseId, Integer status, @RequestHeader("Authorization") String jwt) throws NotFoundException {
		courseService.updateCourseStatusById(courseId, status);
		return Result.success();
	}
	
	@PostMapping("/deleteCourseById")
	@ApiOperation(value = "删除课程")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@ResponseBody
	public Result deleteCourseById(Integer courseId, @RequestHeader("Authorization") String jwt) {
		courseService.deleteCourseById(courseId);
		return Result.success();
	}
	
	@PostMapping("/deleteCourseChapter")
	@ApiOperation(value = "删除课程章节")
	@PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_ADMIN')")
	@ResponseBody
	public Result deleteCourseChapter(Integer courseId, Integer chapterId, @RequestHeader("Authorization") String jwt) {
		courseService.deleteCourseChapter(courseId, chapterId);
		return Result.success();
	}
}
