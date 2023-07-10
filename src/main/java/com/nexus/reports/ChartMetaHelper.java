package com.nexus.reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ChartMetaHelper {
    
    protected ChartMeta createChartMeta(String key, String value)
    {
        ChartMeta chartMeta = new ChartMeta();
        chartMeta.setMetaKey(key);
        chartMeta.setMetaValue(value);
        return chartMeta;
    }
    
    public List<ChartMeta> populateChartMetas(String reportType, ChartRequest chartRequest)
    {
        List<ChartMeta> chartMetas = new ArrayList<ChartMeta>();

        if (reportType.equals("s3")) {
            String bucketNames = chartRequest.getBuckets().toString();    
            chartMetas.add(createChartMeta("buckets", bucketNames));
            chartMetas.add(createChartMeta("s3Metric", chartRequest.getS3Metric()));
        }

        if (reportType.equals("dynamodb")) {
            String tableNames = chartRequest.getTables().toString();    
            chartMetas.add(createChartMeta("tables", tableNames));
            chartMetas.add(createChartMeta("dynamoMetric", chartRequest.getS3Metric()));
        }

        if (reportType.equals("usage")) {
            chartMetas.add(createChartMeta("service", chartRequest.getService()));
            chartMetas.add(createChartMeta("usageMetric", chartRequest.getUsageMetric()));
        }

        return chartMetas;
    }
}
