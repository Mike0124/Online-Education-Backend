package com.shu.online_education.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "comment")
public class CommentInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private int commentId;
	@Column
	private int likes;
	@Column
	private String comment;
	@Column
	private Date time;
	@Column(name = "father_cid")
	private int fatherId;
}
