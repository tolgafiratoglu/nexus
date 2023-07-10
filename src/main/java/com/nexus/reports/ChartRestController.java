package com.nexus.reports;

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
public class ChartRestController {
    @Autowired
    ChartService chartService;

    @Autowired
    ChartMetaService chartMetaService;

    @Autowired
    ChartMetaHelper chartMetaHelper;

    @PutMapping("/save")
    public ResponseEntity saveReport(@RequestBody ChartRequest chartRequest) {
        String reportType = chartRequest.getReportType();

        Chart chart = new Chart();
        chart.setDeleted(false);
        chart.setTitle(chartRequest.getTitle());
        Chart savedChart = chartService.saveChart(chart);

        List<ChartMeta> chartMetas = chartMetaHelper.populateChartMetas(reportType, chartRequest);

        for (ChartMeta chartMeta : chartMetas) {
            chartMeta.setChart(savedChart);
            chartMetaService.saveChartMeta(chartMeta);
        }

        return ResponseEntity.ok().body("Chart was created");
    }     
}
