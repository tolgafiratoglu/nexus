package com.nexus.reports;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ChartData {
    Chart chart;
    String metric;
    Map<String, Map<String, Double>> data = new HashMap<String, Map<String, Double>>();
}
