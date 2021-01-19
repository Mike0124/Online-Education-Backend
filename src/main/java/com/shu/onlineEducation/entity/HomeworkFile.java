package com.shu.onlineEducation.entity;

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
	
	@ManyToOne
	@JoinColumn(name = "homework_id",referencedColumnName = "homework_id")
	Homework homework;
	
	@Column(name = "file_url")
	String fileUrl;
}
