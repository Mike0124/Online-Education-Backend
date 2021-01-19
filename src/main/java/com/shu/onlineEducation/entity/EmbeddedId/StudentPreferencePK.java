package com.shu.onlineEducation.entity.EmbeddedId;

import com.shu.onlineEducation.entity.Prefer;
import com.shu.onlineEducation.entity.Student;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class StudentPreferencePK implements Serializable {
	@Column(name = "student_id")
	Integer studentId;
	
	@Column(name = "prefer_id")
	Integer preferId;
}
