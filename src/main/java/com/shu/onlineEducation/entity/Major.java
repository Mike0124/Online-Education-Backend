package com.shu.onlineEducation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "major")
public class Major {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "major_id")
	private Integer majorId;
	
	@Column(name = "major_content", nullable = false)
	private String majorContent;
	
	@OneToMany(mappedBy = "major")
	@JsonIgnore
	private Set<Prefer> prefers = new HashSet<>(0);
}
