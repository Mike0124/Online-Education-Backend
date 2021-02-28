package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item_based_cf")
public class ItemCF {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name = "student_id")
	private Integer studentId;
	
	@Column(name = "course_id")
	private Integer courseId;
	
	@Column(name = "cf_mark")
	private Double mark;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
	private Course course;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
}
