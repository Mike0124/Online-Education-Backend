package com.shu.onlineEducation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("backend")
public class AppProperties {
    Integer max_rows_in_one_page;
}
