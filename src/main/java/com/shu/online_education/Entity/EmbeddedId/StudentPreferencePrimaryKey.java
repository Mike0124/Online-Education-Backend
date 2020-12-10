package com.shu.online_education.Entity.EmbeddedId;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StudentPreferencePrimaryKey implements Serializable {
	private Integer stu_id;
	private Integer pre_id;
}
