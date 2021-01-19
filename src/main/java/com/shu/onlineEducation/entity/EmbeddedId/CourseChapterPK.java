package com.shu.onlineEducation.entity.EmbeddedId;

import com.shu.onlineEducation.entity.Course;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class CourseChapterPK implements Serializable {
	@Column(name = "course_id")
	private Integer courseId;
	
	@Column(name = "chapter_id")
	private Integer chapterId;
}
