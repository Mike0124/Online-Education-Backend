package com.shu.online_education.Entity.EmbeddedId;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "relationship_stu_prefer")
public class StudentPreference {
	@EmbeddedId
	private StudentPreferencePrimaryKey studentPreferencePrimaryKey;
	@Column(name = "student_id")
	private int studentId;
}
