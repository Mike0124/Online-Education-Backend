package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Integer courseId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String intro;
	
	@CreatedDate
	@Column
	private Timestamp uploadTime;
	
	@Column(name = "prefer_id")
	private Integer preferId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prefer_id", referencedColumnName = "prefer_id", insertable = false, updatable = false)
	private Prefer prefer;
	
	@Column(name = "need_vip")
	private Boolean needVip;
	
	@Column(name = "course_pic")
	private String coursePic;
	
	@Column(name = "course_status")
	private Integer status;
	
	@Column(name = "course_avg_mark")
	private BigDecimal courseAvgMark;
	
	@Column(name = "course_watches")
	private Integer courseWatches;
	
	@Column(name = "teacher_id")
	private Integer teacherId;
	
	@ManyToOne
//	@JsonIgnore
	@JoinColumn(name = "teacher_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Teacher teacher;
	
	public String getUploadTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(uploadTime);
	}
}
