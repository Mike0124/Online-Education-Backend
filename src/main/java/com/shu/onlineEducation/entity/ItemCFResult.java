package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item_based_cf_result")
public class ItemCFResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name = "student_id")
	private Integer studentId;
	
	@Column
	private String result;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
}
