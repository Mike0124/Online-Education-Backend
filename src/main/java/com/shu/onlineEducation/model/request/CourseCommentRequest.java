package com.shu.onlineEducation.model.request;

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
