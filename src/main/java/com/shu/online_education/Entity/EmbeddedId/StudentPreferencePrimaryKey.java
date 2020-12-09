package com.shu.online_education.Entity.EmbeddedId;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StudentPreferencePrimaryKey implements Serializable {
	private Integer student_id;
	private Integer prefer_id;
}
