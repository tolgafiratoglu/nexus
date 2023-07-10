package com.nexus.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.cloudwatch.CloudwatchService;

@Service
public class ChartService {
    @Autowired
    CloudwatchService cloudwatch;

    private final ChartRepository chartRepository;

    @Autowired
    public ChartService(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    // Example methods
    public Chart saveChart(Chart chart) {
        return chartRepository.save(chart);
    }

    public Chart getChartById(Long id) {
        return chartRepository.findById(id).orElse(null);
    }

    public List<Chart> getActiveCharts() {
        return chartRepository.findByDeletedFalse();
    }

    public List<ChartData> getChartData() {
        List<ChartData> dataList = new ArrayList<ChartData>();
        
        List<Chart> activeCharts = getActiveCharts();

        for(Chart chart : activeCharts) {
            ChartData chartData = new ChartData();

            chartData.setChart(chart);
            
            String[] items = chart.getItems().split(",");
        
            for(String item : items){
                Map<String, Double> itemData = cloudwatch.getStats(chart.getNamespace(), item, chart.getMetric());
                chartData.data.put(item, itemData);
            }

            dataList.add(chartData);
        }

        return dataList;
    }
}
