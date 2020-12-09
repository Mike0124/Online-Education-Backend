package com.shu.online_education.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "major")
public class MajorInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "major_id")
	private int majorId;
	@Column(name = "major_content", nullable = false)
	private String majorContent;
	@OneToMany(cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<PreferInfo> ClassInfos = new HashSet<>(0);
}
