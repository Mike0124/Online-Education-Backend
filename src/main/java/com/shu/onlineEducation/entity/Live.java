package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * @author 黄悦麒
 * @className: Live
 * @description
 * @date 2021/1/7
 */
@Entity
@Data
@Table(name = "live")
public class Live {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "live_id")
	Integer liveId;
	
	@Column(name = "live_name")
	String liveName;
	
	@Column(name = "live_intro")
	String intro;
	
	@Column(name = "live_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	Date liveDate;
	
	@Column(name = "live_address_id")
	Integer addressId;

	@Column(name = "teacher_id")
	Integer teacherId;
	
	@Column(name = "live_pic_url")
	String livePicUrl;

	@Column(name = "live_arrange")
	Integer liveArrange;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "teacher_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	Teacher teacher;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "live_address_id", referencedColumnName = "live_address_id", insertable = false, updatable = false)
	LiveAddress liveAddress;
	
	public String getStartTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(liveDate);
	}
	
}
