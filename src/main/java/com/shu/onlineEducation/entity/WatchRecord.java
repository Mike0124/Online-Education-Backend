package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.onlineEducation.entity.EmbeddedId.CourseChapterVideo;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "update course_watch_record set deleted = null where id = ?")
@Where(clause = "deleted = 0")
@Table(name = "course_watch_record")
public class WatchRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@CreatedDate
	@LastModifiedDate
	@Column(name = "watch_time")
	Timestamp watchTime;
	
	@Column(name = "student_id")
	Integer studentId;
	
	@Column(name = "pic_url")
	String picUrl;
	
	@Column(name = "course_name")
	String courseName;
	
	@Column
	@JsonIgnore
	Integer deleted;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private Student student;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "course_id", referencedColumnName = "course_id"),
			@JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id"),
			@JoinColumn(name = "video_id", referencedColumnName = "video_id")
	})
	CourseChapterVideo courseChapterVideo;
	
	public String getWatchTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(watchTime);
	}
}
