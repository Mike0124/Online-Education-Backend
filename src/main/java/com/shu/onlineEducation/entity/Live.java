package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author 黄悦麒
 * @className: Live
 * @description
 * @date 2021/1/7
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Live {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "live_id")
	Integer liveId;
	
	@Column(name = "live_name")
	String liveName;
	
	@Column(name = "live_intro")
	String intro;
	
	@Column(name = "live_start_time")
	Timestamp startTime;
	
	@Column(name = "live_address")
	String address;

	@Column(name = "teacher_id")
	Integer teacherId;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "teacher_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	Teacher teacher;
	
	public String getStartTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(startTime);
	}
	
}
