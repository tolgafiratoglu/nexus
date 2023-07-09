package com.nexus.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartMetaService {
    private final ChartMetaRepository chartMetaRepository;

    @Autowired
    public ChartMetaService(ChartMetaRepository chartMetaRepository) {
        this.chartMetaRepository = chartMetaRepository;
    }

    public ChartMeta saveChartMeta(ChartMeta chartMeta) {
        return chartMetaRepository.save(chartMeta);
    }

    public ChartMeta getChartMetaById(Long id) {
        return chartMetaRepository.findById(id).orElse(null);
    }
}





