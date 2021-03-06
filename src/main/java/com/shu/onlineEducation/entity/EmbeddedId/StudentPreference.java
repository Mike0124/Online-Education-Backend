package com.shu.onlineEducation.entity.EmbeddedId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.onlineEducation.entity.Prefer;
import com.shu.onlineEducation.entity.Student;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "relationship_stu_prefer")
public class StudentPreference {
	@EmbeddedId
	@JsonIgnore
	private StudentPreferencePK studentPreferencePK;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "prefer_id", referencedColumnName = "prefer_id", insertable = false, updatable = false)
	private Prefer prefer;
}
