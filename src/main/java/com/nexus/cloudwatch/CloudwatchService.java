package com.nexus.cloudwatch;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.regions.Regions;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;

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

    public Map<String, Double> getStats(String namespace, String itemName, String metric)
    {
        Map<String, Double> map = new HashMap<String, Double>();

        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();

        calendar.add(Calendar.MONTH, -1); 
        Date startDate = calendar.getTime();

        Dimension dimension = new Dimension();
            
        if(namespace.equals("s3")){
            dimension.setName("BucketName");
        }

        if(namespace.equals("dynamodb")){
            dimension.setName("TableName");
        }

        if(namespace.equals("usage")){
            dimension.setName("Service");
        }

        dimension.setValue(itemName);

        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest();
            request.setNamespace("AWS/S3");
            request.setMetricName(metric);
            request.setDimensions(Arrays.asList(dimension)); 
            request.setStatistics(Arrays.asList("Average", "Sum", "Minimum", "Maximum"));
            request.setPeriod(3600);
            request.setStartTime(startDate);
            request.setEndTime(endDate);

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
