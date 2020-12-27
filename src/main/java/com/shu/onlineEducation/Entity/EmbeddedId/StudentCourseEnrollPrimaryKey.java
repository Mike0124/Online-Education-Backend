package com.shu.onlineEducation.Entity.EmbeddedId;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StudentCourseEnrollPrimaryKey implements Serializable {
    private Integer studentId;
    private Integer courseId;
}
