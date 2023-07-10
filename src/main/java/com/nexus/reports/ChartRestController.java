package com.nexus.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/report")
public class ChartRestController{
    @Autowired
    ChartService chartService;

    @PutMapping("/save")
    public ResponseEntity saveReport(@RequestBody ChartRequest chartRequest) {
        String reportType = chartRequest.getReportType();
        String metric = reportType.equals("s3") ? chartRequest.getS3Metric() : (reportType.equals("usage") ? chartRequest.getUsageMetric() : chartRequest.getDynamoMetric());
        
        String buckets = String.join(",", chartRequest.getBuckets());
        String tables = String.join(",", chartRequest.getTables());
        String services = String.join(",", chartRequest.getServices());
        
        String items = reportType.equals("s3") ? buckets : (reportType.equals("usage") ? services: tables);

        Chart chart = new Chart();
        chart.setDeleted(false);
        chart.setTitle(chartRequest.getTitle());
        chart.setNamespace(reportType);
        chart.setMetric(metric);
        chart.setItems(items);
        Chart savedChart = chartService.saveChart(chart);

        return ResponseEntity.ok().body("Chart was created");
    }     
}
