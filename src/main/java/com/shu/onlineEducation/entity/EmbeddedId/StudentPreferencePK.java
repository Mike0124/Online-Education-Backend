package com.shu.onlineEducation.entity.EmbeddedId;

import com.shu.onlineEducation.entity.Prefer;
import com.shu.onlineEducation.entity.Student;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class StudentPreferencePK implements Serializable {
	@ManyToOne
	@JoinColumn(name = "student_id",referencedColumnName = "user_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "prefer_id",referencedColumnName = "prefer_id")
	private Prefer prefer;
}
