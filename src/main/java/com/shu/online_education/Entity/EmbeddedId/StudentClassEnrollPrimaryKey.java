package com.shu.online_education.Entity.EmbeddedId;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Embeddable
@Data
public class StudentClassEnrollPrimaryKey implements Serializable {
    private Integer student_id;
    private Integer class_id;
}
