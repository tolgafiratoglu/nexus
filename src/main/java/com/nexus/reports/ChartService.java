package com.nexus.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService {
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
}
