package com.shu.onlineEducation.moudle.request;

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
