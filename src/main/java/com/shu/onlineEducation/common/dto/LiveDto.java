package com.shu.onlineEducation.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class LiveDto {
    String liveName;
    String liveIntro;
    Timestamp liveDate;
    Integer addressId;
    Integer teacherId;
    String livePicUrl;
    Integer liveArrange;
}
