package com.nexus.cloudwatch;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.regions.Regions;

import java.util.List;
import java.util.ArrayList;

import com.nexus.cloudwatch.MetricDTO;

import org.springframework.stereotype.Service;

@Service
public class CloudwatchService {
    AmazonCloudWatch cloudWatchClient;

    CloudwatchService () {
        cloudWatchClient = AmazonCloudWatchClientBuilder
            .standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    protected List<MetricDTO> convertMetricsIntoArrayList(List<Metric> metrics) {
        List<MetricDTO> metricList = new ArrayList<MetricDTO>();

        for(Metric metric : metrics) {
            MetricDTO metricDto = new MetricDTO();
            metricDto.setName(metric.getMetricName());
            metricDto.setNamespace(metric.getNamespace());
            metricDto.setDimensions(metric.getDimensions());
            metricList.add(metricDto);
        }

        return metricList;
    }

    protected List<MetricDTO> listMetrics() {
        ListMetricsRequest request = new ListMetricsRequest();
        return convertMetricsIntoArrayList(cloudWatchClient.listMetrics(request).getMetrics());
    }
}
