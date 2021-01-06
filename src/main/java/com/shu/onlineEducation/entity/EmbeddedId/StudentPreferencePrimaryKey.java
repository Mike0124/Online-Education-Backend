package com.shu.onlineEducation.entity.EmbeddedId;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StudentPreferencePrimaryKey implements Serializable {
	private Integer studentId;
	private Integer preferId;
}