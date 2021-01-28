package com.shu.onlineEducation.common.dto.course;

import com.shu.onlineEducation.entity.EmbeddedId.CourseChapter;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class CourseDisplayDto extends LinkedHashMap<CourseChapter, Map<String, List>> {
}
