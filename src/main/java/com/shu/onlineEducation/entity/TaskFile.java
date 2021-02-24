package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Column(name = "task_id")
	Integer taskId;
	
	@Column(name = "file_url")
	String fileUrl;
	
	@Column(name = "file_name")
	String fileName;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "task_id",referencedColumnName = "task_id", updatable = false, insertable = false)
	Task task;
}
