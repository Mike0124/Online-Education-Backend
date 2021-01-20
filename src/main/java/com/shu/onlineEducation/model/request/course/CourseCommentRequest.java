package com.shu.onlineEducation.model.request.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseCommentRequest {
    String comment;
    Integer commentMark;
    Integer courseId;
    Integer studentId;
}
