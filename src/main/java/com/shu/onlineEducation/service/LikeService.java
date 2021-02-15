package com.shu.onlineEducation.service;

import com.shu.onlineEducation.entity.StudentLikeComment;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikeService {
	Page<StudentLikeComment> getByStudent(Pageable pageable, Integer studentId) throws NotFoundException;
	
	void addByStudentAndCourseComment(Integer studentId, Integer commentId) throws NotFoundException;
	
	void deleteByStudentAndCourseComment(Integer studentId, Integer commentId) throws NotFoundException;
	
	List<StudentLikeComment> getByStudentAndComments(Integer studentId, List<Integer> commentIds) throws NotFoundException;
}
