package com.shu.onlineEducation.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "prefer")
public class Prefer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prefer_id")
	private Integer preferId;
	
	@Column(name = "prefer_content", nullable = false)
	private String preferContent;
	
	@ManyToOne
	@JoinColumn(name = "major_id",referencedColumnName = "major_id")
	private Major major;
}
