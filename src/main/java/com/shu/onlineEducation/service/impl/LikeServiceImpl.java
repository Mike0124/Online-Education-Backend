package com.shu.onlineEducation.service.impl;


import com.shu.onlineEducation.dao.CourseCommentJpaRepository;
import com.shu.onlineEducation.dao.StudentJpaRepository;
import com.shu.onlineEducation.dao.StudentLikeCommentJpaRepository;
import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.entity.Student;
import com.shu.onlineEducation.entity.StudentLikeComment;
import com.shu.onlineEducation.service.LikeService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {
	@Autowired
	private StudentLikeCommentJpaRepository studentLikeCommentJpaRepository;
	@Autowired
	private StudentJpaRepository studentJpaRepository;
	@Autowired
	private CourseCommentJpaRepository courseCommentJpaRepository;
	
	@Override
	public Page<StudentLikeComment> getByStudent(Pageable pageable, Integer studentId) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		return studentLikeCommentJpaRepository.findByStudent(pageable, student);
	}
	
	@Override
	public void addByStudentAndCourseComment(Integer studentId, Integer commentId) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		CourseComment comment = courseCommentJpaRepository.findByCommentId(commentId);
		if (student == null || comment == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		if (studentLikeCommentJpaRepository.findByStudentAndCourseComment(student, comment) != null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		StudentLikeComment studentLikeComment = new StudentLikeComment();
		studentLikeComment.setStudentId(student.getUserId());
		studentLikeComment.setCommentId(comment.getCommentId());
		studentLikeCommentJpaRepository.saveAndFlush(studentLikeComment);
		comment.setLikes(comment.getLikes() + 1);
		courseCommentJpaRepository.saveAndFlush(comment);
	}
	
	
	@Override
	public void deleteByStudentAndCourseComment(Integer studentId, Integer commentId) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		CourseComment comment = courseCommentJpaRepository.findByCommentId(commentId);
		if (student == null || comment == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		StudentLikeComment studentLikeComment = studentLikeCommentJpaRepository.findByStudentAndCourseComment(student, comment);
		if (studentLikeComment == null) {
			throw new NotFoundException(ResultCode.PARAM_IS_INVALID);
		}
		studentLikeCommentJpaRepository.delete(studentLikeComment);
		comment.setLikes(comment.getLikes() - 1);
		courseCommentJpaRepository.saveAndFlush(comment);
	}
	
	@Override
	public List<StudentLikeComment> getByStudentAndComments(Integer studentId, List<Integer> commentIds) throws NotFoundException {
		Student student = studentJpaRepository.findByUserId(studentId);
		if (student == null) {
			throw new NotFoundException(ResultCode.USER_NOT_EXIST);
		}
		return studentLikeCommentJpaRepository.findByStudentAndComments(studentId, commentIds);
	}
}
