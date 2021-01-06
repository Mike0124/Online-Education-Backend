package com.shu.onlineEducation.entity.EmbeddedId;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "relationship_stu_prefer")
public class StudentPreference {
	@EmbeddedId
	private StudentPreferencePrimaryKey studentPreferencePrimaryKey;
}
