package com.shu.onlineEducation.service.impl;

import com.alibaba.fastjson.JSON;
import com.shu.onlineEducation.common.dto.StudentDto;
import com.shu.onlineEducation.common.dto.course.CourseCommentDto;
import com.shu.onlineEducation.dao.*;
import com.shu.onlineEducation.entity.*;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreference;
import com.shu.onlineEducation.entity.EmbeddedId.StudentPreferencePK;
import com.shu.onlineEducation.service.CourseCommentService;
import com.shu.onlineEducation.service.StudentService;
import com.shu.onlineEducation.utils.DateUtil;
import com.shu.onlineEducation.utils.ExceptionUtil.*;
import com.shu.onlineEducation.utils.ItemBasedCF;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	@Autowired
	private StudentPreferenceRepository studentPreferenceRepository;
	@Autowired
	private CourseCommentJpaRepository courseCommentJpaRepository;
	@Autowired
	private MajorJpaRepository majorJpaRepository;
	@Autowired
	private StudentLikeCourseJpaRepository likeCourseJpaRepository;
	@Autowired
	private CourseCommentService courseCommentService;
	@Autowired
	private ItemCFResultJpaRepository itemCFResultJpaRepository;
	@Autowired
	private ItemCFJpaRepository itemCFJpaRepository;
	@Autowired
	private ItemBasedCF itemBasedCF;
	
	@Override
	public Student getStudentById(Integer userId) {
		return studentJpaRepository.findByUserId(userId);
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentJpaRepository.findAll();
	}
	
	@Override
	public boolean phoneValid(String phoneId) {
		return studentJpaRepository.existsByPhoneId(phoneId);
	}
	
	@Override
	public void addUser(String phoneId, String password) throws ExistedException {
		if (studentJpaRepository.existsByPhoneId(phoneId)) {
			throw new ExistedException(ResultCode.USER_HAS_EXISTED);
		}
		Student student = new Student();
		student.setPhoneId(phoneId);
		student.setPassword(password);
		studentJpaRepository.save(student);
	}
	
	@Override
	public void deleteStudentById(Integer userId) {
		studentJpaRepository.deleteStudentByUserId(userId);
	}
	
	@Override
	public Student loginByPassword(String phoneId, String password) throws NotFoundException, ParamErrorException {
		Student student = studentJpaRepository.findByPhoneId(phoneId);
		if (student == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		if (!password.equals(student.getPassword())) {
			throw new ParamErrorException(ResultCode.USER_LOGIN_ERROR);
		}
		return student;
	}
	
	@Override
	public void completeStudent(Integer userId, StudentDto studentDto) throws NotFoundException {
		Student stu = studentJpaRepository.findByUserId(userId);
		Major major = majorJpaRepository.findMajorByMajorId(studentDto.getMajorId());
		if (stu == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		if (major == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		stu.setNickName(studentDto.getNickname());
		stu.setSex(studentDto.getSex());
		stu.setSchool(studentDto.getSchool());
		stu.setMajorId(major.getMajorId());
		stu.setGrade(studentDto.getGrade());
		stu.setStudentPicUrl(studentDto.getPicUrl());
		studentJpaRepository.save(stu);
	}
	
	@Override
	public void commentCourseByCourseId(CourseCommentDto courseCommentDto) throws NotFoundException, TasteException {
		Course course = courseJpaRepository.findByCourseId(courseCommentDto.getCourseId());
		Student student = studentJpaRepository.findByUserId(courseCommentDto.getStudentId());
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		//每个学生只能评论一个课程一次
		CourseComment courseComment = courseCommentJpaRepository.findByCourseAndStudent(course, student);
		ItemCF itemCF = itemCFJpaRepository.findByStudentIdAndCourseId(student.getUserId(), course.getCourseId());
		if (itemCF == null) {
			itemCF = new ItemCF();
			itemCF.setCourseId(course.getCourseId());
			itemCF.setStudentId(student.getUserId());
		}
		if (courseComment == null) {
			courseComment = new CourseComment();
			courseComment.setCourseId(course.getCourseId());
			courseComment.setStudentId(student.getUserId());
			Double newMark = itemCF.getMark() == null ? courseCommentDto.getCommentMark() : (itemCF.getMark() + courseCommentDto.getCommentMark());
			itemCF.setMark(newMark);
		} else {
			Double newMark = itemCF.getMark() == null ? courseCommentDto.getCommentMark() : (itemCF.getMark() - courseComment.getCommentMark() + courseCommentDto.getCommentMark());
			itemCF.setMark(newMark);
		}
		itemCFJpaRepository.save(itemCF);
		
		courseComment.setCommentMark(courseCommentDto.getCommentMark());
		courseComment.setContent(courseCommentDto.getComment());
		courseComment.setLikes(0);
		courseCommentJpaRepository.save(courseComment);
		course.setCourseAvgMark(courseCommentJpaRepository.getCommentMarkAvg(course.getCourseId()));
		courseJpaRepository.save(course);
		//更新所有学生推荐信息
		itemBasedCF.asyncUpdateAll();
		//更新课程评论分析信息
		courseCommentService.analysisByCourse(courseCommentDto.getCourseId());
		//每20条评论训练一次情感分析模型
		if (courseComment.getCommentId() % 20 == 0) {
			courseCommentService.trainLatest(courseComment.getCommentId());
		}
	}
	
	@Override
	public CourseComment getCourseCommentByStudentAndCourse(Integer userId, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		Student student = studentJpaRepository.findByUserId(userId);
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return courseCommentJpaRepository.findByCourseAndStudent(course, student);
	}
	
	@Override
	public void collectPreference(Integer userId, Integer[] prefersId) {
		studentPreferenceRepository.deleteAllByStudentId(userId);
		for (Integer prefer : prefersId) {
			StudentPreferencePK key = new StudentPreferencePK();
			key.setStudentId(userId);
			key.setPreferId(prefer);
			StudentPreference studentPreference = new StudentPreference();
			studentPreference.setStudentPreferencePK(key);
			studentPreferenceRepository.save(studentPreference);
		}
	}
	
	@Override
	public List<StudentPreference> getAllPreferences(Integer userId) {
		return studentPreferenceRepository.findAllByStudentId(userId);
	}
	
	@Override
	public String studentVip(Integer studentId, Integer type) throws NotFoundException, ParamErrorException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		Date vipDate = DateUtil.stringToDate(student.getVipDate());
		Date nowDate = DateUtil.getNowDate();
		vipDate = nowDate.before(vipDate) ? vipDate : nowDate;
		switch (type) {
			case (1):
				vipDate = DateUtil.getDateAddMonth(vipDate, 1);
				break;
			case (2):
				vipDate = DateUtil.getDateAddMonth(vipDate, 6);
				break;
			case (3):
				vipDate = DateUtil.getDateAddYear(vipDate, 1);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + type);
		}
		student.setVipDate(DateUtil.dateToTimestamp(vipDate));
		studentJpaRepository.saveAndFlush(student);
		return new SimpleDateFormat("yyyy-MM-dd").format(vipDate);
	}
	
	@Override
	public void likeCourse(Integer studentId, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		StudentLikeCourse studentLikeCourse = likeCourseJpaRepository.findByStudentAndCourse(student, course);
		if (studentLikeCourse == null) {
			studentLikeCourse = new StudentLikeCourse();
			studentLikeCourse.setStudentId(student.getUserId());
			studentLikeCourse.setCourseId(course.getCourseId());
			likeCourseJpaRepository.saveAndFlush(studentLikeCourse);
		} else {
			return;
		}
		ItemCF itemCF = itemCFJpaRepository.findByStudentIdAndCourseId(studentId, courseId);
		if (itemCF == null) {
			itemCF = new ItemCF();
			itemCF.setCourseId(course.getCourseId());
			itemCF.setStudentId(student.getUserId());
			itemCF.setMark(2.0);
		} else {
			itemCF.setMark(itemCF.getMark() + 2.0);
		}
		itemCFJpaRepository.save(itemCF);
	}
	
	@Override
	public void cancelLikeCourse(Integer studentId, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		likeCourseJpaRepository.deleteByStudentIdAndCourseId(student.getUserId(), course.getCourseId());
		ItemCF itemCF = itemCFJpaRepository.findByStudentIdAndCourseId(studentId, courseId);
		Double newMark = itemCF.getMark() - 2.0 < 0 ? 0 : itemCF.getMark() - 2.0;
		itemCF.setMark(newMark);
		itemCFJpaRepository.save(itemCF);
	}
	
	@Override
	public StudentLikeCourse getLikeByStudentAndCourse(Integer studentId, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null || course == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return likeCourseJpaRepository.findByStudentAndCourse(student, course);
	}
	
	@Override
	public Page<StudentLikeCourse> getStudentLikeCourse(Pageable pageable, Integer studentId) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		return likeCourseJpaRepository.findByStudent(student, pageable);
	}
	
	@Override
	public String getStudentItemCfResult(Integer studentId) throws NotFoundException, TasteException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		ItemCFResult itemCFResult = itemCFResultJpaRepository.findByStudentId(studentId);
		if (itemCFResult == null) {
			List<Course> courseList = itemBasedCF.studentCourseCF(ItemBasedCF.getRecommender(), student);
			itemCFResult = new ItemCFResult();
			itemCFResult.setStudentId(student.getUserId());
			String jsonString = JSON.toJSONString(courseList);
			itemCFResult.setResult(jsonString);
			itemCFResultJpaRepository.save(itemCFResult);
			return jsonString;
		} else {
			return itemCFResult.getResult();
		}
	}
}
