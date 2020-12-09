package com.shu.online_education.Entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StudentClassEnrollPrimaryKey implements Serializable {
    private Integer student_id;
    private Integer class_id;
}
