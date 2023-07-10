package com.nexus.reports;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotBlank;
  
@Data
public class ChartRequest {
    @NotBlank(message="Title cannot be blank")
    String title;  

    String reportType;

    List<String> buckets;
    List<String> tables;
    List<String> services;
    String usageMetric;
    String s3Metric;
    String dynamoMetric;
}
