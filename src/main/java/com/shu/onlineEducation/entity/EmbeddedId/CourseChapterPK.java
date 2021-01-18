package com.shu.onlineEducation.entity.EmbeddedId;

import com.shu.onlineEducation.entity.Course;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class CourseChapterPK implements Serializable {
	@ManyToOne
	@JoinColumn (name = "course_id",referencedColumnName = "course_id")
	private Course course;
	
	@Column(name = "chapter_id")
	private Integer chapterId;
}
