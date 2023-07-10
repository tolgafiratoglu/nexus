package com.nexus.reports;

import lombok.Data;
import javax.validation.constraints.NotBlank;
  
@Data
public class ChartRequest {
    @NotBlank(message="Title cannot be blank")
    String title;  

    String reportType;

    String[] buckets;
    String[] tables;
    String service;
    String usageMetric;
    String s3Metric;
    String dynamoMetric;
}
