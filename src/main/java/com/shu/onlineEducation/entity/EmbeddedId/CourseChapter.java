package com.shu.onlineEducation.entity.EmbeddedId;

import com.shu.onlineEducation.entity.Course;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "course_chapter")
public class CourseChapter implements Serializable {
	@EmbeddedId
	CourseChapterPK courseChapterPK;
	
	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
	private Course course;
	
	@Column(name = "chapter_intro")
	String chapterIntro;
}
