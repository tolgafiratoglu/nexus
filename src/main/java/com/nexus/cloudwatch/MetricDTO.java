package com.nexus.cloudwatch;

import lombok.Data;
import com.amazonaws.services.cloudwatch.model.Dimension;

import java.util.List;

@Data
public class MetricDTO{
    private String name;
    private String namespace;
    private List<Dimension> dimensions;
}
