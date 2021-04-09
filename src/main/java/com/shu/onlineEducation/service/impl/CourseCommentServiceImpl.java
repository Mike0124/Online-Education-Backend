package com.shu.onlineEducation.service.impl;

import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.shu.onlineEducation.dao.CommentAnalysisResultJpaRepository;
import com.shu.onlineEducation.dao.CourseCommentJpaRepository;
import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.entity.CommentAnalysisResult;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.CourseComment;
import com.shu.onlineEducation.service.CourseCommentService;
import com.shu.onlineEducation.utils.ExceptionUtil.NotFoundException;
import com.shu.onlineEducation.utils.Result.ResultCode;
import com.shu.onlineEducation.utils.runner.PythonRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class CourseCommentServiceImpl implements CourseCommentService {
	
	@Autowired
	private CourseCommentJpaRepository courseCommentJpaRepository;
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	@Autowired
	private CommentAnalysisResultJpaRepository commentAnalysisResultJpaRepository;
	@Autowired
	private PythonRunner pythonRunner;
	
	@Override
	public BigDecimal getCommentMarkAvg(int courseId) {
		return courseCommentJpaRepository.getCommentMarkAvg(courseId);
	}
	
	@Async
	@Override
	public void trainLatest(Integer end) {
		Integer start = Math.max(end - 20, 0);
		List<CourseComment> courseComments = courseCommentJpaRepository.findByBetween(start, end);
		StringBuilder sb = new StringBuilder();
		for (CourseComment courseComment : courseComments) {
			sb.append(courseComment.getContent().replace("\t", " ".replace("\n", ""))).append("\t").append(courseComment.getCommentMark()).append("\n");
		}
		pythonRunner.train(sb.toString());
	}
	
	@Override
	public Page<CourseComment> getCommentsByCourse(Pageable pageable, Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		if (course == null) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		return courseCommentJpaRepository.findByCourse(pageable, course);
	}
	
	@Override
	public Page<CourseComment> getCommentsByCourseWithRegex(Pageable pageable, Integer courseId, String query) throws NotFoundException {
		if (courseJpaRepository.findByCourseId(courseId) == null) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		// 使用HanLP分词
		List<Term> termList = StandardTokenizer.segment(query);
		// 拼接正则字符串
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < termList.size(); i++) {
			String word = termList.get(i).word;
			if (i != termList.size() - 1) {
				sb.append(word).append("|");
			} else {
				sb.append(word);
			}
		}
		String regex = sb.toString();
		sb.insert(0, ".*(");
		sb.append(").*");
		
		Page<CourseComment> courseComments = courseCommentJpaRepository.findByCourseWithRegex(pageable,courseId,sb.toString());
		List<CourseComment> list = courseComments.getContent();
		list.forEach(courseComment -> {
			String newContent = courseComment.getContent().replaceAll(regex, "<font color='#409eff'>$0</font>");
			courseComment.setContent(newContent);
		});
		return courseComments;
	}
	
//	@Async
	@Override
	public void analysisByCourse(Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		if (course == null) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		List<CourseComment> commentList = courseCommentJpaRepository.findByCourse(course);
		StringBuilder sb = new StringBuilder();
		for (CourseComment courseComment : commentList) {
			sb.append(courseComment.getContent().replace("\t", " ".replace("\n", ""))).append("\t").append(courseComment.getCommentMark()).append("\n");
		}
		
		String result = PythonRunner.run("D:\\Projects\\Github\\Online-Education-Backend\\src\\main\\resources\\static\\python\\emotion_analysis.py", new String[]{sb.toString()});
		CommentAnalysisResult commentAnalysisResult = commentAnalysisResultJpaRepository.findByCourse(course);
		if (commentAnalysisResult == null) {
			commentAnalysisResult = new CommentAnalysisResult();
			commentAnalysisResult.setCourseId(course.getCourseId());
		}
		commentAnalysisResult.setCommentResult(result);
		commentAnalysisResultJpaRepository.save(commentAnalysisResult);
	}
	
	@Override
	public JSON getResultByCourse(Integer courseId) throws NotFoundException {
		Course course = courseJpaRepository.findByCourseId(courseId);
		if (course == null) {
			throw new NotFoundException(ResultCode.COURSE_NOT_EXIST);
		}
		CommentAnalysisResult commentAnalysisResult = commentAnalysisResultJpaRepository.findByCourse(course);
		if (commentAnalysisResult == null) {
			analysisByCourse(courseId);
			return null;
		}
		return commentAnalysisResult.getCommentResult();
	}
}
