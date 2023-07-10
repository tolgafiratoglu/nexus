package com.nexus.cloudwatch;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.regions.Regions;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    protected Map<String, Double> getS3Stats(String bucketName, String metric)
    {
        Map<String, Double> map = new HashMap<String, Double>();

        Dimension dimension = new Dimension();
            dimension.setName("BucketName");
            dimension.setValue(bucketName);

        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest();
            request.setNamespace("AWS/S3");
            request.setMetricName(metric);
            request.setDimensions(Arrays.asList(dimension)); 
            request.setStatistics(Arrays.asList("Average", "Sum", "Minimum", "Maximum"));
            request.setPeriod(3600);

        GetMetricStatisticsResult response = cloudWatchClient.getMetricStatistics(request);
    
        for (Datapoint dataPoint : response.getDatapoints()) {
            map.put("average", dataPoint.getAverage());
            map.put("minimum", dataPoint.getMinimum());
            map.put("maximum", dataPoint.getMaximum());
            map.put("sum", dataPoint.getSum());
        }

        return map;
    }
}
