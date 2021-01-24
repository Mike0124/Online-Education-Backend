package com.shu.onlineEducation.common.dto.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseCommentDto {
    String comment;
    Integer commentMark;
    Integer courseId;
    Integer studentId;
}
