package com.nexus.reports;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

import com.nexus.reports.Report;

@Data
@Entity
@Table(name = "report_metrics")
public class ReportMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, insertable = false, updatable = false)
    long id;

    @ManyToOne
    @JoinColumn(name="report_id")
    Report report;

    @Column(nullable = false)
    private String metric_name;
}
