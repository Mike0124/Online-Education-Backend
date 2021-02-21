package com.shu.onlineEducation.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment_analysis_result")
public class CommentAnalysisResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name = "course_id")
	private Integer courseId;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
	private Course course;
	
	@Column(name = "comment_result")
	String commentResult;
	
	public JSON getCommentResult() {
		return JSON.parseObject(this.commentResult);
	}
}
