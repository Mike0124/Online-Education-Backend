package com.shu.onlineEducation.service;


import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CourseCommentService {
	BigDecimal getCommentMarkAvg(int courseId);
	
	void trainLatest(Integer end);
	
	Page<CourseComment> getCommentsByCourse(Pageable pageable, Integer courseId) throws NotFoundException;
	
	Map<String,Object> analysisByCourse(Integer courseId)throws NotFoundException;
}
