package com.shu.online_education.Entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "prefer")
public class PreferInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prefer_id")
	private int preferId;
	@Column(name = "prefer_content", nullable = false)
	private String preferContent;
//	@ManyToOne
//	@JoinTable(name = "major",joinColumns = {@JoinColumn(referencedColumnName = "major_id")})
//	@Column(name = "major_id")
//	private MajorInfo majorInfo;
}
