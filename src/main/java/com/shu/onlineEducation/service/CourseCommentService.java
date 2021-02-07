package com.shu.onlineEducation.service;


import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CourseCommentService {
	BigDecimal getCommentMarkAvg(int courseId);
	
	void trainLatest(Integer end);
	
	List<CourseComment> getCommentsByCourse(Pageable pageable, Integer courseId) throws NotFoundException;
}
