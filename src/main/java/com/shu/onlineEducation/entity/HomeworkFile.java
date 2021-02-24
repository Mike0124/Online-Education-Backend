package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "homework_file")
public class HomeworkFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "homework_file_id")
	Integer homeworkFileId;
	
	@Column(name = "file_url")
	String fileUrl;
	
	@Column(name = "file_name")
	String fileName;

	@Column(name = "homework_id")
	Integer homeworkId;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "homework_id",referencedColumnName = "homework_id", insertable = false, updatable = false)
	Homework homework;
}
