package com.shu.onlineEducation.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "task_file")
public class TaskFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_file_id")
	Integer taskFileId;
	
	@ManyToOne
	@JoinColumn(name = "task_id",referencedColumnName = "task_id")
	Task task;
	
	@Column(name = "file_url")
	String fileUrl;
}
